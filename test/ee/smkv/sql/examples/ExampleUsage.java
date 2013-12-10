package ee.smkv.sql.examples;

import ee.smkv.sql.Database;
import ee.smkv.sql.Record;
import ee.smkv.sql.SimpleDataSource;

import java.sql.Connection;
import java.sql.Timestamp;

/**
 * @author samko
 */
public class ExampleUsage {

  public static void main(String ... args) {

    Database database = new Database(new SimpleDataSource("jdbc:h2:mem:test", "sa", "", "org.h2.Driver"));

    database.execute("CREATE TABLE Airport (id int auto_increment , IATA VARCHAR(3) , name VARCHAR2(255))");

    database.insert("INSERT INTO Airport (IATA , name) VALUES( ? , ? )", "TLL", "Tallinn Ülemiste");
    database.insert("INSERT INTO Airport (IATA , name) VALUES( ? , ? )", "HEL", "Helsinki Vaanta");


    for( Record record : database.select("SELECT * FROM Airport ORDER BY name")){
      System.out.println(record);
    }


    database.execute("CREATE ALIAS greeting FOR \"ee.smkv.sql.examples.ExampleUsage.greeting\"");
    System.out.println( database.executeFunction("greeting" , "Anybody"));

    database.execute("CREATE ALIAS custom_today FOR \"ee.smkv.sql.examples.ExampleUsage.today\"");
    System.out.println( database.executeFunction("custom_today"));
  }


  public static String greeting(String user){
    return String.format("Hello %s !" , user);
  }
  public static Timestamp today(){
    return new Timestamp(System.currentTimeMillis());
  }
}
