package ee.smkv.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

public class QueryResult implements Iterable<Record> {
    private ResultSet resultSet;

    public QueryResult(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public Iterator<Record> iterator() {
        return new Iterator<Record>() {
            @Override
            public boolean hasNext() {
                try {
                    return resultSet.next();
                } catch (SQLException e) {
                    throw new SqlException(e.getMessage() , e);
                }
            }

            @Override
            public Record next() {
                return Record.create(resultSet);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Removing not supported");
            }
        };
    }
}
