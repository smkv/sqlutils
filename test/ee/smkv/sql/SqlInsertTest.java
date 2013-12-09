package ee.smkv.sql;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author samko
 */
public class SqlInsertTest {
  private TestInMemoryDataSource ds;

  @Before
  public void init() throws Exception{
    ds = new TestInMemoryDataSource(SqlSelectTest.class);
    new Sql(ds, "CREATE TABLE S (I INT , V VARCHAR2(255))").execute().close();
    new Sql(ds, "CREATE TABLE S2 (I INT auto_increment , V VARCHAR2(255))").execute().close();
  }

  @After
  public void destroy(){
    new Sql(ds, "DROP TABLE S").execute().close();
    new Sql(ds, "DROP TABLE S2").execute().close();
    ds.close();
  }


  @Test
  public void testExecute() throws Exception {
    new SqlInsert(ds, "INSERT INTO S (I , V) VALUES(?,?)").parameters(1 , "TEST").execute().close();
    String result = new SqlSelect(ds, "SELECT * FROM S").all().toString();
    Assert.assertEquals("[Record{I=1, V=TEST}]" , result);
  }

  @Test
  public void testGetGenerated() throws Exception {
    SqlInsert insert = new SqlInsert(ds, "INSERT INTO S2 (V) VALUES(?)");
    insert.parameters("TEST").execute();
    Assert.assertEquals(1L , insert.getGeneratedKey());
    insert.close();
    String result = new SqlSelect(ds, "SELECT * FROM S2").all().toString();
    Assert.assertEquals("[Record{I=1, V=TEST}]" , result);
  }
}
