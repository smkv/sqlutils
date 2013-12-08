package ee.smkv.sql;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SqlSelect implements Iterable<Record> {
    private DataSource db;
    private String sql;
    private Object[] parameters = {};

    public SqlSelect(DataSource db, String sql) {
        this.db = db;
        this.sql = sql;
    }

    public List<Record> all() {
        List<Record> records = new ArrayList<Record>();
        ResultSet resultSet = execute();
        try {
            while (resultSet.next()) {
                records.add(Record.create(resultSet));
            }
        } catch (SQLException e) {
            throw new SqlException(e.getMessage(), e);
        } finally {
            closeResultSetSilent(resultSet);
        }
        return records;
    }

    @Override
    public Iterator<Record> iterator() {
        return all().iterator();
    }

    private ResultSet execute() {
        PreparedStatement statement = null;
        try {
            statement = db.getConnection().prepareStatement(sql);

            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }


            return statement.executeQuery();
        } catch (SQLException e) {
            throw new SqlException("Failed execute select:" + e.getMessage(), e);
        }
    }

    private void closeStatementSilent(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ignore) {

        }
    }

    private void closeResultSetSilent(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException ignore) {

        }
    }

    public SqlSelect parameters(Object[] parameters) {
        this.parameters = parameters;
        return this;
    }


    public Record first() {
        Record record = null;
        ResultSet resultSet = execute();
        try {
            if (resultSet.next()) {
                record = Record.create(resultSet);
            }
        } catch (SQLException ignore) {
        } finally {
            closeResultSetSilent(resultSet);
        }
        return record;
    }

    public Record last() {
        Record record = null;
        ResultSet resultSet = execute();
        try {
            while (resultSet.next()) {
                if (resultSet.last()) {
                    record = Record.create(resultSet);
                }
            }
        } catch (SQLException ignore) {
        } finally {
            closeResultSetSilent(resultSet);
        }
        return record;
    }
}
