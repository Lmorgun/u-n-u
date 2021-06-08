package server.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

    private final List<Connection> connections;

    private int curConIndex = 0;
    private final int connectionNumber;

    public ConnectionPool(String connStr, int connectionNumber) throws SQLException {
        this.connectionNumber = connectionNumber;
        connections = new ArrayList<>();

        for (int i = 0; i < connectionNumber; i++) {
            connections.add(DriverManager.getConnection(connStr));
        }
    }

    public synchronized Connection getConnection(){
        if(curConIndex==connectionNumber){
            curConIndex = 0;
        }
        return connections.get(curConIndex++);
    }
}
