package edu.uci.ics.vegao1.service.billing.records.sales;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cart {
    private int id;
    private float total;

    private Cart(int id, float total) {
        this.id = id;
        this.total = total;
    }

    static Cart fromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        float total = resultSet.getFloat("total");

        return new Cart(id, total);
    }


    @SuppressWarnings("unused") //Used by Jackson
    public int getId() {
        return id;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setId(int id) {
        this.id = id;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public float getTotal() {
        return total;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setTotal(float total) {
        this.total = total;
    }
}