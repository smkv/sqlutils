package ee.smkv.sql;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {

        Database db = new Database("jdbc:h2:findroute", "sa", "", "org.h2.Driver");

        db.insert("insert into route (time , start, destination, company, duration , price) values(?,?,?,?,?,?)",
                new Timestamp(System.currentTimeMillis()) ,
                "TLL",
                "ARN",
                "Estonianair",
                60 ,
                new BigDecimal("75")
        );


        for(Record record: db.select("select * from route where start = ? " , "TLL")){
            System.out.println(record);
        }

    }
}
