package edu.uci.ics.vegao1.service.idm.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequestModel {

    private String email;
    private char[] password;

    @SuppressWarnings("unused") //Used by Jackson
    @JsonCreator
    public UserRequestModel(@JsonProperty(value = "email") String email, @JsonProperty(value = "password") char[] password) {
        this.email = email;
        this.password = password;
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
    public char[] getPassword() {
        return password;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setPassword(char[] password) {
        this.password = password;
    }
}
