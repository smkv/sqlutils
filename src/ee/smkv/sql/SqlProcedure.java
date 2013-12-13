package ee.smkv.sql;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author samko
 */
public class SqlProcedure extends Sql {
  protected Object[] parameters = {};
  protected Map<String,Object> result;
  public SqlProcedure(DataSource dataSource, String name, Object... parameters) {
    super(dataSource, "{call "+name+"("+ SqlUtils.getPlaceHoldersForParameters(parameters)+")}");
    this.parameters = parameters;
  }

  @Override
  public SqlProcedure execute() {

    try {
      CallableStatement callableStatement = createStatementIfNecessary();
      for(int i = 0 ; i < parameters.length ; i++){
        Object parameter = parameters[i];

        if(parameter instanceof SqlOutParameter){
          callableStatement.registerOutParameter(i+1 , Types.OTHER);
          if(parameter instanceof SqlInOutParameter){
            callableStatement.setObject(i+1  , ((SqlInOutParameter)parameter).getValue());
          }
        }else{
          callableStatement.setObject(i + 1, parameter);
        }

      }
      callableStatement.execute();
      result = new LinkedHashMap<String, Object>();
      for(int i = 0 ; i < parameters.length ; i++){
        Object parameter = parameters[i];
        if(parameter instanceof SqlOutParameter){
           result.put( ((SqlOutParameter)parameter).getName() , callableStatement.getObject(i+1));
        }
      }
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


  public Map<String, Object> getOutParameters() {
    return result;
  }


}
