package edu.uci.ics.vegao1.service.billing.models.customer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerModel {
    @JsonProperty(value = "email", required = true)
    private String email;
    @JsonProperty(value = "firstName", required = true)
    private String firstName;
    @JsonProperty(value = "lastName", required = true)
    private String lastName;
    @JsonProperty(value = "ccId", required = true)
    private String ccId;
    @JsonProperty(value = "address", required = true)
    private String address;

    @SuppressWarnings("WeakerAccess") //Used by Jackson
    public CustomerModel(@JsonProperty(value = "email", required = true) String email,
                         @JsonProperty(value = "firstName", required = true) String firstName,
                         @JsonProperty(value = "lastName", required = true) String lastName,
                         @JsonProperty(value = "ccId", required = true) String ccId,
                         @JsonProperty(value = "address", required = true) String address) {

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ccId = ccId;
        this.address = address;
    }

    public static CustomerModel fromResultSet(ResultSet resultSet) throws SQLException {
        String email = resultSet.getString("email");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String ccId = resultSet.getString("ccId");
        String address = resultSet.getString("address");

        return new CustomerModel(email, firstName, lastName, ccId, address);
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCcId() {
        return ccId;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ccId='" + ccId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
