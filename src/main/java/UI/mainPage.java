package UI;
import bed.bedMap;
import doctors.doctorView;
import doctors.setDoctorToPatient;
import patients.patientsView;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class mainPage {

    private JPanel buttonPanel=new JPanel(new GridLayout(7,1));
    private JPanel mainPanel=new JPanel(new GridLayout(2,1));
    private JPanel addPatientsPanel=new JPanel();

    private  JPanel mapview=new JPanel();

    private JButton viewP,viewDr;

    private JLabel title=new JLabel("Hospital A&E Reception");
    private JLabel gap= new JLabel();

    private bedMap bedmap = new bedMap();

    public mainPage() throws SQLException, URISyntaxException, IOException {

        viewP=new JButton("View all patients");
        viewDr= new JButton("View all doctors");

        //view patients
        viewP.addActionListener(actionEvent -> {
            try {
                patientsView view= new patientsView();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

        //view drs
        viewDr.addActionListener(actionEvent -> {

            try {
                setDoctorToPatient setDtP=new setDoctorToPatient();
                doctorView dr = new doctorView();
            } catch (SQLException | IOException | URISyntaxException e) {
                e.printStackTrace();
            }

        });

        //add buttons to button panel
        buttonPanel.add(title);
        buttonPanel.add(gap);
        buttonPanel.add(viewP);
        buttonPanel.add(viewDr);

        //title style
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(new Color(252,247,248));
        title.setFont(new Font("Raleway Light", Font.PLAIN, 42));

        //style button panel
        buttonPanel.setBorder(new EmptyBorder(30,0,0,0));
        buttonPanel.setPreferredSize(new Dimension(550,350));
        buttonPanel.setMaximumSize(new Dimension(450,550));

        //add button panel to add patients panel
        addPatientsPanel.add(buttonPanel);

        //styling panel and refresh page
        stylePanel();

        //add the map n patient panel to main panel
        mainPanel.add(addPatientsPanel);
        mapview=bedmap.getBedsPanel();

        mainPanel.add(mapview);
    }


    public JPanel getMainPanel(){
        return mainPanel;
    }

    private void stylePanel(){
        viewP.setFont(new Font("Raleway Light",Font.PLAIN,15));
        viewDr.setFont(new Font("Raleway Light",Font.PLAIN,15));

        buttonPanel.setBackground(new Color(44, 44, 88));
        addPatientsPanel.setBackground(new Color(44, 44, 88));
        addPatientsPanel.setBorder(new LineBorder(new Color(163,22,33),2));

        //to refresh the page, itll refresh when mouse is in panel
        mainPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                mainPanel.remove(mapview);
                try {
                    mapview=new bedMap().getBedsPanel();
                    mainPanel.add(mapview);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (URISyntaxException | IOException e) {
                    e.printStackTrace();
                }
                mainPanel.revalidate();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

}
