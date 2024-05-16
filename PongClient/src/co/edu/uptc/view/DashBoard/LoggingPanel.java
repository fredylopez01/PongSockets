package co.edu.uptc.view.DashBoard;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

public class LoggingPanel extends JPanel{

    private JLabel lblIP;
    private JTextField txtIP;
    

    private JButton btnContinue;
    private Image image;

    public LoggingPanel(ActionListener listener) {
        initComponents(listener);
        addComponents();
    }
    public void paint(Graphics g) {
        image = new ImageIcon(getClass().getResource("/co/edu/uptc/view/images/backImage.jpeg")).getImage();
        g.drawImage(image, 0, 0, 850, 600, this);
        setOpaque(false);
        super.paint(g);
    }

    private void initComponents(ActionListener listener) {
        Font font = new Font("Arial", Font.BOLD, 16); 
        lblIP = new JLabel("IP Adress");
        lblIP.setFont(font);
        txtIP = new JTextField();
   

        btnContinue = new JButton("Continue");
        btnContinue.setActionCommand("continue");
        btnContinue.addActionListener(listener);
        btnContinue.setFont(font);
    
       
    }

    private void addComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(lblIP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        this.add(txtIP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(btnContinue, gbc);
    }

    public JTextField getTxtIP() {
        return txtIP;
    }
    public void setTxtIP(JTextField txtIP) {
        this.txtIP = txtIP;
    }
    
}