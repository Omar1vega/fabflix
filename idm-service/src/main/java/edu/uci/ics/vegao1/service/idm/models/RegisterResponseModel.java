package edu.uci.ics.vegao1.service.idm.models;

public class RegisterResponseModel {
    private int resultCode;
    private String message;

    public RegisterResponseModel(int resultCode, String message) {
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
}
