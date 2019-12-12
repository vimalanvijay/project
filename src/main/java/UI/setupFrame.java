package UI;

import javax.swing.*;
import java.awt.*;

public class setupFrame {
    private JDialog frame;


    public void setFrame(){
        frame = new JDialog();
        frame.revalidate();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        frame.setJMenuBar(new MenuBar());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(2);
    }

    public JDialog getFrame(){
        return frame;
    }

    public void add(JPanel mainPanel){
        frame.add(mainPanel);
    }

    public void setTitle(String title){
        frame.setTitle(title);
    }

    public void setBackground(Color colour){
        frame.setBackground(colour);
    }
}
