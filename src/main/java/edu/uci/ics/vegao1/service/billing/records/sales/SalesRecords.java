package edu.uci.ics.vegao1.service.billing.records.sales;

import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.models.order.OrderRequestModel;
import edu.uci.ics.vegao1.service.billing.models.order.OrderResponseModel;
import edu.uci.ics.vegao1.service.billing.util.Db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesRecords {

    private static final String PLACE_ORDER_STATEMENT = "" +
            "INSERT INTO sales (email, movieId, quantity, saleDate)\n" +
            "SELECT email, movieId, quantity, CURDATE()\n" +
            "FROM carts\n" +
            "WHERE email = ?";
    private static final String DELETE_CART_STATEMENT = "DELETE FROM carts WHERE email=?";
    private static final String RETRIEVE_ORDER_STATEMENT = "SELECT email,movieId,quantity,saleDate FROM sales WHERE email = ?";


    public static ResponseModel placeOrder(OrderRequestModel order) throws SQLException {
        ServiceLogger.LOGGER.info("preparing statement to place order");
        boolean orderPlaced = Db.executeStatement(PLACE_ORDER_STATEMENT, order.getEmail());
        if (orderPlaced) {
            Db.executeStatement(DELETE_CART_STATEMENT, order.getEmail());
            return ResponseModel.ORDER_PLACE_SUCCESSFUL;
        }
        return ResponseModel.SHOPPING_CART_NOT_FOUND;
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
        if (!items.isEmpty()) {
            return new OrderResponseModel(ResponseModel.ORDER_RETRIEVE_SUCCESSFUL, items);
        }
        return OrderResponseModel.fromResponseModel(ResponseModel.CUSTOMER_DOES_NOT_EXIST);
    }
}
