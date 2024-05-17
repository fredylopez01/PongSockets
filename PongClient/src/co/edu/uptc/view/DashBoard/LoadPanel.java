package co.edu.uptc.view.DashBoard;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
        background = new ImageIcon(getClass().getResource("/co/edu/uptc/view/images/load.gif")).getImage();
        g.drawImage(background, 0, 0, 850, 600, this);
        setOpaque(false);
        super.paint(g);
    }
}
