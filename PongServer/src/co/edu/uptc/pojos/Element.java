package co.edu.uptc.pojos;

import java.awt.Color;

public class Element {
    private int x;
    private int y;
    private int width;
    private int height;
    private int type;
    private int speed;
    private boolean isOnScreen;
    private Color color;
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public boolean isOnScreen() {
        return isOnScreen;
    }
    public void setActive(boolean isOnScreen) {
        this.isOnScreen = isOnScreen;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    
    
}
