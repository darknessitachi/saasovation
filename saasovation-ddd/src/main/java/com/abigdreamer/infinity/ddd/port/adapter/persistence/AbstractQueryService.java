package com.abigdreamer.infinity.ddd.port.adapter.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

/**
 * 抽象查询服务
 * 
 * @author Darkness
 * @date 2014-5-31 下午2:10:08
 * @version V1.0
 */
public abstract class AbstractQueryService {

    private DataSource dataSource;

    protected AbstractQueryService(DataSource aDataSource) {
        super();

        this.dataSource = aDataSource;
    }

    protected void close(Statement aStatement, ResultSet aResult) {
        if (aStatement != null) {
            try {
                aStatement.close();
            } catch (Exception e) {
                // ignore
            }
        }
        if (aResult != null) {
            try {
                aResult.close();
            } catch (Exception e) {
                // ignore
            }
        }

        ConnectionProvider.closeConnection();
    }

    protected <T> T queryObject(
            Class<T> aClass,
            String aQuery,
            JoinOn aJoinOn,
            String... anArguments) {

        T object = null;

        Connection connection = ConnectionProvider.connection(this.dataSource);
        PreparedStatement selectStatement = null;
        ResultSet result = null;

        try {
            selectStatement = connection.prepareStatement(aQuery);

            this.setStatementArguments(selectStatement, anArguments);

            result = selectStatement.executeQuery();

            if (result.next()) {
                object = this.mapResultToType(result, aClass, aJoinOn);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot query: " + aQuery, e);
        } finally {
            this.close(selectStatement, result);
        }

        return object;
    }

    protected <T> Collection<T> queryObjects(
            Class<T> aClass,
            String aQuery,
            JoinOn aJoinOn,
            Object... anArguments) {

        List<T> objects = new ArrayList<T>();

        Connection connection = ConnectionProvider.connection(this.dataSource);
        PreparedStatement selectStatement = null;
        ResultSet result = null;

        try {
            selectStatement = connection.prepareStatement(aQuery);

            this.setStatementArguments(selectStatement, anArguments);

            result = selectStatement.executeQuery();

            while (result.next()) {
                T object = this.mapResultToType(result, aClass, aJoinOn);

                objects.add(object);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot query: " + aQuery, e);
        } finally {
            this.close(selectStatement, result);
        }

        return objects;
    }

    protected String queryString(
            String aQuery,
            String... anArguments) {

        String value = null;

        Connection connection = ConnectionProvider.connection(this.dataSource);
        PreparedStatement selectStatement = null;
        ResultSet result = null;

        try {
            selectStatement = connection.prepareStatement(aQuery);

            this.setStatementArguments(selectStatement, anArguments);

            result = selectStatement.executeQuery();

            if (result.next()) {
                value = result.getString(1);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Cannot query: " + aQuery, e);
        } finally {
            this.close(selectStatement, result);
        }

        return value;
    }

    private <T> T mapResultToType(ResultSet aResultSet, Class<T> aClass, JoinOn aJoinOn) {
        ResultSetObjectMapper<T> mapper =
                new ResultSetObjectMapper<T>(aResultSet, aClass, aJoinOn);

        return mapper.mapResultToType();
    }

    private void setStatementArguments(
            PreparedStatement aPreparedStatement,
            Object[] anArguments) throws SQLException {

        for (int idx = 0; idx < anArguments.length; ++idx) {
            Object argument = anArguments[idx];
            Class<?> argumentType = argument.getClass();

            if (argumentType == String.class) {
                aPreparedStatement.setString(idx+1, (String) argument);
            } else if (argumentType == Integer.class) {
                aPreparedStatement.setInt(idx+1, (Integer) argument);
            } else if (argumentType == Long.class) {
                aPreparedStatement.setLong(idx+1, (Long) argument);
            } else if (argumentType == Boolean.class) {
                aPreparedStatement.setBoolean(idx+1, (Boolean) argument);
            } else if (argumentType == Date.class) {
                java.sql.Date sqlDate = new java.sql.Date(((Date) argument).getTime());
                aPreparedStatement.setDate(idx+1, sqlDate);
            } else if (argumentType == Double.class) {
                aPreparedStatement.setDouble(idx+1, (Double) argument);
            } else if (argumentType == Float.class) {
                aPreparedStatement.setFloat(idx+1, (Float) argument);
            }
        }
    }
}
