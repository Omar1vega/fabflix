package edu.uci.ics.vegao1.service.billing.records;

import edu.uci.ics.vegao1.service.billing.BillingService;
import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.CartInsertRequestModel;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;

import java.sql.PreparedStatement;

public class CartRecords {
    private static final String INSERT_CART_STATEMENT = "INSERT IGNORE INTO carts (quantity, email, movieId) VALUES (?, ?, ?)";
    private static final String UPDATE_CART_STATEMENT = "UPDATE carts SET quantity = ? WHERE email = ? AND movieId = ?";

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
        } catch (Exception e) {
            ServiceLogger.LOGGER.info("Unable to execute query: " + e.getClass() + e.getCause().getLocalizedMessage());
            return false;
        }
    }
}
