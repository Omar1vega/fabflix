package edu.uci.ics.vegao1.service.billing.models.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CartRetrieveRequestModel {
    @JsonProperty(value = "email", required = true)
    private String email;

    @JsonCreator
    public CartRetrieveRequestModel(@JsonProperty(value = "email", required = true) String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "CartRetrieveRequestModel{" +
                "email='" + email + '\'' +
                '}';
    }
}
