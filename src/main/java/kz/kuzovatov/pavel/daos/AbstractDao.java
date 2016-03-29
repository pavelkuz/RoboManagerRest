package kz.kuzovatov.pavel.daos;

import java.sql.Connection;

public class AbstractDao {
    private JdbcDaoFactory factory;
    private Connection connection;

    public AbstractDao() {
    }

    public AbstractDao(JdbcDaoFactory factory) {
        setFactory(factory);
        this.connection = factory.getConnection();
    }

    public JdbcDaoFactory getFactory() {
        return factory;
    }

    public void setFactory(JdbcDaoFactory factory) {
        this.factory = factory;
    }
}
