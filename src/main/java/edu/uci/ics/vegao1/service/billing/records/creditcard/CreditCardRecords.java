package edu.uci.ics.vegao1.service.billing.records.creditcard;

import edu.uci.ics.vegao1.service.billing.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.billing.models.ResponseModel;
import edu.uci.ics.vegao1.service.billing.models.creditcard.CreditCardDeleteRequestModel;
import edu.uci.ics.vegao1.service.billing.models.creditcard.CreditCardInsertRequestModel;
import edu.uci.ics.vegao1.service.billing.util.Db;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreditCardRecords {
    private static final String INSERT_CREDIT_CARD_STATEMENT = "INSERT IGNORE INTO creditcards (firstName, lastName, expiration, id) VALUES (? ,?, ?, ?)";
    private static final String UPDATE_CREDIT_CARD_STATEMENT = "UPDATE creditcards SET firstName = ?, lastName = ?, expiration = ? WHERE id= ?";
    private static final String DELETE_CREDIT_CARD_STATEMENT = "DELETE FROM creditcards WHERE id = ?";


    public static ResponseModel insertCard(CreditCardInsertRequestModel cartInsertRequest) throws ParseException, SQLException {
        ServiceLogger.LOGGER.info("preparing statement to insert creditcard");
        Date expirationDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(cartInsertRequest.getExpiration()).getTime());
        boolean cardInserted = Db.executeStatement(INSERT_CREDIT_CARD_STATEMENT, cartInsertRequest.getFirstName(), cartInsertRequest.getLastName(), expirationDate, cartInsertRequest.getId());
        if (cardInserted) {
            return ResponseModel.CREDIT_CARD_INSERT_SUCCESSFUL;
        }
        return ResponseModel.CREDIT_CARD_DUPLICATE_INSERTION;
    }

    public static ResponseModel updateCardInfo(CreditCardInsertRequestModel creditCardUpdateRequest) throws ParseException, SQLException {
        Date expirationDate = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(creditCardUpdateRequest.getExpiration()).getTime());
        boolean cardUpdate = Db.executeStatement(UPDATE_CREDIT_CARD_STATEMENT, creditCardUpdateRequest.getFirstName(), creditCardUpdateRequest.getLastName(), expirationDate, creditCardUpdateRequest.getId());
        if (cardUpdate) {
            return ResponseModel.CREDIT_CARD_UPDATE_SUCCESSFUL;
        }
        return ResponseModel.CREDIT_CARD_DOES_NOT_EXIST;
    }

    public static ResponseModel deleteCreditCard(CreditCardDeleteRequestModel creditCardDeleteRequest) throws SQLException {
        boolean cardDeleted = Db.executeStatement(DELETE_CREDIT_CARD_STATEMENT, creditCardDeleteRequest.getId());
        if (cardDeleted) {
            return ResponseModel.CREDIT_CARD_DELETE_SUCCESSFUL;
        }
        return ResponseModel.CREDIT_CARD_DOES_NOT_EXIST;
    }
}
