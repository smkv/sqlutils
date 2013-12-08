package ee.smkv.sql;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by Andrei Samkov on 08.12.13.
 */
public class DatabaseTest {



    @Test
    public void testSelect() throws Exception {
        TestInMemoryDataSource dataSource = new TestInMemoryDataSource(DatabaseTest.class);
        Database database = new Database(dataSource);
        database.execute("CREATE TABLE SELECT_TEST ( COL1 INT, COL2 VARCHAR2(100))");
        database.execute("INSERT INTO SELECT_TEST (COL1 , COL2) VALUES (1 , 'TEST1')");
        database.execute("INSERT INTO SELECT_TEST (COL1 , COL2) VALUES (2 , 'TEST2')");
        database.execute("INSERT INTO SELECT_TEST (COL1 , COL2) VALUES (3 , 'TEST3')");

        Iterator<Record> iterator = database.select("SELECT * FROM SELECT_TEST").iterator();
        Record record = iterator.next();
        Assert.assertEquals(1, record.get("COL1"));
        Assert.assertEquals("TEST1", record.get("COL2"));

        record = iterator.next();
        Assert.assertEquals(2, record.get("COL1"));
        Assert.assertEquals("TEST2", record.get("COL2"));

        record = iterator.next();
        Assert.assertEquals(3, record.get("COL1"));
        Assert.assertEquals("TEST3", record.get("COL2"));

        Assert.assertFalse( iterator.hasNext());
        dataSource.close();
    }

    @Test
    public void testInsert() throws Exception {
//        Database database = new Database(new TestInMemoryDataSource(DatabaseTest.class));
//        database.execute("CREATE TABLE SELECT_TEST ( COL1 INT, COL2 VARCHAR2(100))");

    }
}
