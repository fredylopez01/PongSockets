package co.edu.uptc.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import co.edu.uptc.Utils.Values;
import co.edu.uptc.pojos.Element;

public class MyPanel extends JPanel {
    private Element ball;
    private Element racketOne;
    private Element racketTwo;

    public MyPanel(){
    
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, Values.widthWindow, Values.heightWindow-40);
        if (ball != null) {
            g.setColor(ball.getColor());
            g.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());            
        }
        if (racketOne != null) {
            g.setColor(racketOne.getColor());
            g.fillRect(racketOne.getX(), racketOne.getY(), racketOne.getWidth(), racketOne.getHeight());
        }
        if (racketTwo != null) {
            g.setColor(racketTwo.getColor());
            g.fillRect(racketTwo.getX(), racketTwo.getY(), racketTwo.getWidth(), racketTwo.getHeight());
        }
    }
    public Element getBall() {
        return ball;
    }
    public void setBall(Element ball) {
        this.ball = ball;
    }
    public Element getRacketOne() {
        return racketOne;
    }
    public void setRacketOne(Element racketOne) {
        this.racketOne = racketOne;
    }
    public Element getRacketTwo() {
        return racketTwo;
    }
    public void setRacketTwo(Element racketTwo) {
        this.racketTwo = racketTwo;
    }

}
