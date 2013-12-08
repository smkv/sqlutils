package ee.smkv.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class SqlSelect implements Iterable<Record> {
    private Database db;
    private String sql;
    private Object[] parameters = {};

    public SqlSelect(Database db, String sql) {
        this.db = db;
        this.sql = sql;
    }

    @Override
    public Iterator<Record> iterator() {
        final ResultSet resultSet = execute();
        return new Iterator<Record>() {
            @Override
            public boolean hasNext() {
                try {
                    return resultSet.next();
                } catch (SQLException e) {
                    throw new SqlException(e.getMessage(), e);
                }
            }

            @Override
            public Record next() {
                return Record.create(resultSet);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove not supported");
            }
        };
    }

    private ResultSet execute() {
        try {
            PreparedStatement statement = db.getConnection().prepareStatement(sql);

            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }


            return statement.executeQuery();
        } catch (SQLException e) {
            throw new SqlException("Failed execute select:" + e.getMessage(), e);
        }
    }

    public SqlSelect parameters(Object[] parameters) {
        this.parameters = parameters;
        return this;
    }
}
