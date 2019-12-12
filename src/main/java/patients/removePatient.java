package patients;
import UI.MenuBar;
import database_conn.connectDatabase;
import UI.setupFrame;
import UI.countRowsRequired;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.sql.*;
import bed.assignBed;

public class removePatient{
    private setupFrame frame =new setupFrame();
    private JPanel mainPanel=new JPanel(new GridLayout(4,5));
    private JPanel panel1=new JPanel(new FlowLayout());
    private JPanel searchPanel=new JPanel(new GridLayout(3,1));
    private JPanel numPeople = new JPanel(new GridLayout(2,2));
    private JPanel patientList; //lists all the patient(s) that the user typed

    private JButton searchbtn=new JButton("Search");
    private JButton dischargeBtn;

    private JLabel searchNameLabel=new JLabel();

    private JTextField nameField;

    private JLabel firstname=new JLabel(),lastname=new JLabel();
    private JLabel phonenumber=new JLabel(),idnumber=new JLabel();
    private JLabel numberofPatients=new JLabel(),sucessLabel=new JLabel(),bednumber=new JLabel();
    private  JLabel firstnameLabel,lastLabel,phoneLabel,idLabel,bednumberLabel;

    private int count=0;

    String nameEntered=new String();

    public removePatient() throws SQLException, URISyntaxException {

        //gridlayout for patient list
        GridLayout gl=new GridLayout();
        gl.setRows(3);
        patientList=new JPanel(gl);

        //frame
        frame.setFrame();
        frame.setTitle("Search patients");
        frame.getFrame();

        setupLabel();

        //set up database
        connectDatabase conn=new connectDatabase();
        conn.getConnection();

        //labels n field declaration
        searchNameLabel=new JLabel("Enter name: ");
        nameField=new JTextField();

        sucessLabel=new JLabel("Patient is discharged successfully!");

        searchbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //clears the JPanels

                patientList.removeAll();
                sucessLabel.setVisible(false);
                numPeople.removeAll();

                //get user input
                nameEntered = nameField.getText();

                try {
                    Statement s = conn.createStatement();
                    Statement s2 = conn.createStatement();
                    Statement s3=conn.createStatement();

                    String sqlStr = "SELECT * FROM patients where firstname='" + nameEntered + "' ";
                    ResultSet rset = s.executeQuery(sqlStr);

                    String sqlStr2 = "SELECT count(id) FROM patients WHERE firstname='" + nameEntered + "' ";
                    ResultSet rset2 = s2.executeQuery(sqlStr2);

                    //gets num of rows the panel needs
                    countRowsRequired rowsRequired = new countRowsRequired();
                    rowsRequired.NumofRows(gl, rset2);

                    //counts the number of patients w same name by getting the number of rows
                    int count = rowsRequired.getCount();

                    String numPpl = "There are " + Integer.toString(count) + " patients named " + nameEntered + ".";
                    if (count > 1) {
                        numberofPatients = new JLabel(numPpl);
                        numberofPatients.setForeground(Color.WHITE);

                    } else if (count == 0) {
                        numberofPatients = new JLabel("There are no patient named " + nameEntered + ".");
                    } else {
                        numberofPatients = new JLabel("There is " + Integer.toString(count) + " patient named " + nameEntered + ".");
                    }

                    numPeople.add(numberofPatients);
                    numPeople.add(sucessLabel);
                    mainPanel.add(numPeople);

                    while (rset.next()) {
                        firstname = new JLabel("F.Name:"+rset.getString("firstname"));
                        lastname = new JLabel("L.Name:"+rset.getString(("lastname")));
                        phonenumber = new JLabel("Phone:"+rset.getString("phonenumber"));
                        idnumber = new JLabel("ID Num:"+rset.getString("identitynumber"));
                        String bednum=rset.getString("bednumber");
                        bednumber=new JLabel("Bed: "+bednum);

                        stylePage();

                        patientList.add(firstname);
                        patientList.add(lastname);
                        patientList.add(idnumber);
                        patientList.add(phonenumber);
                        patientList.add(bednumber);

                        mainPanel.revalidate();
                        mainPanel.add(patientList);
                    }
                } catch (Exception e) {
                }
            }
        });

        //search panel
        addtoSearchPanel();

        //main panel
        stylePage();
        mainPanel.add(panel1);
        mainPanel.revalidate();
        frame.add(mainPanel);

    }

    private void stylePage(){
        Color background =new Color(44, 44, 88);
        mainPanel.setBackground(background);
        searchPanel.setBackground(background);
        numPeople.setBackground(background);
        patientList.setBackground(background);

        panel1.setBackground(background);

        firstname.setFont(new Font("Raleway Light", Font.PLAIN, 16));
        lastname.setFont(new Font("Raleway Light", Font.PLAIN, 16));
        phonenumber.setFont(new Font("Raleway Light", Font.PLAIN, 16));
        idnumber.setFont(new Font("Raleway Light", Font.PLAIN, 16));;
        numberofPatients.setFont(new Font("Raleway Light", Font.PLAIN, 23));
        sucessLabel.setFont(new Font("Raleway Light", Font.PLAIN, 20));
        bednumber.setFont(new Font("Raleway Light", Font.PLAIN, 16));
        searchNameLabel.setFont(new Font("Raleway Light", Font.PLAIN, 20));
        nameField.setFont(new Font("Raleway Light", Font.PLAIN, 20));

        firstname.setForeground(Color.WHITE);
        lastname.setForeground(Color.WHITE);
        idnumber.setForeground(Color.WHITE);
        bednumber.setForeground(Color.WHITE);
        phonenumber.setForeground(Color.WHITE);

        searchNameLabel.setForeground(Color.WHITE);
        sucessLabel.setForeground(Color.RED);

        numberofPatients.setHorizontalAlignment(JLabel.CENTER);
        sucessLabel.setHorizontalAlignment(JLabel.CENTER);

        numberofPatients.setForeground(Color.white);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        searchPanel.setPreferredSize(new Dimension((int) (screenSize.width*0.60), (int) (screenSize.height*0.23)));
        searchPanel.setMinimumSize(new Dimension(500,160));
        searchPanel.setMaximumSize(new Dimension(900,175));




    }
    private void addtoSearchPanel() {
        searchPanel.add(searchNameLabel);
        searchPanel.add(nameField);
        searchPanel.add(searchbtn);
        panel1.add(searchPanel);
    }
    public JPanel getMainPanel(){
        mainPanel.revalidate();
        return mainPanel;
    }
    private void setupLabel(){
        firstnameLabel=new JLabel("First name:");
        lastLabel=new JLabel("Last name:");
        idLabel=new JLabel("ID number:");
        bednumberLabel=new JLabel("Bed number:");
        phoneLabel=new JLabel("Phone number:");
    }

}