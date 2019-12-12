import UI.MenuBar;
import UI.mainPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;

public class Main {
    public static void main(String args[]) throws SQLException, URISyntaxException, IOException {

        JFrame mainFrame=new JFrame("Hospital");
        JPanel p;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(screenSize.width, screenSize.height);
        mainFrame.setJMenuBar(new MenuBar());
        mainPage main=new mainPage();
        p=main.getMainPanel();
        mainFrame.getContentPane().add(p);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(3);
    }
}

