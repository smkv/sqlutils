package ee.smkv.sql.examples;

import ee.smkv.sql.Database;
import ee.smkv.sql.Record;
import ee.smkv.sql.SimpleDataSource;
import ee.smkv.sql.SqlInsert;


import java.sql.Timestamp;
import java.util.Map;

/**
 * @author samko
 */
public class ExampleUsage {

  public static void main(String ... args) {

    Database database = new Database(new SimpleDataSource("jdbc:h2:mem:test", "sa", "", "org.h2.Driver"));

    database.execute("CREATE TABLE Airport (id int auto_increment , IATA VARCHAR(3) , name VARCHAR2(255))");

    database.insert("INSERT INTO Airport (IATA , name) VALUES( ? , ? )", "TLL", "Tallinn Ülemiste");
    SqlInsert insert = database.insert("INSERT INTO Airport (IATA , name) VALUES( ? , ? )", "HEL", "Helsinki Vaanta");
    System.out.println(insert.getGeneratedKey());


    for( Record record : database.select("SELECT * FROM Airport ORDER BY name")){
      System.out.println(record);
    }


    database.execute("CREATE ALIAS greeting FOR \"ee.smkv.sql.examples.ExampleUsage.greeting\"");
    System.out.println( database.executeFunction("greeting" , "Anybody"));

    database.execute("CREATE ALIAS custom_today FOR \"ee.smkv.sql.examples.ExampleUsage.today\"");
    System.out.println( database.executeFunction("custom_today"));

    database.execute("CREATE ALIAS print FOR \"ee.smkv.sql.examples.ExampleUsage.print\"");
    database.executeProcedure("print" , "Hello SQL utils");
  }


  public static String greeting(String user){
    return String.format("Hello %s !" , user);
  }
  public static Timestamp today(){
    return new Timestamp(System.currentTimeMillis());
  }

  public static void print(String text){
    System.out.println(text);
  }

}
