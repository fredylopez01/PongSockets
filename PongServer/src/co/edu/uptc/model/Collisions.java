package co.edu.uptc.model;

import java.awt.Rectangle;

import co.edu.uptc.Utils.Values;
import co.edu.uptc.pojos.DirectionEnum;
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

    public boolean isCollisionOne(Element ballE, Element racket, int ballPosition,  DirectionEnum directionY){
        ball.setLocation((Values.widthWindow*ballPosition)+ballE.getX(), ballE.getY());
        racketOne.setLocation(racket.getX(), racket.getY());
        if (ball.intersects(racketOne)) {
            changeDirection(ballE, racketOne, directionY);
            return true;
        }
        return false;
    }

    public boolean isCollisionTwo(Element ballE, Element racket, int ballPosition, DirectionEnum directionY){
        ball.setLocation((Values.widthWindow*ballPosition)+ballE.getX(), ballE.getY());
        racketTwo.setLocation(Values.widthWindow*(numberScreens-1)+racket.getX(), racket.getY());
        if (ball.intersects(racketTwo)) {
            changeDirection(ballE, racketTwo, directionY);
            return true;
        }
        return false;
    }

    private void changeDirection(Element ballE, Rectangle racket,  DirectionEnum directionY){
        Rectangle intersection = racket.intersection(ball);
        int yIntersection = (int) (racket.getY()+racket.getHeight()/3);
        if(intersection.getY() < yIntersection){
            ballE.setSpeedY(ballE.getSpeedY()-5);
            if(ballE.getSpeedY()<0){
                directionY = DirectionEnum.UP;
                ballE.setSpeedY(ballE.getSpeedY()*-1);
            }
            ballE.setSpeedX(ballE.getSpeedX()+2);
        } else if(intersection.getY() > yIntersection+(racket.getHeight()/3)){
            ballE.setSpeedY(ballE.getSpeedY()+5);
            directionY = DirectionEnum.DOWN;
            ballE.setSpeedX(ballE.getSpeedX()+2);
        }
        System.out.println(ballE.getSpeedY());
    }
}
