package co.edu.uptc.view.DashBoard.panels;

import java.awt.event.ActionListener;
import javax.swing.*;

import co.edu.uptc.view.DashBoard.utils.ShappedButton;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

public class EndPanel extends JPanel{
    private String routeImage;
    private JButton btnContinue;
    private Image image;

    public EndPanel(ActionListener listener) {
        routeImage = "/co/edu/uptc/view/images/backImage.jpeg";
        initComponents(listener);
        addComponents();
    }
    public void paint(Graphics g) {
        image = new ImageIcon(getClass().getResource(routeImage)).getImage();
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        setOpaque(false);
        super.paint(g);
    }

    public void gameOver(){
        routeImage = "/co/edu/uptc/view/images/backImage.jpeg";
        repaint();
    }
    
    public void endGame(){
        routeImage = "/co/edu/uptc/view/images/backImage.jpeg";
        repaint();
    }

    public void youWin(){
        routeImage = "/co/edu/uptc/view/images/backImage.jpeg";
        repaint();
    }

    private void initComponents(ActionListener listener) {
        Font font = new Font("Arial", Font.BOLD, 18);
        btnContinue = new JButton("Conectar");
        btnContinue.setActionCommand("connect");
        btnContinue.addActionListener(listener);
        btnContinue.setFont(font);
        styleButton();
    }

    private void addComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        this.add(btnContinue, gbc);
    }

    public void styleButton(){
        btnContinue.setUI(new ShappedButton(new Color(0x1FFF00)));
        btnContinue.setForeground(Color.WHITE);
        btnContinue.setContentAreaFilled(false);
        btnContinue.setBorderPainted(false);
        btnContinue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnContinue.setMargin(new Insets(3, 10, 3, 10));
        btnContinue.setFocusPainted(false);
        btnContinue.setFocusable(false);
    }
    
}