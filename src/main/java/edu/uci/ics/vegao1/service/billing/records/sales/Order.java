package edu.uci.ics.vegao1.service.billing.records.sales;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Order {
    private String email;
    private String movieId;
    private int quantity;
    private String saleDate;
    private float unit_price;
    private float discount;

    private Order(String email, String movieId, int quantity, String saleDate, float unit_price, float discount) {
        this.email = email;
        this.movieId = movieId;
        this.quantity = quantity;
        this.saleDate = saleDate;
        this.unit_price = unit_price;
        this.discount = discount;
    }

    static Order fromResultSet(ResultSet resultSet) throws SQLException {
        String email = resultSet.getString("email");
        String movieId = resultSet.getString("movieId");
        int quantity = resultSet.getInt("quantity");
        String saleDate = resultSet.getString("saleDate");
        float unit_price = resultSet.getFloat("unit_price");
        float discount = resultSet.getFloat("discount");

        return new Order(email, movieId, quantity, saleDate, unit_price, discount);
    }

    @SuppressWarnings("unused") //Used by Jackson
    public float getUnit_price() {
        return unit_price;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setUnit_price(float unit_price) {
        this.unit_price = unit_price;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public float getDiscount() {
        return discount;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setDiscount(float discount) {
        this.discount = discount;
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

    @SuppressWarnings("unused") //Used by Jackson
    public String getSaleDate() {
        return saleDate;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "email='" + email + '\'' +
                ", movieId='" + movieId + '\'' +
                ", quantity=" + quantity +
                ", saleDate='" + saleDate + '\'' +
                ", unit_price=" + unit_price +
                ", discount=" + discount +
                '}';
    }
}
