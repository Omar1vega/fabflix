package edu.uci.ics.vegao1.service.api_gateway.connectionpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    LinkedList<Connection> connections;
    String driver;
    String url;
    String username;
    String password;

    public ConnectionPool(int numCons, String driver, String url, String username, String password) {

    }

    public Connection requestCon() {
        return null;
    }

    public void releaseCon(Connection con) {

    }

    private Connection createConnection() {
        return null;

    }
}
