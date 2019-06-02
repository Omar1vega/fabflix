package edu.uci.ics.vegao1.service.billing.models.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequestModel {
    @JsonProperty(value = "email", required = true)
    private String email;


    @JsonCreator
    public OrderRequestModel(@JsonProperty(value = "email", required = true) String email) {
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "OrderRequestModel{" +
                "email='" + email + '\'' +
                '}';
    }
}
