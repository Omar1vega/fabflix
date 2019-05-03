package edu.uci.ics.vegao1.service.billing.models.creditcard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardDeleteRequestModel {
    @JsonProperty(value = "id", required = true)
    private String id;


    @JsonCreator
    public CreditCardDeleteRequestModel(@JsonProperty(value = "id", required = true) String id) {
        this.id = id;

    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CreditCardDeleteRequestModel{" +
                "id='" + id + '\'' +
                '}';
    }
}
