package doctors;

import database_conn.connectDatabase;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class setDoctorToPatient {
    gettingAvailableDrs getDr= new gettingAvailableDrs();

   private ArrayList<String>doctosAvailable=getDr.getAvailableDr();
   private ArrayList<String> drNot=getDr.getNotAvail();

   private connectDatabase conn= new connectDatabase();

    public setDoctorToPatient() throws IOException, SQLException {

        conn.getConnection();

        Statement s=conn.createStatement();

        for(String name:doctosAvailable){
            String[] split=name.split(" ");
            System.out.println(split[0]);

            String sql="UPDATE doctors SET availability=true WHERE firstname='"+split[0]+"'";
            s.execute(sql);
        }

        for(String name:drNot){
            String[] split=name.split(" ");


            String sql="UPDATE doctors SET availability=false WHERE firstname='"+split[0]+"'";
            s.execute(sql);
        }

    }
}
