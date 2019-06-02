package edu.uci.ics.vegao1.service.billing.records.sales;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.models.order.OrderCompleteModel;
import edu.uci.ics.vegao1.service.billing.models.order.OrderPlaceResponseModel;
import edu.uci.ics.vegao1.service.billing.models.order.OrderRequestModel;
import edu.uci.ics.vegao1.service.billing.models.order.OrderResponseModel;
import edu.uci.ics.vegao1.service.billing.util.Db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SalesRecords {

    private static final String PLACE_ORDER_STATEMENT = "" +
            "INSERT INTO sales (email, movieId, quantity, saleDate)\n" +
            "SELECT email, movieId, quantity, CURDATE()\n" +
            "FROM carts\n" +
            "WHERE email = ?";

    private static final String GET_CART_TOTALS_STATEMENT = "" +
            "SELECT id ,(quantity * (unit_price * discount)) AS total\n" +
            "FROM carts,\n" +
            "     movie_prices\n" +
            "WHERE email = ?\n" +
            "  AND carts.movieId = movie_prices.movieId\n" +
            "GROUP BY carts.movieId";

    private static final String INSERT_SALES_TRANSCATION_STATEMENT = "CALL insert_sales_transactions(?, ?)";

    private static final String UPDATE_TRANSACTIONS_STATEMENT = "" +
            "UPDATE transactions\n" +
            "SET transactionId = ?\n" +
            "WHERE token = ?";

    private static final String GET_CARTS_STATEMENT = "" +
            "SELECT email, movieId, quantity, CURDATE() AS saleDate\n" +
            "FROM carts\n" +
            "WHERE email = ?";

    private static final String CALCULATE_TOTAL_STATEMENT = "" +
            "SELECT sum(total) AS total\n" +
            "FROM (SELECT (quantity * (unit_price * discount)) AS total\n" +
            "      FROM carts,\n" +
            "           movie_prices\n" +
            "      WHERE email = ?" +
            "        AND carts.movieId = movie_prices.movieId\n" +
            "      GROUP BY carts.movieId) AS totals";

    private static final String DELETE_CART_STATEMENT = "DELETE FROM carts WHERE email=?";
    private static final String RETRIEVE_ORDER_STATEMENT = "" +
            "SELECT email, sales.movieId, quantity, saleDate, unit_price, discount\n" +
            "FROM sales,\n" +
            "     movie_prices\n" +
            "WHERE email = ? \n" +
            "  AND sales.movieId = movie_prices.movieId";

    private static final String RETRIEVE_TRANSACTIONS_STATEMENT = "" +
            "SELECT transactionId\n" +
            "FROM transactions,\n" +
            "     sales\n" +
            "WHERE email = ? \n" +
            "  AND sales.id = transactions.sId\n" +
            "GROUP BY transactionId";
    private static final String PAYPAL_CLIENT_ID = "AcYDHJDy-FDaHXrrjwYCzVXn04iMoI-KV1WHEP5BYYqptrE37liYPhoOzMJBeEIfS8M3Tz9JjNYVVO_o";
    private static final String PAYPAL_CLIENT_SECRET = "EJo76NofLVn96SIBz0B258OOGUwNAsQMQ1Pxt7HUDJiSXH1Pjda4ROEkt8SrDLqoVlW_krl28Ar7lG7W";


    public static OrderPlaceResponseModel placeOrder(OrderRequestModel order, String returnUrl) throws SQLException {
        ServiceLogger.LOGGER.info("preparing statement to place order");
        String cost = getCost(order.getEmail());

        ServiceLogger.LOGGER.info("Cost : " + cost);
        if (cost != null) {
            OrderPlaceResponseModel response = sendToPaypal(cost, returnUrl);

            List<Cart> carts = getCarts(order.getEmail());
            for (Cart cart : carts) {
                Db.executeStatement(INSERT_SALES_TRANSCATION_STATEMENT, cart.getId(), response.getToken());
            }
            return response;
        }
        return OrderPlaceResponseModel.fromResponseModel(ResponseModel.SHOPPING_CART_NOT_FOUND);
    }


    public static OrderResponseModel retrieveOrder(OrderRequestModel orderRetrieveRequest) throws SQLException {
        ServiceLogger.LOGGER.info("preparing statement to retrieve order");
        ResultSet resultSet = Db.executeStatementForResult(RETRIEVE_ORDER_STATEMENT, orderRetrieveRequest.getEmail());
        List<Order> items = new ArrayList<>();
        while (resultSet.next()) {
            Order order = Order.fromResultSet(resultSet);
            ServiceLogger.LOGGER.info("Found order: " + order);
            items.add(order);
        }

        resultSet = Db.executeStatementForResult(RETRIEVE_TRANSACTIONS_STATEMENT, orderRetrieveRequest.getEmail());
        List<String> transactionIds = new ArrayList<>();
        while (resultSet.next()) {
            String transactionId = resultSet.getString("transactionId");
            ServiceLogger.LOGGER.info("Found transactionId: " + transactionId);
            transactionIds.add(transactionId);
        }

        List<Transaction> transactions = new ArrayList<>();
        for (String transactionId : transactionIds) {
            try {
                APIContext apiContext = new APIContext(PAYPAL_CLIENT_ID, PAYPAL_CLIENT_SECRET, "sandbox");
                com.paypal.api.payments.Sale sale = com.paypal.api.payments.Sale.get(apiContext, transactionId);
                Transaction transaction = new Transaction(transactionId, sale.getState(), new GhettoAmount(sale.getAmount().getTotal(), sale.getAmount().getCurrency()), sale.getTransactionFee(), sale.getCreateTime(), sale.getUpdateTime());
                transactions.add(transaction);

            } catch (Exception e) {
                ServiceLogger.LOGGER.info("error payment with paypal");
                ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            }
        }

        if (!items.isEmpty()) {
            return new OrderResponseModel(ResponseModel.ORDER_RETRIEVE_SUCCESSFUL, items, transactions);
        }
        return OrderResponseModel.fromResponseModel(ResponseModel.CUSTOMER_DOES_NOT_EXIST);
    }

    private static OrderPlaceResponseModel sendToPaypal(String cost, String returnUrl) {
        ServiceLogger.LOGGER.info("setting up payment with paypal");
        Amount amount = new Amount().setCurrency("USD").setTotal(cost);
        ServiceLogger.LOGGER.info("Amount: " + amount.getTotal());

        com.paypal.api.payments.Transaction transaction = new com.paypal.api.payments.Transaction();
        transaction.setAmount(amount);

        Payer payer = new Payer().setPaymentMethod("paypal");
        RedirectUrls redirectUrls = new RedirectUrls().setCancelUrl("https://example.com/cancel").setReturnUrl(returnUrl);
        Payment payment = new Payment().setIntent("sale").setPayer(payer).setTransactions(Collections.singletonList(transaction)).setRedirectUrls(redirectUrls);

        try {
            APIContext apiContext = new APIContext(PAYPAL_CLIENT_ID, PAYPAL_CLIENT_SECRET, "sandbox");
            Payment createdPayment = payment.create(apiContext);
            String redirectUrl = createdPayment.getLinks().get(1).getHref();
            String token = redirectUrl.split("=")[2];

            return new OrderPlaceResponseModel(ResponseModel.ORDER_PLACE_SUCCESSFUL, redirectUrl, token);

        } catch (Exception e) {
            ServiceLogger.LOGGER.info("error payment with paypal");
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
            return OrderPlaceResponseModel.fromResponseModel(ResponseModel.ORDER_CREATE_PAYMENT_FAILED);
        }
    }

    private static String getCost(String user) throws SQLException {
        ResultSet resultSet = Db.executeStatementForResult(CALCULATE_TOTAL_STATEMENT, user);
        if (resultSet.next()) {
            DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
            DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
            symbols.setCurrencySymbol("");
            format.setDecimalFormatSymbols(symbols);
            return format.format(resultSet.getFloat("total"));
        }
        return null;
    }

    public static ResponseModel completeOrder(OrderCompleteModel orderCompleteModel) {
        Payment payment = new Payment();
        payment.setId(orderCompleteModel.getPaymentId());
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(orderCompleteModel.getPayerID());


        try {
            APIContext apiContext = new APIContext(PAYPAL_CLIENT_ID, PAYPAL_CLIENT_SECRET, "sandbox");
            Payment createdPayment = payment.execute(apiContext, paymentExecution);
            String transactionId = createdPayment.getTransactions().get(0).getRelatedResources().get(0).getSale().getId();
            String token = orderCompleteModel.getToken();

            boolean tokenFound = Db.executeStatement(UPDATE_TRANSACTIONS_STATEMENT, transactionId, token);
            if (tokenFound) {
                return ResponseModel.ORDER_PAYMENT_COMPLETED;
            } else {
                return ResponseModel.ORDER_TOKEN_NOT_FOUND;
            }

        } catch (Exception e) {
            ServiceLogger.LOGGER.info("error payment with paypal");
            ServiceLogger.LOGGER.info(e.getClass().getCanonicalName() + e.getLocalizedMessage());
        }

        return ResponseModel.ORDER_PAYMENT_CANNOT_BE_COMPLETED;
    }

    private static List<Sale> callP(String user) throws SQLException {
        ResultSet resultSet = Db.executeStatementForResult(GET_CARTS_STATEMENT, user);
        List<Sale> sales = new ArrayList<>();
        while (resultSet.next()) {
            sales.add(Sale.fromResultSet(resultSet));
        }
        return sales;
    }

    private static List<Cart> getCarts(String email) throws SQLException {
        ResultSet resultSet = Db.executeStatementForResult(GET_CART_TOTALS_STATEMENT, email);
        List<Cart> carts = new ArrayList<>();
        while (resultSet.next()) {
            carts.add(Cart.fromResultSet(resultSet));
        }
        return carts;
    }
}
