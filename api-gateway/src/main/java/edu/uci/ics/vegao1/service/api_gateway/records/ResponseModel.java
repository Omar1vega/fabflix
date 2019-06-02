package edu.uci.ics.vegao1.service.api_gateway.records;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponseModel {
    private String json;
    private int status;

    private ResponseModel(String json, int status) {
        this.json = json;
        this.status = status;
    }

    static ResponseModel fromResultSet(ResultSet resultSet) throws SQLException {
        String json = resultSet.getString("response");
        int status = resultSet.getInt("httpstatus");

        return new ResponseModel(json, status);
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getJson() {
        return json;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setJson(String json) {
        this.json = json;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public int getStatus() {
        return status;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setStatus(int status) {
        this.status = status;
    }
}
