package edu.uci.ics.vegao1.service.api_gateway.records;

import edu.uci.ics.vegao1.service.api_gateway.util.Db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponseRecords {

    private static final String RETRIEVE_RESPONSE_STATEMENT = "" +
            "SELECT response, httpstatus\n" +
            "FROM responses\n" +
            "WHERE transactionid = ? ";

    private static final String DELETE_RESPONSE_STATEMENT = "" +
            "DELETE\n" +
            "FROM responses\n" +
            "WHERE transactionid = ?";

    public static ResponseModel getReport(String transactionId) throws SQLException {
        ResultSet resultSet = Db.executeStatementForResult(RETRIEVE_RESPONSE_STATEMENT, transactionId);
        if (resultSet.next()) {
            Db.executeStatement(DELETE_RESPONSE_STATEMENT, transactionId);
            return ResponseModel.fromResultSet(resultSet);
        }
        return null;
    }
}
