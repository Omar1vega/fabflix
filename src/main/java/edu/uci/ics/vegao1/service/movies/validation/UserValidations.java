package edu.uci.ics.vegao1.service.movies.validation;

import edu.uci.ics.vegao1.service.movies.models.ResponseModel;

import java.util.regex.Pattern;

public class UserValidations {
    private static final int MAX_EMAIL_LENGTH = 50;
    private static final Pattern emailPattern = Pattern.compile("(^.+)@(.+)\\.(.+)");


    public static ResponseModel validateEmail(String email) {
        if (!emailPattern.matcher(email).matches()) {
            return ResponseModel.EMAIL_INVALID_FORMAT;
        }

        if (email.length() > MAX_EMAIL_LENGTH) {
            return ResponseModel.EMAIL_INVALID_LENGTH;
        }
        return ResponseModel.VALID_REQUEST;
    }
}
