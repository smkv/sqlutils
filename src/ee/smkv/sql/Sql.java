package ee.smkv.sql;

import javax.sql.DataSource;
import java.sql.SQLException;


public class Sql {

    private DataSource dataSource;
    private String sql;


    public Sql(DataSource dataSource, String sql) {
        this.dataSource = dataSource;
        this.sql = sql;
    }

    public void execute() {
        try {
            dataSource.getConnection().createStatement().execute(sql);
        } catch (SQLException e) {
            throw new SqlException(e.getMessage(), e);
        }
    }
}
