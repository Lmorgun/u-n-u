package server.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryHandler {
    private final ConnectionPool pool;

    public QueryHandler(ConnectionPool pool) {
        this.pool = pool;
    }

    public void executeNoReturn(String sql) throws SQLException {
        Connection con = pool.getConnection();
        Statement statement = con.createStatement();

        statement.executeUpdate(sql);
    }

    public ResultSet execute(String sql) throws SQLException {
        Connection con = pool.getConnection();
        Statement statement = con.createStatement();

        return statement.executeQuery(sql);
    }
}
