package edu.uci.ics.vegao1.service.idm.validation;

import edu.uci.ics.vegao1.service.idm.models.UserRequestModel;
import edu.uci.ics.vegao1.service.idm.models.UserResponseModel;

import java.util.regex.Pattern;

public class UserValidations {
    public static final UserResponseModel VALID_REQUEST = new UserResponseModel(420, "");
    private static final int MAX_EMAIL_LENGTH = 50;
    private static final int MAX_PASSWORD_LENGTH = 16;
    private static final int MIN_PASSWORD_LENGTH = 7;
    private static final Pattern emailPattern = Pattern.compile("(^.+)@(.+)\\.(.+)");

    private UserValidations() {
    }

    public static UserResponseModel processRequest(UserRequestModel requestModel) {
        String email = requestModel.getEmail();
        char[] password = requestModel.getPassword();

        if (password == null || password.length == 0) {
            return new UserResponseModel(-12, "Password has invalid length (cannot be empty/null).");
        }

        if (!emailPattern.matcher(email).matches()) {
            return new UserResponseModel(-11, "Email address has invalid format.");
        }

        if (email.length() > MAX_EMAIL_LENGTH) {
            return new UserResponseModel(-10, "Email address has invalid length.");
        }

        if (password.length < MIN_PASSWORD_LENGTH || password.length > MAX_PASSWORD_LENGTH) {
            return new UserResponseModel(12, "Password has invalid length (cannot be empty/null).");
        }

        if (!validPassword(password)) {
            return new UserResponseModel(13, "Password does not meet character requirements");
        }

//        if (UserRecords.emailInUse(email)) {
//            return new UserResponseModel(16, "Email already in use.");
//        }
        return VALID_REQUEST;
    }

    public static UserResponseModel validateEmail(String email) {
        if (!emailPattern.matcher(email).matches()) {
            return new UserResponseModel(-11, "Email address has invalid format.");
        }

        if (email.length() > MAX_EMAIL_LENGTH) {
            return new UserResponseModel(-10, "Email address has invalid length.");
        }
        return VALID_REQUEST;
    }


    private static boolean validPassword(char[] password) {
        boolean upper = false, lower = false, numeric = false, symbol = false;

        for (char c : password) {
            if (Character.isUpperCase(c)) {
                upper = true;
            }
            if (Character.isLowerCase(c)) {
                lower = true;
            }
            if (Character.isDigit(c)) {
                numeric = true;
            }
            if (!(Character.isAlphabetic(c) && Character.isDigit(c))) {
                symbol = true;
            }
        }
        return upper && lower && numeric && symbol;
    }

}
