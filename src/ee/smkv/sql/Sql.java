package ee.smkv.sql;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;


public class Sql{
  protected Statement statement;
  protected DataSource dataSource;
  protected String sql;


  public Sql(DataSource dataSource, String sql) {
    this.dataSource = dataSource;
    this.sql = sql;

  }

  public Sql execute() {
    try {
      createStatementIfNecessary().execute(sql);
    }
    catch (SQLException e) {
      throw new SqlException(e.getMessage(), e);
    }
    return this;
  }

  protected Statement createStatementIfNecessary() throws SQLException {
    if (statement == null) {
      statement = dataSource.getConnection().createStatement();
    }
    return statement;
  }

  public void close(){
    try {
      if (statement != null) {
        statement.close();
      }
    }
    catch (SQLException e) {
      throw new SqlException("Unable tpo close statement: " + e.getMessage() , e);
    }
  }
}
