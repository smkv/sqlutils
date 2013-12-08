package ee.smkv.sql;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class SimpleDataSource implements DataSource {
    private static final Logger log = Logger.getLogger(SimpleDataSource.class.getName());
    private String url;
    private String user;
    private String password;
    private String driver;
    private Connection connection;

    public SimpleDataSource() {
    }

    public SimpleDataSource(String url, String user, String password, String driver) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.driver = driver;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriver(String driver) {
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

    @Override
    public Connection getConnection() throws SQLException {
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

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        setUser(username);
        setPassword(password);
        return getConnection();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
