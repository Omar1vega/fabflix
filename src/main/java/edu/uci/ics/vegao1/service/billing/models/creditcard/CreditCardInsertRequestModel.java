package edu.uci.ics.vegao1.service.billing.models.creditcard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardInsertRequestModel {
    @JsonProperty(value = "id", required = true)
    private String id;
    @JsonProperty(value = "firstName", required = true)
    private String firstName;
    @JsonProperty(value = "lastName", required = true)
    private String lastName;
    @JsonProperty(value = "expiration", required = true)
    private String expiration;

    @JsonCreator
    public CreditCardInsertRequestModel(@JsonProperty(value = "id", required = true) String id,
                                        @JsonProperty(value = "firstName", required = true) String firstName,
                                        @JsonProperty(value = "lastName", required = true) String lastName,
                                        @JsonProperty(value = "expiration", required = true) String expiration) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expiration = expiration;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getExpiration() {
        return expiration;
    }

    @Override
    public String toString() {
        return "CreditCardInsertRequestModel{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", expiration='" + expiration + '\'' +
                '}';
    }
}
