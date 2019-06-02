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
        boolean updateSuccessful = preparedStatement.executeUpdate() > 0;
        GatewayService.getConPool().releaseCon(preparedStatement.getConnection());
        return updateSuccessful;
    }

    public static ResultSet executeStatementForResult(String statement, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = prepareStatement(statement, parameters);
        ResultSet resultSet = preparedStatement.executeQuery();
        GatewayService.getConPool().releaseCon(preparedStatement.getConnection());
        return resultSet;
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
