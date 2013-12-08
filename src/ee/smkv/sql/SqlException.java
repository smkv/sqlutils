package ee.smkv.sql;

/**
 * Created by Andrei Samkov on 08.12.13.
 */
public class SqlException extends RuntimeException {
    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }
}
