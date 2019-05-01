package edu.uci.ics.vegao1.service.billing.records;

import edu.uci.ics.vegao1.service.billing.BillingService;
import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.CartInsertRequestModel;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;

import java.sql.PreparedStatement;

public class CartRecords {
    private static final String INSERT_CART_STATEMENT = "INSERT IGNORE INTO carts (email, movieId, quantity) VALUES (?, ?, ?)";

    public static ResponseModel insertCart(CartInsertRequestModel cartInsertRequest) {
        try {
            ServiceLogger.LOGGER.info("preparing statement to insert cart");
            PreparedStatement statement = BillingService.getCon().prepareStatement(INSERT_CART_STATEMENT);
            statement.setString(1, cartInsertRequest.getEmail());
            statement.setString(2, cartInsertRequest.getMovieId());
            statement.setInt(3, cartInsertRequest.getQuantity());

            ServiceLogger.LOGGER.info("Inserting cart: " + statement.toString());
            boolean rowAdded = statement.executeUpdate() > 0;
            if (rowAdded) {
                ServiceLogger.LOGGER.info("Cart inserted successfully");
                return ResponseModel.SHOPPING_CART_INSERT_SUCCESSFUL;
            } else {
                ServiceLogger.LOGGER.info("Unable to insert cart: ");
                return ResponseModel.DUPLICATE_INSERTION;
            }


        } catch (Exception e) {
            //Case 311: Duplicate insertion.
            ServiceLogger.LOGGER.info("Unable to insert cart: " + e.getClass() + e.getCause().getLocalizedMessage());
            return ResponseModel.DUPLICATE_INSERTION;
        }
    }

}
