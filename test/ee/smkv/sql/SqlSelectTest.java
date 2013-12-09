package ee.smkv.sql;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Andrei Samkov on 09.12.13.
 */
public class SqlSelectTest {
    private TestInMemoryDataSource ds;

    @Before
    public void init() throws Exception{
        ds = new TestInMemoryDataSource(SqlSelectTest.class);
        new Sql(ds, "CREATE TABLE S (I INT , V VARCHAR2(255))").execute().close();
        new Sql(ds, "INSERT INTO S (I,V) VALUES(1,'test1')").execute().close();
        new Sql(ds, "INSERT INTO S (I,V) VALUES(2,'test2')").execute().close();
        new Sql(ds, "INSERT INTO S (I,V) VALUES(3,'test3')").execute().close();
    }

    @After
    public void destroy(){
        new Sql(ds, "DROP TABLE S").execute();
        ds.close();
    }

    @Test
    public void testAll() throws Exception {
        List<Record> allRecords = new SqlSelect(ds, "SELECT * FROM S").all();
        Assert.assertEquals("[Record{I=1, V=test1}, Record{I=2, V=test2}, Record{I=3, V=test3}]", allRecords.toString());
    }

    @Test
    public void testFirst() throws Exception {

    }

    @Test
    public void testLast() throws Exception {

    }
}
