package kz.kuzovatov.pavel.daos;

import kz.kuzovatov.pavel.utils.ConnectionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcDaoFactory<T extends AbstractDao> implements DaoFactory {
    private static Logger log = Logger.getLogger(JdbcDaoFactory.class);

    public JdbcDaoFactory() {
        ConnectionManager.configureConnPool();
    }

    @Override
    public Connection getConnection() {
        Connection connection;
        try {
            connection = ConnectionManager.getConnectionPool().getConnection();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return connection;
    }

    @Override
    public void freeConnection(Connection connection) {
        ConnectionManager.closeConnection(connection);
    }

    @Override
    public T getDao(Class clazz) {
        T t = null;
        try {
            t = (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("InstantiationException or IllegalAccessException was occurred: "+e);
        }
        assert t != null;
        t.setFactory(this);
        return t;
    }
}
