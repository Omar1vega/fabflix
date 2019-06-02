package edu.uci.ics.vegao1.service.billing.records.creditcard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCard {
    private String id;
    private String firstName;
    private String lastName;
    private String expiration;

    private CreditCard(String id, String firstName, String lastName, String expiration) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expiration = expiration;
    }

    static CreditCard fromResultSet(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String expiration = resultSet.getString("expiration");

        return new CreditCard(id, firstName, lastName, expiration);
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getId() {
        return id;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getFirstName() {
        return firstName;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getLastName() {
        return lastName;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getExpiration() {
        return expiration;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
