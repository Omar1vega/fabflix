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
                return new ResponseModel(3100, "Shopping cart item inserted successfully.");
            } else {
                ServiceLogger.LOGGER.info("Unable to insert cart: ");
                return new ResponseModel(311, " Duplicate insertion");
            }


        } catch (Exception e) {
            //Case 311: Duplicate insertion.
            ServiceLogger.LOGGER.info("Unable to insert cart: " + e.getClass() + e.getCause().getLocalizedMessage());
            return new ResponseModel(311, " Duplicate insertion");
        }
    }

}
