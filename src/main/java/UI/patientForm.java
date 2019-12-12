package UI;
import bed.assignBed;
import database_conn.connectDatabase;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class patientForm{

    //class initialisation
    private setupFrame frame =new setupFrame();
    private assignBed bed= new assignBed();

    //jpanels to add components
    private JPanel mainPanel= new JPanel(new GridLayout(9,2));
    private JPanel buttonPanel=new JPanel(new GridLayout(1,1));
    private JPanel bedsPanel= new JPanel(new GridLayout(1,2));
    private JPanel labelPanels=new JPanel(new GridLayout(2,1));

    //buttons
    private JButton p_btn = new JButton("Add new patient");

    //text fields
    private JTextField nameField,familyField,ageField,notesField,IDField,phoneField,bedField;

    //initialise labels
    private JLabel nameLabel=new JLabel("First name:");
    private JLabel familyLabel=new JLabel("Family name:");
    private JLabel ageLabel=new JLabel("Age:");
    private JLabel notesLabel=new JLabel("Notes:");
    private JLabel IDLabel=new JLabel("ID number:");
    private JLabel phoneLabel=new JLabel("Phone number:");
    private JLabel bedId=new JLabel("Bed number:");

    private final JLabel succcessLabel= new JLabel("Patient added successfully!"); //prints only if patient is added
    private final JLabel bedAvailableLabel= new JLabel("Available beds:");

    //variables
    private String name;
    private String age;
    private String ID;
    private String notes;
    private String phonenumber;
    private String familyname;
    private String bedEntered;

    public patientForm(String bed_in) throws SQLException, URISyntaxException {

        //setting frame
        frame.setFrame();
        frame.setTitle("Add patients");
        frame.getFrame();

        //get connection to db
        connectDatabase conn= new connectDatabase();
        conn.getConnection();

        initialiseFormField(bed_in);

        //initalise the label not to print wout the button pressed
        succcessLabel.setVisible(false);

        p_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                name= nameField.getText();
                familyname=familyField.getText();
                age=ageField.getText();
                ID=IDField.getText();
                notes=notesField.getText();
                phonenumber=phoneField.getText();
                bedEntered=bed_in;

                //get date and time from system
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                //add patient to postgres db
                try{
                    if((bed.isBedEmpty(bedEntered) == true)&&(!name.isEmpty())&&(!familyname.isEmpty())&&(!ID.isEmpty())&&(!phonenumber.isEmpty())){
                        Statement s= conn.createStatement();
                        String sql = "INSERT INTO patients (firstname, lastname, phonenumber, identitynumber, age, notes,admit_status,bednumber,time_date) values ('"+name+"','"+familyname+"','"+phonenumber+"','"+ID+"','"+age+"','"+notes+"',true,'"+bedEntered+"','"+dtf.format(now)+"' );";
                        s.execute(sql);
                        succcessLabel.setText("Successfully added patient!");
                        succcessLabel.setVisible(true);
                        bed.setBedUnavailable(bedEntered);
                        clearFields();
                    }else{
                        succcessLabel.setText("Some inputs are left empty!");
                        succcessLabel.setVisible(true);
                    }
                }catch(Exception e){
                    succcessLabel.setText("Bed is occupied!");
                    succcessLabel.setVisible(true);
                }

                try{
                    String timeDate=dtf.format(now);
                    String sql2="UPDATE beds SET check_in_time='"+timeDate+"' WHERE bed_id='"+bedEntered+"' ";
                    Statement s2=conn.createStatement();
                    s2.execute(sql2);
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });

        succcessLabel.setVisible(false);

        //add all labels and text fields to main panel
        styleLabels();
        addToPanel();

        // add buttons to button panel
        p_btn.setFont(new Font("Raleway Light",Font.PLAIN,20));
        buttonPanel.add(p_btn);
        buttonPanel.setPreferredSize(new Dimension(50,50));

        //panel to show the beds available
        bedsPanel.setBorder(new LineBorder(Color.BLACK,1));
        bedsPanel.add(bed.getAvailableBeds());

        //add bedpanel to mainpanel
        labelPanels.add(succcessLabel);
        mainPanel.add(labelPanels);
        mainPanel.add(buttonPanel,BorderLayout.CENTER);
        mainPanel.add(bedAvailableLabel);
        mainPanel.add(bedsPanel);
        mainPanel.revalidate();

        frame.add(mainPanel);
    }
    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        IDField.setText("");
        notesField.setText("");
        phoneField.setText("");
        familyField.setText("");
    }
    private void addToPanel() {
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(familyLabel);
        mainPanel.add(familyField);
        mainPanel.add(ageLabel);
        mainPanel.add(ageField);
        mainPanel.add(IDLabel);
        mainPanel.add(IDField);
        mainPanel.add(phoneLabel);
        mainPanel.add(phoneField);
        mainPanel.add(notesLabel);
        mainPanel.add(notesField);
        mainPanel.add(bedId);
        mainPanel.add(bedField);
    }
    private void styleLabels(){

        int fontSize=22;
        nameLabel.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        familyLabel.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        ageLabel.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        IDLabel.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        phoneLabel.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        notesLabel.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        bedId.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));

        succcessLabel.setFont(new Font("Raleway Light", Font.BOLD, 25));
        succcessLabel.setForeground(Color.RED);

        bedAvailableLabel.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));

        nameField.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        familyField.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        ageField.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        notesField.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        phoneField.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        bedField.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));
        IDField.setFont(new Font("Raleway Light", Font.PLAIN, fontSize));

        Color background =new Color(44, 44, 88);
        mainPanel.setBackground(background);

        nameLabel.setForeground(Color.white);
        familyLabel.setForeground(Color.white);
        ageLabel.setForeground(Color.white);
        IDLabel.setForeground(Color.white);
        phoneLabel.setForeground(Color.white);
        notesLabel.setForeground(Color.white);
        bedId.setForeground(Color.white);
        bedAvailableLabel.setForeground(Color.white);

        buttonPanel.setBackground(background);
        labelPanels.setBackground(background);
        succcessLabel.setBackground(background);
        bedsPanel.setBackground(background);

        mainPanel.setBorder(new EmptyBorder(0,10,0,10));



    }
    private void initialiseFormField(String bed_in) {
        nameField=new JTextField();
        familyField=new JTextField();
        ageField=new JTextField();
        IDField=new JTextField();
        notesField=new JTextField();
        phoneField=new JTextField();
        bedField=new JTextField(bed_in);

    }
    public JPanel getMainPanel(){
        return mainPanel;
    }
}
