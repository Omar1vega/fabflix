package edu.uci.ics.vegao1.service.billing.records;

import edu.uci.ics.vegao1.service.billing.BillingService;
import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartRecords {
    private static final String INSERT_CART_STATEMENT = "INSERT IGNORE INTO carts (quantity, email, movieId) VALUES (?, ?, ?)";
    private static final String UPDATE_CART_STATEMENT = "UPDATE carts SET quantity = ? WHERE email = ? AND movieId = ?";
    private static final String DELETE_CART_STATEMENT = "DELETE FROM carts WHERE email = ? AND movieId = ?";
    private static final String CLEAR_CART_STATEMENT = "DELETE FROM carts WHERE email = ?";
    private static final String RETRIEVE_CART_STATEMENT = "SELECT email, movieId, quantity FROM carts WHERE email = ?";

    public static ResponseModel insertCart(CartInsertRequestModel cartInsertRequest) {
        ServiceLogger.LOGGER.info("preparing statement to insert cart");
        boolean cartInserted = exec(cartInsertRequest, INSERT_CART_STATEMENT);
        if (cartInserted) {
            ServiceLogger.LOGGER.info("Cart inserted successfully");
            return ResponseModel.SHOPPING_CART_INSERT_SUCCESSFUL;
        } else {
            ServiceLogger.LOGGER.info("Unable to insert cart: ");
            return ResponseModel.DUPLICATE_INSERTION;
        }
    }

    public static ResponseModel updateCart(CartInsertRequestModel cartUpdateRequest) {
        ServiceLogger.LOGGER.info("preparing statement to update cart");
        boolean cartUpdated = exec(cartUpdateRequest, UPDATE_CART_STATEMENT);
        if (cartUpdated) {
            ServiceLogger.LOGGER.info("Cart updated successfully");
            return ResponseModel.SHOPPING_CART_UPDATE_SUCCESSFUL;
        } else {
            ServiceLogger.LOGGER.info("Unable to update cart: ");
            return ResponseModel.ITEM_DOES_NOT_EXIST;
        }
    }

    public static ResponseModel deleteCart(DeleteCartRequestModel cartDeleteRequest) {
        ServiceLogger.LOGGER.info("preparing statement to delete cart");
        try {
            PreparedStatement statement = BillingService.getCon().prepareStatement(DELETE_CART_STATEMENT);
            statement.setString(1, cartDeleteRequest.getEmail());
            statement.setString(2, cartDeleteRequest.getMovieId());

            ServiceLogger.LOGGER.info("Executing query: " + statement.toString());
            if (statement.executeUpdate() > 0) {
                ServiceLogger.LOGGER.info("Cart deleted successfully");
                return ResponseModel.SHOPPING_CART_DELETE_SUCCESSFUL;
            } else {
                ServiceLogger.LOGGER.info("Unable to delete cart: ");
                return ResponseModel.ITEM_DOES_NOT_EXIST;
            }
        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to execute query: " + e.getClass() + e.getCause().getLocalizedMessage());
            return ResponseModel.ITEM_DOES_NOT_EXIST;
        }
    }

    public static CartRetrieveResponseModel retrieveCart(RetrieveCartRequestModel retrieveCartRequest) {
        ServiceLogger.LOGGER.info("preparing statement to retrieve cart");
        try {
            PreparedStatement statement = BillingService.getCon().prepareStatement(RETRIEVE_CART_STATEMENT);
            statement.setString(1, retrieveCartRequest.getEmail());
            ServiceLogger.LOGGER.info("Executing query: " + statement.toString());
            List<Cart> carts = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                carts.add(Cart.fromResultSet(resultSet));
            }

            if (carts.isEmpty()) {
                return CartRetrieveResponseModel.fromResponseModel(ResponseModel.ITEM_DOES_NOT_EXIST);
            } else {
                return new CartRetrieveResponseModel(ResponseModel.SHOPPING_CART_RETRIEVED_SUCCESSFUL, carts);
            }

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to execute query: " + e.getClass() + e.getCause().getLocalizedMessage());
            return CartRetrieveResponseModel.fromResponseModel(ResponseModel.ITEM_DOES_NOT_EXIST);
        }
    }

    public static ResponseModel clearCart(RetrieveCartRequestModel clearCart) {
        ServiceLogger.LOGGER.info("preparing statement to update cart");
        try {
            PreparedStatement statement = BillingService.getCon().prepareStatement(CLEAR_CART_STATEMENT);
            statement.setString(1, clearCart.getEmail());

            ServiceLogger.LOGGER.info("Executing query: " + statement.toString());
            statement.execute();

            return ResponseModel.SHOPPING_CART_CLEAR_SUCCESSFUL;

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to execute query: " + e.getClass() + e.getCause().getLocalizedMessage());
            return ResponseModel.ITEM_DOES_NOT_EXIST;
        }
    }

    private static boolean exec(CartInsertRequestModel model, String query) {
        try {
            PreparedStatement statement = BillingService.getCon().prepareStatement(query);
            statement.setInt(1, model.getQuantity());
            statement.setString(2, model.getEmail());
            statement.setString(3, model.getMovieId());

            ServiceLogger.LOGGER.info("Executing query: " + statement.toString());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to execute query: " + e.getClass() + e.getCause().getLocalizedMessage());
            return false;
        }
    }
}
