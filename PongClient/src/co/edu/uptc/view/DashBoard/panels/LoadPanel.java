package co.edu.uptc.view.DashBoard.panels;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.Utils.Values;

import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

public class LoadPanel extends JPanel {
    private JLabel lbl;
    private Image background;

    public LoadPanel(){
        initComponents();
    }

    private void initComponents() {
        lbl = new JLabel("Cargando...");
        lbl.setFont(new Font("Currier", Font.BOLD, 20));
        this.setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(lbl);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void paint(Graphics g) {
        background = new ImageIcon(getClass().getResource(Values.pathImgLoad)).getImage();
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        setOpaque(false);
        super.paint(g);
    }
}
