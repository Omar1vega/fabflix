package edu.uci.ics.vegao1.service.billing.validation;

import edu.uci.ics.vegao1.service.billing.models.ResponseModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CreditCardValidations {
    private static final int EXPECTED_CCID_LENGTH = 19;
    private static final Pattern CCID_PATTERN = Pattern.compile("\\d+");
    private static final Pattern DATE_PATTERN = Pattern.compile("([0-9]{4})-([0-9]{2})-([0-9]{2})");
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    public static ResponseModel validateCreditCard(String ccid, String expiration) {
        ResponseModel idCheck = validateId(ccid);
        if (idCheck == ResponseModel.VALID_REQUEST) {
            return validateExpiration(expiration);
        } else {
            return idCheck;
        }
    }

    private static ResponseModel validateExpiration(String expiration) {
        if (DATE_PATTERN.matcher(expiration.trim()).matches()) {
            try {
                Date expirationDate = DATE_FORMAT.parse(expiration);
                Date today = new Date();

                if (expirationDate.before(today)) {
                    return ResponseModel.CREDIT_CARD_EXPIRATION_INVALID_VALUE;
                }
            } catch (ParseException e) {
                return ResponseModel.CREDIT_CARD_EXPIRATION_INVALID_VALUE;
            }
        } else {
            return ResponseModel.CREDIT_CARD_EXPIRATION_INVALID_VALUE;
        }

        return ResponseModel.VALID_REQUEST;
    }

    public static ResponseModel validateId(String ccid) {
        if (ccid.trim().length() != EXPECTED_CCID_LENGTH) {
            return ResponseModel.CREDIT_CARD_INVALID_LENGTH;
        }
        if (!CCID_PATTERN.matcher(ccid.trim()).matches()) {
            return ResponseModel.CREDIT_CARD_INVALID_VALUE;
        }
        return ResponseModel.VALID_REQUEST;
    }
}
