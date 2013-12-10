<h1>Usage example</h2>

```java
 Database database = new Database(new SimpleDataSource("jdbc:h2:mem:test", "sa", "", "org.h2.Driver"));

 database.execute("CREATE TABLE Airport (id int auto_increment , IATA VARCHAR(3) , name VARCHAR2(255))");

 database.insert("INSERT INTO Airport (IATA , name) VALUES( ? , ? )" , "TLL" , "Tallinn Ülemiste");
 database.insert("INSERT INTO Airport (IATA , name) VALUES( ? , ? )" , "HEL" , "Helsinki Vaanta");


 for( Record record : database.select("SELECT * FROM Airport ORDER BY name")){
   System.out.println( record);
 }
```