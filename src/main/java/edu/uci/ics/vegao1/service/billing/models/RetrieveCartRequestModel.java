package edu.uci.ics.vegao1.service.billing.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RetrieveCartRequestModel {
    @JsonProperty(value = "email", required = true)
    private String email;

    @JsonCreator
    public RetrieveCartRequestModel(@JsonProperty(value = "email", required = true) String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "RetrieveCartRequestModel{" +
                "email='" + email + '\'' +
                '}';
    }
}
