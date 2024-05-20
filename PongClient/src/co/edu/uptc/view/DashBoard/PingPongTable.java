package co.edu.uptc.view.DashBoard;

import javax.swing.*;

import co.edu.uptc.pojos.Element;

import java.awt.*;

public class PingPongTable extends JPanel {
    private Element ball;
    private Element racket;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(new Color(0x39a5f0));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(new Color(0x15368b));
        g.fillRect(0, 30, getWidth(), getHeight()-60);
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 30, this.getWidth(), 10);
        g.fillRect(0, this.getHeight()/2, this.getWidth(), 5);

        g.fillRect(0, this.getHeight()-40, this.getWidth(), 10);

        if(ball!=null){
            g.setColor(ball.getColor());
            g.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
        }
        if(racket!=null){
            g.setColor(racket.getColor());
            g.fillRect(racket.getX(), racket.getY(), racket.getWidth(), racket.getHeight());
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
