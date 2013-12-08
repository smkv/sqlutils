package ee.smkv.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInsert {
    private Database db;
    private String sql;
    private Object[] parameters;
    private ResultSet resultSet;

    public SqlInsert(Database db, String sql) {
        this.db = db;
        this.sql = sql;
    }

    public SqlInsert parameters(Object[] parameters) {
        this.parameters = parameters;
        return this;
    }

    public SqlInsert execute(){
        try {
            PreparedStatement statement = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for(int i = 0 ; i < parameters.length ; i++){
                 statement.setObject(i+1 , parameters[i]);
            }
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new SqlException(e.getMessage() , e);
        }
        return this;
    }

    public Object getGenerated(String name) {
        return null;
    }
}
