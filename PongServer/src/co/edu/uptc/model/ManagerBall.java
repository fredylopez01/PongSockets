package co.edu.uptc.model;

import java.awt.Color;

import co.edu.uptc.Utils.Values;
import co.edu.uptc.pojos.DirectionEnum;
import co.edu.uptc.pojos.Element;

public class ManagerBall {
    private Element element;
    private DirectionEnum horizontalDirection;
    private DirectionEnum verticalDirection;
    public boolean statusThread;

    public ManagerBall(){
        createElement();
        direction();
        statusThread = true;
    }
    public void createElement(){
        this.element = new Element();
        this.element.setWidth(Values.lengthBall);
        this.element.setHeight(Values.lengthBall);
        this.element.setSpeed((int)(Math.random()*(Values.maxSpeedBall-Values.minSpeedBall+1)+Values.minSpeedBall));
        this.element.setActive(true);
        this.element.setColor(Color.WHITE);
        this.element.setType(1);
    }
    public void direction(){
        int directionR = (int)(Math.random()*3);
        switch (directionR) {
            case 1 -> changeDirection(DirectionEnum.RIGHT, DirectionEnum.UP);
            case 2 -> changeDirection(DirectionEnum.RIGHT, DirectionEnum.DOWN);
            default -> changeDirection(DirectionEnum.RIGHT, DirectionEnum.DOWN);
        }
    }
    private void changeDirection(DirectionEnum horizontal, DirectionEnum vertical) {
        this.element.setX(Values.widthWindow/2);
        this.element.setY(Values.heightWindow/2);
        horizontalDirection = horizontal;
        verticalDirection = vertical;
    }
    public void move(){
        if(horizontalDirection==DirectionEnum.LEFT){
            left();
        } else if (horizontalDirection==DirectionEnum.RIGHT) {
            right();
        }
        if(verticalDirection==DirectionEnum.UP){
            up();
        } else if(verticalDirection==DirectionEnum.DOWN){
            down();
        }
    }
    public void down(){
        if(element.getY()+element.getSpeed()>Values.heightWindow-60){
            verticalDirection=DirectionEnum.UP;
        }
        element.setY(element.getY()+element.getSpeed());
    }
    public void up(){
        if(element.getY()-element.getSpeed()<=0){
            verticalDirection=DirectionEnum.DOWN;
        }
        element.setY(element.getY()-element.getSpeed());
    }
    public void left(){
        if(element.getX()-element.getSpeed()<=0-element.getWidth()){
            element.setX(Values.widthWindow);
            this.setIsOnScreen(false);
        }
        element.setX(element.getX()-element.getSpeed());
    }
    public void right(){
        if(element.getX()+element.getSpeed()>=Values.widthWindow){
            element.setX(0);
            this.setIsOnScreen(false);
        }
        element.setX(element.getX()+element.getSpeed());
    }
    public void opposite() {
        if(horizontalDirection==DirectionEnum.LEFT){
            horizontalDirection=DirectionEnum.RIGHT;
        } else {
            horizontalDirection=DirectionEnum.LEFT;
        }
    }
    public boolean isOnScreen(){
        return element.isOnScreen();
    }
    public void setIsOnScreen(boolean isOnScreen){
        element.setActive(isOnScreen);
    }
    public void stopThread(){
        element.setActive(false);
        statusThread = false;
    }
    public Element getElement(){
        return element;
    }
    public DirectionEnum getHorizontalDirection() {
        return horizontalDirection;
    }
    public void setHorizontalDirection(DirectionEnum horizontalDirection) {
        this.horizontalDirection = horizontalDirection;
    }
    public void setVerticalDirection(DirectionEnum verticalDirection) {
        this.verticalDirection = verticalDirection;
    }
    public DirectionEnum getVerticalDirection() {
        return verticalDirection;
    }
}
