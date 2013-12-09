package ee.smkv.sql;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInsert extends Sql {

  protected Object[] parameters;
  protected ResultSet resultSet;

  public SqlInsert(DataSource dataSource, String sql) {
    super(dataSource, sql);
  }

  public SqlInsert parameters(Object ... parameters) {
    this.parameters = parameters;
    return this;
  }

  @Override
  protected PreparedStatement createStatementIfNecessary() throws SQLException {
    if (statement == null) {
      statement = dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
    return (PreparedStatement)statement;
  }

  @Override
  public SqlInsert execute() {
    try {
      PreparedStatement preparedStatement = createStatementIfNecessary();

      for (int i = 0; i < parameters.length; i++) {
        preparedStatement.setObject(i + 1, parameters[i]);
      }
      preparedStatement.executeUpdate();
      resultSet = statement.getGeneratedKeys();
      resultSet.next();
    }
    catch (SQLException e) {
      throw new SqlException(e.getMessage(), e);
    }
    return this;
  }

  public Object getGeneratedKey() {
    try {
      return resultSet.getObject(1);
    }
    catch (SQLException e) {
      throw new SqlException("Unable to get generated key: " + e.getMessage(), e);
    }
  }
}
