package co.edu.uptc.view.DashBoard.panels;

import java.awt.event.ActionListener;
import javax.swing.*;

import co.edu.uptc.Utils.Values;
import co.edu.uptc.view.DashBoard.utils.ShappedButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

public class EndPanel extends JPanel{
    private String routeImage;
    private JButton btnContinue;
    private Image image;

    public EndPanel(ActionListener listener) {
        routeImage = Values.pathImgEndGame;
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
        routeImage = Values.pathImgGameOver;
        repaint();
    }
    public void endGame(){
        routeImage = Values.pathImgEndGame;
        repaint();
    }
    public void youWin(){
        routeImage = Values.pathImgYouWin;
        repaint();
    }

    private void initComponents(ActionListener listener) {
        Font font = new Font("Arial", Font.BOLD, 18);
        btnContinue = new JButton("Finalizar");
        btnContinue.setActionCommand("endGame");
        btnContinue.addActionListener(listener);
        btnContinue.setFont(font);
        styleButton();
    }

    private void addComponents() {
        this.setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnContinue);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleButton(){
        btnContinue.setUI(new ShappedButton(new Color(0x121f40)));
        btnContinue.setForeground(Color.WHITE);
        btnContinue.setContentAreaFilled(false);
        btnContinue.setBorderPainted(false);
        btnContinue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnContinue.setMargin(new Insets(3, 10, 3, 10));
        btnContinue.setFocusPainted(false);
        btnContinue.setFocusable(false);
    }
    
}