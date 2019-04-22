package edu.uci.ics.vegao1.service.idm.records.privilege;

import edu.uci.ics.vegao1.service.idm.IDMService;
import edu.uci.ics.vegao1.service.idm.logger.ServiceLogger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrivilegeRecords {
    private static final String GET_PRIVILEGE_LEVELS_STATEMENT = "SELECT plevel FROM privilege_levels";
    private static final String GET_PRIVILEGE_LEVEL_FOR_USER_STATEMENT = "SELECT plevel FROM users WHERE email = ?";

    public static List<Integer> getPrivilegeLevels() {
        ServiceLogger.LOGGER.info("Retrieving privilege levels from database.");
        List<Integer> levels = new ArrayList<>();
        try {
            PreparedStatement statement = IDMService.getCon().prepareStatement(GET_PRIVILEGE_LEVELS_STATEMENT);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                levels.add(resultSet.getInt("plevel"));
            }

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to retrieve privilege levels from database");
        }
        return levels;
    }

    public static boolean privilegeIsSufficient(String email, int privilegeLevel) {
        ServiceLogger.LOGGER.info("Retrieving privilege levels from database for user: " + email);

        try {
            PreparedStatement statement = IDMService.getCon().prepareStatement(GET_PRIVILEGE_LEVEL_FOR_USER_STATEMENT);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("plevel") <= privilegeLevel;
            }

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to retrieve privilege levels from database for user: " + email);
        }
        return false;
    }
}
