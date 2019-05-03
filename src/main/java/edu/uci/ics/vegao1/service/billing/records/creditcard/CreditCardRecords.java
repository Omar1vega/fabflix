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
    private static final String INSERT_CART_STATEMENT = "INSERT IGNORE INTO creditcards (firstName, lastName, expiration, id) VALUES (? ,?, ?, ?)";
    private static final String UPDATE_CART_STATEMENT = "UPDATE creditcards SET firstName = ?, lastName = ?, expiration = ? WHERE id= ?";


    public static ResponseModel insertCard(CreditCardInsertRequestModel cartInsertRequest) {
        ServiceLogger.LOGGER.info("preparing statement to insert creditcard");

        if (exec(cartInsertRequest, INSERT_CART_STATEMENT)) {
            return ResponseModel.CREDIT_CARD_INSERT_SUCCESSFUL;
        }
        return ResponseModel.CREDIT_CARD_DUPLICATE_INSERTION;

    }

    public static ResponseModel updateCardInfo(CreditCardInsertRequestModel creditCardUpdateRequest) {
        if (exec(creditCardUpdateRequest, UPDATE_CART_STATEMENT)) {
            return ResponseModel.CREDIT_CARD_UPDATE_SUCCESSFUL;
        }
        return ResponseModel.CREDIT_CARD_DOES_NOT_EXIST;


    }

    private static boolean exec(CreditCardInsertRequestModel model, String query) {
        try {
            PreparedStatement statement = BillingService.getCon().prepareStatement(query);
            statement.setString(4, model.getId());
            statement.setString(1, model.getFirstName());
            statement.setString(2, model.getLastName());

            Date expirationDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(model.getExpiration()).getTime());
            statement.setDate(3, expirationDate);

            ServiceLogger.LOGGER.info("Executing query: " + statement.toString());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to execute query: " + e.getClass() + e.getCause().getLocalizedMessage());
        } catch (ParseException ignored) {//already handled
        }
        return false;
    }

}
