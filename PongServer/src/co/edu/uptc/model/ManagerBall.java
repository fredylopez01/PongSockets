package co.edu.uptc.model;

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
        this.element.setWidth(20);
        this.element.setHeight(20);
        this.element.setType((int)(Math.random()*5));
        this.element.setSpeed((int)(Math.random()*(30-3+1)+3));
        this.element.setActive(true);
    }
    public void direction(){
        int directionR = (int)(Math.random()*3);
        switch (directionR) {
            case 0 -> changeDirection(DirectionEnum.LEFT, DirectionEnum.UP);
            case 1 -> changeDirection(DirectionEnum.RIGHT, DirectionEnum.UP);
            case 2 -> changeDirection(DirectionEnum.RIGHT, DirectionEnum.DOWN);
            case 3 -> changeDirection(DirectionEnum.LEFT, DirectionEnum.DOWN);
            default -> changeDirection(DirectionEnum.LEFT, DirectionEnum.DOWN);
        }
    }
    private void changeDirection(DirectionEnum left, DirectionEnum down) {
        this.element.setX(10);
        this.element.setY(13);
        horizontalDirection = left;
        verticalDirection = down;
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
        if(element.getY()+element.getHeight()>=Values.heightWindow){
            verticalDirection=DirectionEnum.UP;
        }
        element.setY(element.getY()+element.getSpeed());
    }
    public void up(){
        if(element.getY()-element.getHeight()/2<=0){
            verticalDirection=DirectionEnum.DOWN;
        }
        element.setY(element.getY()-element.getSpeed());
    }
    public void left(){
        if(element.getX()-element.getWidth()/2<=0-element.getWidth()){
            horizontalDirection = DirectionEnum.RIGHT;
        }
        element.setX(element.getX()-element.getSpeed());
    }
    public void right(){
        if(element.getX()+element.getWidth()/2>=Values.widthWindow){
            horizontalDirection = DirectionEnum.LEFT;
        }
        element.setX(element.getX()+element.getSpeed());
    }
    public boolean isActive(){
        return element.isActive();
    }
    public void setActive(boolean isActive){
        element.setActive(isActive);
    }
    public void stopThread(){
        element.setActive(false);
        statusThread = false;
    }
    public Element getElement(){
        return element;
    }
}
