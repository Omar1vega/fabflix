package edu.uci.ics.vegao1.service.billing.validation;

import edu.uci.ics.vegao1.service.billing.models.ResponseModel;

import java.util.regex.Pattern;

public class UserValidations {
    public static final ResponseModel VALID_REQUEST = new ResponseModel(420, "");
    private static final int MAX_EMAIL_LENGTH = 50;
    private static final Pattern emailPattern = Pattern.compile("(^.+)@(.+)\\.(.+)");


    public static ResponseModel validateEmail(String email) {
        if (!emailPattern.matcher(email).matches()) {
            return new ResponseModel(-11, "Email has invalid format.");
        }

        if (email.length() > MAX_EMAIL_LENGTH) {
            return new ResponseModel(-10, "Email has invalid length.");
        }
        return VALID_REQUEST;
    }
}
