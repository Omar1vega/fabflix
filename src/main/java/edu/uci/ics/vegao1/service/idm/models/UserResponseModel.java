package edu.uci.ics.vegao1.service.idm.models;

import java.util.Objects;

public class UserResponseModel {
    private int resultCode;
    private String message;

    public UserResponseModel(int resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public int getResultCode() {
        return resultCode;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponseModel that = (UserResponseModel) o;
        return resultCode == that.resultCode &&
                message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultCode, message);
    }
}
