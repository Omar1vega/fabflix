package edu.uci.ics.vegao1.service.billing.records.creditcard;

import edu.uci.ics.vegao1.service.billing.BillingService;
import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.models.creditcard.CreditCardInsertRequestModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreditCardRecords {
    private static final String INSERT_CART_STATEMENT = "INSERT IGNORE INTO creditcards (id, firstName, lastName, expiration) VALUES (? ,?, ?, ?)";

    public static ResponseModel insertCard(CreditCardInsertRequestModel cartInsertRequest) {
        ServiceLogger.LOGGER.info("preparing statement to insert creditcard");

        try {
            PreparedStatement statement = BillingService.getCon().prepareStatement(INSERT_CART_STATEMENT);
            statement.setString(1, cartInsertRequest.getId());
            statement.setString(2, cartInsertRequest.getFirstName());
            statement.setString(3, cartInsertRequest.getLastName());

            Date expirationDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(cartInsertRequest.getExpiration()).getTime());
            statement.setDate(4, expirationDate);

            ServiceLogger.LOGGER.info("Executing query: " + statement.toString());
            if (statement.executeUpdate() > 0) {
                return ResponseModel.CREDIT_CARD_INSERT_SUCCESSFUL;
            }
        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to execute query: " + e.getClass() + e.getCause().getLocalizedMessage());
        } catch (ParseException ignored) {//already handled
        }
        return ResponseModel.CREDIT_CARD_DUPLICATE_INSERTION;
    }

}
