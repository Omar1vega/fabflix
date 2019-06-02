package edu.uci.ics.vegao1.service.idm.models;

public class SessionResponseModel {
    private int resultCode;
    private String message;
    private String sessionID;

    public SessionResponseModel(int resultCode, String message, String sessionID) {
        this.resultCode = resultCode;
        this.message = message;
        this.sessionID = sessionID;
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

    @SuppressWarnings("unused") //Used by Jackson
    public String getSessionID() {
        return sessionID;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
