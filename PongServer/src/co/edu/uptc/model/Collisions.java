package co.edu.uptc.model;

import java.awt.Rectangle;

import co.edu.uptc.Utils.Values;
import co.edu.uptc.pojos.Element;

public class Collisions {
    private Rectangle ball;
    private Rectangle racketOne;
    private Rectangle racketTwo;
    private int numberScreens;

    public Collisions(Element ballE, Element racketOneE, Element racketTwoE, int numberScreens){
        ball = new Rectangle(
            ballE.getX(),
            ballE.getY(),
            ballE.getWidth(),
            ballE.getHeight()
        );
        racketOne = new Rectangle(
            racketOneE.getX(),
            racketOneE.getY(),
            racketOneE.getWidth(),
            racketOneE.getHeight()
        );
        this.numberScreens = numberScreens;
        racketTwo = new Rectangle(
            racketTwoE.getX()*this.numberScreens,
            racketTwoE.getY(),
            racketTwoE.getWidth(),
            racketTwoE.getHeight()
        );
    }

    public boolean isCollisionOne(Element ballE, Element racket, int ballPosition){
        ball.setLocation((Values.widthWindow*ballPosition)+ballE.getX(), ballE.getY());
        racketOne.setLocation(racket.getX(), racket.getY());
        if (ball.intersects(racketOne)) {
           return true;
        }
        return false;
    }

    public boolean isCollisionTwo(Element ballE, Element racket, int ballPosition){
        ball.setLocation((Values.widthWindow*ballPosition)+ballE.getX(), ballE.getY());
        racketTwo.setLocation(Values.widthWindow*(numberScreens-1)+racket.getX(), racket.getY());
        if (ball.intersects(racketTwo)) {
           return true;
        }
        return false;
    }
}
