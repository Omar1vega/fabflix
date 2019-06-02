package edu.uci.ics.vegao1.service.idm.records.user;

import edu.uci.ics.vegao1.service.idm.IDMService;
import edu.uci.ics.vegao1.service.idm.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.idm.models.RegisterRequestModel;
import edu.uci.ics.vegao1.service.idm.security.Crypto;
import org.apache.commons.codec.binary.Hex;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRecords {
    private static final int DEFAULT_USER_STATUS = 1;
    private static final int DEFAULT_USER_P_LEVEL = 5;
    private static final String EMAIL_CHECK_STATEMENT = "SELECT 1 FROM users WHERE email=?";
    private static final String GET_USER_STATEMENT = "SELECT * FROM users WHERE email=?";
    private static final String INSERT_USER_STATEMENT = "INSERT INTO users (email, status, plevel, salt, pword) VALUES (?, ?, ?, ?, ?)";

    public static boolean emailInUse(String email) {
        ServiceLogger.LOGGER.info("Checking database for email" + email);
        try {
            PreparedStatement statement = IDMService.getCon().prepareStatement(EMAIL_CHECK_STATEMENT);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to check database for email" + email);
        }
        return true;
    }

    public static void createUser(RegisterRequestModel model) {
        ServiceLogger.LOGGER.info("Inserting user into database.");
        try {
            PreparedStatement statement = IDMService.getCon().prepareStatement(INSERT_USER_STATEMENT);
            byte[] salt = Crypto.genSalt();
            String hashedPassword = Hex.encodeHexString(Crypto.hashPassword(model.getPassword(), salt, Crypto.ITERATIONS, Crypto.KEY_LENGTH));
            statement.setString(1, model.getEmail().trim());
            statement.setInt(2, DEFAULT_USER_STATUS);
            statement.setInt(3, DEFAULT_USER_P_LEVEL);
            statement.setString(4, Hex.encodeHexString(salt));
            statement.setString(5, hashedPassword);

            statement.execute();

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to insert user into database");
        }
    }

    public static UserRecord getUser(String email) {
        ServiceLogger.LOGGER.info("Retreiving user from database with email: " + email);
        UserRecord userRecord = null;
        try {
            PreparedStatement statement = IDMService.getCon().prepareStatement(GET_USER_STATEMENT);
            statement.setString(1, email.trim());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int status = resultSet.getInt("status");
                int plevel = resultSet.getInt("plevel");
                String salt = resultSet.getString("salt");
                String pword = resultSet.getString("pword");
                userRecord = new UserRecord(id, email, status, plevel, salt, pword);
            }

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to insert user into database");
        }
        return userRecord;
    }


}
