package patients;

import UI.setupFrame;
import database_conn.connectDatabase;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class patientsView {

    private JPanel mainPanel=new JPanel();
    private JPanel patientPanel =new JPanel(new GridLayout(5,2));
    private JPanel cards;

    private JLabel firstname,lastname,id,status,notes,phone,admitTime,admitDate,dishargeTime,dischargeDate;

    private setupFrame frame = new setupFrame();

    private connectDatabase conn=new connectDatabase();

    public patientsView() throws SQLException {

        //setup Frame
        frame.setFrame();
        frame.setTitle("All patients");
        frame.getFrame();

        getAllPatients();
    }

    private void getAllPatients() throws SQLException {

        Statement s= conn.createStatement();
        String sql="SELECT firstname,lastname,identitynumber,notes,admit_status,phonenumber,time_date,discharge_time from patients WHERE id>1 Order BY firstname  ; ";
        ResultSet rset=s.executeQuery(sql);

        while (rset.next()){
            firstname=new JLabel("First name: "+rset.getString("firstname"));
            lastname=new JLabel("Last name: "+rset.getString("lastname"));
            id=new JLabel("ID: "+rset.getString("identitynumber"));
            phone=new JLabel("Phone: "+(rset.getString("phonenumber")));
            notes=new JLabel("Notes: "+rset.getString("notes"));

            String AdmitDateTime=rset.getString("time_date");
            String dischargeTimeandDate=rset.getString("discharge_time");

            if(dischargeTimeandDate==null){
                dischargeDate=new JLabel("Discharged: -");
                dishargeTime=new JLabel("Discharge: -");
            }else{
                dischargeDate=new JLabel("Discharged: "+getDate(dischargeTimeandDate));
                dishargeTime=new JLabel("Discharge: "+getTime(dischargeTimeandDate));

            }

            //if admitsatus is false
            if(!rset.getBoolean("admit_status")){
                status=new JLabel("Status: Discharged");
                admitDate=new JLabel("Admit date: "+getDate(AdmitDateTime));
                admitTime=new JLabel("Admit time: "+getTime(AdmitDateTime));
            }else{
                status=new JLabel("Status: Admitted");
                admitDate=new JLabel("Admit date: "+getDate(AdmitDateTime));
                admitTime=new JLabel("Admit time: "+getDate(AdmitDateTime));
            }

            cards=new JPanel(new GridLayout(5,2));
            mainPanel.setLayout(new GridLayout(1,1));

            cards.add(firstname);
            cards.add(lastname);
            cards.add(id);
            cards.add(phone);
            cards.add(notes);
            cards.add(status);
            cards.add(admitDate);
            cards.add(admitTime);
            cards.add(dischargeDate);
            cards.add(dishargeTime);

            pageStyle();
            cardStyle(cards,rset.getBoolean("admit_status"));

            patientPanel.add(cards);
            mainPanel.add(patientPanel);

            frame.add(mainPanel);
        }
        JScrollPane scroll=new JScrollPane(patientPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.getHorizontalScrollBar().setUnitIncrement(20);
        scroll.getVerticalScrollBar().setUnitIncrement(20);
        mainPanel.add(scroll);
        conn.close();
    }

    private String getDate(String time_in){

        String[] timedateSplit;

        timedateSplit=time_in.split("\\s+");

        return timedateSplit[0];
    }

    private String getTime(String time_in){

        String[] timedateSplit;

        timedateSplit=time_in.split("\\s+");

        return timedateSplit[1];
    }

    private void cardStyle(JPanel card_in,boolean admit){
        Color discharged =new Color(225, 225, 225, 249);
        Color admitted = new Color(253,253,253);
        Color border=new Color(44, 44, 88);

        firstname.setHorizontalAlignment(JLabel.LEFT);
        lastname.setHorizontalAlignment(JLabel.LEFT);
        id.setHorizontalAlignment(JLabel.LEFT);
        phone.setHorizontalAlignment(JLabel.LEFT);
        notes.setHorizontalAlignment(JLabel.LEFT);
        status.setHorizontalAlignment(JLabel.LEFT);
        admitTime.setHorizontalAlignment(JLabel.LEFT);
        admitDate.setHorizontalAlignment(JLabel.LEFT);
        dishargeTime.setHorizontalAlignment(JLabel.LEFT);
        dischargeDate.setHorizontalAlignment(JLabel.LEFT);

        firstname.setFont(new Font("Raleway Light", Font.PLAIN, 15));
        lastname.setFont(new Font("Raleway Light", Font.PLAIN, 15));
        id.setFont(new Font("Raleway Light", Font.PLAIN, 15));
        phone.setFont(new Font("Raleway Light", Font.PLAIN, 15));
        notes.setFont(new Font("Raleway Light", Font.PLAIN, 15));
        status.setFont(new Font("Raleway Light", Font.PLAIN, 15));
        admitTime.setFont(new Font("Raleway Light", Font.PLAIN, 15));
        admitDate.setFont(new Font("Raleway Light", Font.PLAIN, 15));
        dishargeTime.setFont(new Font("Raleway Light", Font.PLAIN, 15));
        dischargeDate.setFont(new Font("Raleway Light", Font.PLAIN, 15));

        card_in.setPreferredSize(new Dimension(200,250));

        CompoundBorder cardBorder=new CompoundBorder(new LineBorder(border,2),new EmptyBorder(10,20,10,10));
        card_in.setBorder(cardBorder);

        if(!admit){
            card_in.setBackground(discharged);
        }else{
            card_in.setBackground(admitted);
        }
    }

    private void pageStyle(){
        Color background=new Color(44, 44, 88);

        CompoundBorder border=new CompoundBorder(new LineBorder(Color.BLACK,1),new EmptyBorder(20,10,10,10));
        patientPanel.setBorder(border);
        patientPanel.setBackground(background);

        mainPanel.setBackground(background);
        mainPanel.setBorder(new LineBorder(Color.RED,1));
    }



}
