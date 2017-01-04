package com.abigdreamer.infinity.ddd.port.adapter.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 数据库连接提供器
 * 
 * @author Darkness
 * @date 2014-5-31 下午6:27:31
 * @version V1.0
 */
public class ConnectionProvider {

    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    public static void closeConnection() {

        try {
            Connection connection = connection();

            if (connection != null) {
                connection.close();

                // System.out.println("---CONNECTION CLOSED");
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "Cannot close connection because: "
                            + e.getMessage(),
                    e);
        } finally {
            connectionHolder.set(null);
        }
    }

    public static Connection connection() {
        Connection connection = connectionHolder.get();

        return connection;
    }

    public static Connection connection(DataSource aDataSource) {

        Connection connection = connection();

        try {
            if (connection == null) {
                connection = aDataSource.getConnection();

                connectionHolder.set(connection);

                // System.out.println("CONNECTION OPENED");
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Connection not provided because: " + e.getMessage(), e);
        }

        return connection;
    }
}
