package co.edu.uptc.view.DashBoard;

import javax.swing.*;
import java.awt.*;

public class PingPongTable extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(8)); 
        g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
    }
   
}
