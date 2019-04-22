package edu.uci.ics.vegao1.service.idm.records.session;

import edu.uci.ics.vegao1.service.idm.IDMService;
import edu.uci.ics.vegao1.service.idm.logger.ServiceLogger;
import edu.uci.ics.vegao1.service.idm.security.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionRecords {
    private static final String INSERT_SESSION_STATEMENT = "INSERT INTO sessions (sessionID, email, status, timeCreated, lastUsed, exprTime) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_SESSION_STATEMENT = "SELECT * FROM sessions where email = ?";
    private static final String REVOKE_SESSIONS_STATEMENT = "UPDATE sessions SET status = " + Session.REVOKED + " WHERE email = ?";
    private static final String GET_SESSION_STATUS_STATEMENT = "SELECT status FROM sessions where sessionID = ?";

    public static void createSession(Session session) {
        ServiceLogger.LOGGER.info("Inserting session into database.");
        try {
            PreparedStatement statement = IDMService.getCon().prepareStatement(INSERT_SESSION_STATEMENT);

            statement.setString(1, session.getSessionID().toString());
            statement.setString(2, session.getEmail());
            statement.setInt(3, Session.ACTIVE);
            statement.setTimestamp(4, session.getTimeCreated());
            statement.setTimestamp(5, session.getLastUsed());
            statement.setTimestamp(6, session.getExprTime());

            statement.execute();
        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to insert user into database");
        }
    }

    public static void revokeSessions(String email) {
        ServiceLogger.LOGGER.info("Revoking existing sessions in database for email: " + email);
        try {
            PreparedStatement statement = IDMService.getCon().prepareStatement(REVOKE_SESSIONS_STATEMENT);
            statement.setString(1, email);
            statement.execute();

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to Revoking existing sessions in database");
        }

    }

    public static int getSessionStatus(String sessionID) {
        ServiceLogger.LOGGER.info("Retrieving session status from database for session: " + sessionID);
        try {
            PreparedStatement statement = IDMService.getCon().prepareStatement(GET_SESSION_STATUS_STATEMENT);
            statement.setString(1, sessionID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("status");
            }

        } catch (SQLException e) {
            ServiceLogger.LOGGER.info("Unable to insert user into database");
        }
        return -1;
    }


}
