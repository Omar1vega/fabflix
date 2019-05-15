package edu.uci.ics.vegao1.service.api_gateway.connectionpool;

import edu.uci.ics.vegao1.service.api_gateway.logger.ServiceLogger;
import org.glassfish.jersey.internal.util.ExceptionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
    LinkedList<Connection> connections;
    String driver;
    String url;
    String username;
    String password;


    public ConnectionPool(int numCons, String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;

        this.connections = new LinkedList<>();
        for (int i = 0; i < numCons; i++) {
            connections.add(createConnection());
        }
    }

    public Connection requestCon() {
        if (connections.isEmpty()) {
            connections.add(createConnection());
        }
        return connections.pop();
    }

    public void releaseCon(Connection con) {
        connections.addLast(con);

    }

    private Connection createConnection() {
        try {
            Class.forName(this.driver);
            ServiceLogger.LOGGER.config("Database URL: " + this.url);
            Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
            ServiceLogger.LOGGER.config("Connected to database: " + this.url);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            ServiceLogger.LOGGER.severe("Unable to connect to database.\n" + ExceptionUtils.exceptionStackTraceAsString(e));
        }
        return null;
    }
}
