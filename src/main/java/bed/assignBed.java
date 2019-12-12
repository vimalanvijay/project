package bed;

import UI.countRowsRequired;
import database_conn.connectDatabase;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class assignBed {

    //ward has 10 beds
    //database has 10 beds already initialised

    JPanel availableBeds=new JPanel();
    private ArrayList<Integer> bednumbers=new ArrayList<>();
    JLabel bed_id_Labels;

    private connectDatabase conn=new connectDatabase();

    public assignBed() throws SQLException, URISyntaxException {

    }

    //thesea are all accessed in the bedMap class
    public void setBedUnavailable(String bed_id_in) throws SQLException {
        conn.getConnection();
        Statement s=conn.createStatement();

        String sql="UPDATE beds SET availability=false where bed_id='"+bed_id_in+"' ";
        s.execute(sql);
        conn.close();

    }

    public void setBedAvailable(String bed_id_in) throws SQLException{
        conn.getConnection();
        Statement s=conn.createStatement();

        String sql="UPDATE beds SET availability=true where bed_id='"+bed_id_in+"'";
        s.execute(sql);
        conn.close();
    }

    public JPanel getAvailableBeds() throws SQLException {

        conn.getConnection();
        Statement s=conn.createStatement();

        String sqlStr="SELECT bed_id from beds where availability=true";
        ResultSet rset= s.executeQuery(sqlStr);

        while (rset.next()){
            bednumbers.add(rset.getInt("bed_id"));
        }
        Collections.sort(bednumbers);

        for(int i=0;i<bednumbers.size();i++){
            bed_id_Labels=new JLabel(Integer.toString(bednumbers.get(i))+", ");
            availableBeds.add(bed_id_Labels);
            bed_id_Labels.setFont(new Font("Raleway Light", Font.PLAIN, 25));
            bed_id_Labels.setHorizontalAlignment(JLabel.CENTER);

        }
        availableBeds.setBackground(new Color(253,253,253));

        return availableBeds;
    }

    public boolean isBedEmpty(String bed_in) throws SQLException {
        boolean emptyBed=true;
        conn.getConnection();
        Statement s=conn.createStatement();
        String sql="SELECT availability from beds where bed_id='"+bed_in+"' ";
        ResultSet rset=s.executeQuery(sql);

        while(rset.next()){
            if(rset.getBoolean("availability")==false){
                emptyBed=false;
            }
        }

        return emptyBed;
    }

    //this will get the time shift at which the bed is set patients to
    public int getBedOccupiedTime(String bed_num) throws SQLException {
        String sql="SELECT check_in_time FROM beds WHERE bed_id='"+bed_num+"'";
        String dateAndTime = null;
        Statement s=conn.createStatement();
        ResultSet rset=s.executeQuery(sql);

        while (rset.next()){
            dateAndTime=rset.getString("check_in_time");
        }

        String[] total=dateAndTime.split("\\s+");
        String date=total[0];
        String time=total[1];

        //this gives the hour the patient is admitted
        total=time.split(":");
        int shift= Integer.parseInt(total[0]);

        if((shift>=0) && (shift<8)){
            return 1; //12am to 8am
        }else if((shift>=8)&&(shift<16)){
            return 2; //8am to 4pm
        }else {
            return 3; //4pm to 12am
        }


    }



}