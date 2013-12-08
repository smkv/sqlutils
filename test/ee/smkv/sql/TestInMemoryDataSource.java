package ee.smkv.sql;


import org.h2.Driver;

public class TestInMemoryDataSource extends SimpleDataSource {
    public TestInMemoryDataSource(Class aClass) {
        super("jdbc:h2:mem:"+aClass.getSimpleName(), "", "", Driver.class.getName());
    }
}
