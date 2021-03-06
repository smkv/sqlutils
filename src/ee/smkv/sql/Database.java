package ee.smkv.sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

public class Database {
    private static final Logger log = Logger.getLogger(Database.class.getName());
    private DataSource dataSource;

    public Database() {
    }

    public Database(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SqlSelect select(String sql , Object ... parameters){
        return new SqlSelect(dataSource, sql).parameters(parameters);
    }

    public SqlInsert insert(String sql , Object ... parameters){
        return new SqlInsert(dataSource, sql).parameters(parameters).execute();
    }

    public void execute(String sql) {
        new Sql(dataSource, sql).execute().close();
    }

    public Object executeFunction(String name, Object ... parameters){
       return new SqlFunction(dataSource , name , parameters).execute().getResult();
    }

  public Map<String, Object> executeProcedure(String name, String ... parameters) {
    return new SqlProcedure(dataSource ,name ,parameters).execute().getOutParameters();
  }
}
