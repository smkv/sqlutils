package ee.smkv.sql;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlSelect extends Sql implements Iterable<Record> {

  protected Object[] parameters = {};
  protected ResultSet resultSet;



  public SqlSelect(DataSource dataSource, String sql) {
    super(dataSource, sql);
  }


  @Override
  protected PreparedStatement createStatementIfNecessary() throws SQLException {
    if ( statement == null){
      statement = dataSource.getConnection().prepareStatement(sql);
    }
    return (PreparedStatement)statement;
  }

  @Override
  public SqlSelect execute() {
    try {
      PreparedStatement preparedStatement = createStatementIfNecessary();
      for (int i = 0; i < parameters.length; i++) {
        preparedStatement.setObject(i + 1, parameters[i]);
      }

      resultSet = preparedStatement.executeQuery();
    }
    catch (SQLException e) {
      throw new SqlException("Failed execute SQL query: " + e.getMessage()  ,e);
    }
    return this;
  }

  public SqlSelect parameters(Object ... parameters) {
    this.parameters = parameters;
    return this;
  }


  public Iterator<Record> iterator() {
    return all().iterator();
  }

  public List<Record> all() {
    List<Record> records = new ArrayList<Record>();
    execute();
    try {
      while (resultSet.next()) {
        records.add(Record.create(resultSet));
      }
    }
    catch (SQLException e) {
      throw new SqlException(e.getMessage(), e);
    }
    finally {
      closeResultSetSilent(resultSet);
    }
    return records;
  }

  public Record first() {
    Record record = null;
    execute();
    try {
      if (resultSet.next()) {
        record = Record.create(resultSet);
      }
    }
    catch (SQLException ignore) {
    }
    finally {
      closeResultSetSilent(resultSet);
    }
    return record;
  }


  public Record last() {
    Record record = null;
    execute();
    try {
      while (resultSet.next()) {
        if (resultSet.last()) {
          record = Record.create(resultSet);
        }
      }
    }
    catch (SQLException ignore) {
    }
    finally {
      closeResultSetSilent(resultSet);
    }
    return record;
  }

  private void closeResultSetSilent(ResultSet resultSet) {
    try {
      if (resultSet != null) {
        resultSet.close();
      }
    }
    catch (SQLException ignore) {
    }
  }
}
