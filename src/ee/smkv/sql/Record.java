package ee.smkv.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


public class Record {
    private Map<String, Object> data = new LinkedHashMap<String, Object>();

    private Record() {

    }

    public static Record create(ResultSet resultSet) {
        Record record = new Record();

        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);


                record.data.put(columnName, extract(i, resultSet));

            }
        } catch (SQLException e) {
            throw new SqlException(e.getMessage(), e);
        }

        return record;
    }

    private static Object extract(int columnIndex, ResultSet resultSet) throws SQLException {


        Object result = resultSet.getObject(columnIndex);

        return resultSet.wasNull() ? null : result;
    }

    public String toString(){
        return "Record"+data;
    }

    public Object get(String key) {
        return data.get(key);
    }
}
