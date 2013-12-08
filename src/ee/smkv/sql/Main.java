package ee.smkv.sql;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Database db = new Database(new SimpleDataSource("jdbc:h2:findroute", "sa", "", "org.h2.Driver"));

//        db.insert("insert into route (time , start, destination, company, duration , price) values(?,?,?,?,?,?)",
//                new Date(),
//                "TLL",
//                "ARN",
//                "Estonianair",
//                60 ,
//                new BigDecimal("75")
//        );


        for(Record record: db.select("select * from route where start = ? " , "TLL")){
            System.out.println(record);
        }

        System.out.println(db.select("select * from route").first());
        System.out.println(db.select("select * from route").last());

    }
}
