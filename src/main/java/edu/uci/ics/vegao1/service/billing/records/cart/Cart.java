package edu.uci.ics.vegao1.service.billing.records.cart;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cart {
    private String email;
    private String movieId;
    private int quantity;

    private Cart(String email, String movieId, int quantity) {
        this.email = email;
        this.movieId = movieId;
        this.quantity = quantity;
    }

    static Cart fromResultSet(ResultSet resultSet) throws SQLException {
        String email = resultSet.getString("email");
        String movieId = resultSet.getString("movieId");
        int quantity = resultSet.getInt("quantity");

        return new Cart(email, movieId, quantity);
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
    public String getMovieId() {
        return movieId;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public int getQuantity() {
        return quantity;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
