package ee.smkv.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Database {
    private static final Logger log = Logger.getLogger(Database.class.getName());
    private String url;
    private String user;
    private String password;
    private String driver;
    private Connection connection;

    public Database(String url, String user, String password, String driver) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
    }

    public Connection connect() {
        log.info("Loading driver " + driver);
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new SqlException("Driver " + driver + " not found", e);
        }

        try {
            log.info("Connection to " + url);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SqlException("Could not connect to " + url + " with user " + user, e);
        }
        log.info("Connected");
        return connection;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(3);
        } catch (SQLException e) {
            log.info("Checking SQL connection failed: " + e.getMessage());
            return false;
        }
    }

    public boolean isInitialized() {
        return connection != null;
    }

    public Connection reconnect() {
        close();
        return connect();
    }

    private void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ignore) {
        }
    }

    public Connection getConnection() {
        if (isInitialized()) {
            if (isConnected()) {
                return connection;
            } else {
                return reconnect();
            }
        } else {
            return connect();
        }

    }

    public SqlSelect select(String sql , Object ... parameters){
        return new SqlSelect(this, sql).parameters(parameters);
    }

    public SqlInsert insert(String sql , Object ... parameters){
        return new SqlInsert(this, sql).parameters(parameters).execute();
    }

}
