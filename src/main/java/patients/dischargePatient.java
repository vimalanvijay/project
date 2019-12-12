package patients;

import database_conn.connectDatabase;
import java.sql.SQLException;
import java.sql.Statement;

public class dischargePatient {

    public void discharge(String name,String bednum,String dischageDate) throws SQLException {

        System.out.println(name);

        //connect to database
        connectDatabase conn1=new connectDatabase();

        conn1.getConnection();
        Statement statement=conn1.createStatement();

        String sqlStr = "UPDATE patients SET admit_status=false,bednumber='',discharge_time='"+dischageDate+"' WHERE firstname='" + name + "'; ";
        statement.execute(sqlStr);

        String sqlStr5="UPDATE beds SET availability=true WHERE bed_id='"+bednum+"' ";
        statement.execute(sqlStr5);

        conn1.close();

    }

}

