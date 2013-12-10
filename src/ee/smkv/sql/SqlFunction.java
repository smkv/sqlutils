package ee.smkv.sql;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;


/**
 * @author samko
 */
public class SqlFunction extends Sql {
  protected Object[] parameters = {};
  protected Object result;
  public SqlFunction(DataSource dataSource, String name , Object ... parameters) {
    super(dataSource, "{ ? = call "+name+"("+ SqlUtils.getPlaceHoldersForParameters(parameters)+")}");
    this.parameters = parameters;
  }

  @Override
  public SqlFunction execute() {

    try {
      CallableStatement callableStatement = createStatementIfNecessary();
      callableStatement.registerOutParameter(1, Types.OTHER);
      for(int i = 0 ; i < parameters.length ; i++){
        callableStatement.setObject(i+2  , parameters[i]);
      }
      callableStatement.execute();
      result = callableStatement.getObject(1);
    }
    catch (SQLException e) {
      throw new SqlException("Failed execute function " + sql + ": " + e.getMessage() , e);
    }

    return this;
  }

  @Override
  protected CallableStatement createStatementIfNecessary() throws SQLException {

    if( statement == null){
      statement = dataSource.getConnection().prepareCall(sql);
    }

    return (CallableStatement)statement;
  }

  public Object getResult() {
    return result;
  }
}
