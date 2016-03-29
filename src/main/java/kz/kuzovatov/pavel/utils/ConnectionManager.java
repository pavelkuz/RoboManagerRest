package kz.kuzovatov.pavel.utils;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
    private static final PropertyManager connectionProperties = PropertyManager.getInstance();
    private static Logger log = Logger.getLogger(ConnectionManager.class);
    private static volatile BoneCP connectionPool = null;

    public static void configureConnPool() {
        if (connectionPool!=null)return;
        try {
            connectionProperties.configure("connectionPool.properties");
            Class.forName(connectionProperties.getProperty("driverName"));
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl(connectionProperties.getProperty("url"));
            config.setUsername(connectionProperties.getProperty("login"));
            config.setPassword(connectionProperties.getProperty("password"));
            //config.setMaxConnectionsPerPartition(Integer.parseInt(connectionProperties.getProperty("maxConnectionCount")));
            config.setMinConnectionsPerPartition(Integer.parseInt(connectionProperties.getProperty("minConnectionCount"))); //if you say 5 here, there will be 10 connection available   config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(2); //2*5 = 10 connection will be available
            //config.setLazyInit(true); //depends on the application usage you should chose lazy or not
            //setting Lazy true means BoneCP won't open any connections before you request a one from it.
            //added creation of BoneCP connection pool by singleton pattern
            BoneCP newInstance = connectionPool;
            if (newInstance == null) {
                synchronized (ConnectionManager.class) {
                    newInstance = connectionPool;
                    if (newInstance == null) {
                        connectionPool = newInstance = new BoneCP(config);
                        log.info("Context initialized and connection pool was configured");
                        log.info("Total available connections: " + connectionPool.getTotalCreatedConnections());
                    }
                }
            }
            ConnectionManager.setConnectionPool(connectionPool);
        } catch (Exception e) {
            log.error("Error was occurred: " + e);
        }

    }

    public static void shutdownConnPool() {

        try {
            BoneCP connectionPool = ConnectionManager.getConnectionPool();
            log.info("Context destroyed.");
            if (connectionPool != null) {
                connectionPool.shutdown(); //this method must be called only once when the application stops.
                //you don't need to call it every time when you get a connection from the Connection Pool
                log.info("Connection pool shut downed!");
            }

        } catch (Exception e) {
            log.error("Error was occurred: " + e);
        }
    }

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = getConnectionPool().getConnection();
            //will get a thread-safe connection from the BoneCP connection pool.
            //synchronization of the method will be done inside BoneCP source

        } catch (Exception e) {
            log.error("Error was occurred: " + e);
        }
        return connection;

    }

    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        } catch (Exception e) {
            log.error("Error was occurred: " + e);
        }

    }

    public static void closeResultSet(ResultSet rSet) {
        try {
            if (rSet != null)
                rSet.close();
        } catch (Exception e) {
            log.error("Error was occurred: " + e);
        }

    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null)
                conn.close(); //release the connection - the name is tricky but connection is not closed it is released
            //and it will stay in pool
        } catch (SQLException e) {
            log.error("Error was occurred: " + e);
        }

    }

    public static BoneCP getConnectionPool() {
        return connectionPool;
    }

    public static void setConnectionPool(BoneCP connectionPool) {
        ConnectionManager.connectionPool = connectionPool;
    }

}

