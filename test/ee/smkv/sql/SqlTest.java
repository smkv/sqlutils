package ee.smkv.sql;

import org.easymock.EasyMock;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author samko
 */
public class SqlTest {
  @Test
  public void testExecute() throws Exception {
    DataSource dataSource = EasyMock.createMock(DataSource.class);
    Connection connection = EasyMock.createMock(Connection.class);
    Statement statement = EasyMock.createMock(Statement.class);

    EasyMock.expect( dataSource.getConnection() ).andReturn( connection);
    EasyMock.expect( connection.createStatement()).andReturn(statement);
    EasyMock.expect(statement.execute("SELECT 1")).andReturn(true);
    statement.close();
    EasyMock.replay(dataSource , connection , statement);

    Sql sql = new Sql(dataSource, "SELECT 1");
    sql.execute();
    sql.close();
    EasyMock.verify(dataSource , connection, statement );

  }
}
