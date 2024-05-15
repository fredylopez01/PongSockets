package co.edu.uptc.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import co.edu.uptc.pojos.Element;

public class MyPanel extends JPanel {
    private Element ball;

    public MyPanel(){
    
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ball.getColor());
        g.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
    }

    public Element getBall() {
        return ball;
    }

    public void setBall(Element ball) {
        this.ball = ball;
    }
}
