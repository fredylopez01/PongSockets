package co.edu.uptc.view.DashBoard;

import javax.swing.*;

import co.edu.uptc.Utils.MyUtils;
import co.edu.uptc.pojos.Element;

import java.awt.*;

public class PingPongTable extends JPanel {
    private Element ball;
    private Element racket;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(new Color(0x15368b));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), 10);
        g.fillRect(0, this.getHeight()/2, this.getWidth(), 5);

        g.fillRect(0, this.getHeight()-10, this.getWidth(), 10);

        if(ball!=null){
            g.setColor(ball.getColor());
            Rectangle rectangle = MyUtils.calculatePositions(ball, this.getBounds());
            g.fillOval((int)rectangle.getX(), (int)rectangle.getY(), (int)rectangle.getWidth(), (int)rectangle.getHeight());
        }
        if(racket!=null){
            g.setColor(racket.getColor());
            Rectangle rectangle = MyUtils.calculatePositions(racket, this.getBounds());
            g.fillRect((int)rectangle.getX(), (int)rectangle.getY(), (int)rectangle.getWidth(), (int)rectangle.getHeight());
        }
    }
    public void reset(){
        if(ball != null && racket != null){
            ball.setX(-100);
            ball.setY(-100);
            racket.setX(-100);
            racket.setY(-100);
        }
    }
    public Element getBall() {
        return ball;
    }
    public void setBall(Element ball) {
        this.ball = ball;
    }
    public Element getRacket() {
        return racket;
    }
    public void setRacket(Element racket) {
        this.racket = racket;
    }
}
