package edu.uci.ics.vegao1.service.api_gateway.util;

import edu.uci.ics.vegao1.service.api_gateway.GatewayService;
import edu.uci.ics.vegao1.service.api_gateway.logger.ServiceLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Db {

    private Db() {
    }


    public static boolean executeStatement(String statement, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = prepareStatement(statement, parameters);
        return preparedStatement.executeUpdate() > 0;
    }

    public static ResultSet executeStatementForResult(String statement, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = prepareStatement(statement, parameters);
        return preparedStatement.executeQuery();
    }

    private static PreparedStatement prepareStatement(String statement, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = GatewayService.getConPool().requestCon().prepareStatement(statement);
        int i = 1;
        for (Object parameter : parameters) {
            preparedStatement.setObject(i++, parameter);
        }
        ServiceLogger.LOGGER.info("Statement Prepared: " + preparedStatement.toString());
        return preparedStatement;
    }
}
