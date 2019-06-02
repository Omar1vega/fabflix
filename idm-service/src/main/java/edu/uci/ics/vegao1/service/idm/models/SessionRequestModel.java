package edu.uci.ics.vegao1.service.idm.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionRequestModel {
    private String email;
    private String sessionID;

    @JsonCreator
    public SessionRequestModel(@JsonProperty(value = "email", required = true) String email,
                               @JsonProperty(value = "sessionID", required = true) String sessionID) {
        this.email = email;
        this.sessionID = sessionID;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getEmail() {
        return email;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setEmail(String email) {
        this.email = email;
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
