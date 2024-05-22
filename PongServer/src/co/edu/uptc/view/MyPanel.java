package co.edu.uptc.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import co.edu.uptc.Utils.Values;
import co.edu.uptc.pojos.Element;

public class MyPanel extends JPanel {
    private Element ball;
    private Element racketOne;
    private Element racketTwo;
    private int numberScreens;
    private int currentScreen;

    public MyPanel(){
        numberScreens = 1;
        currentScreen = 1;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = (Values.heightWindow/2)-(Values.heightWindow/2)/numberScreens;
        g.drawRect(0, y, Values.widthWindow, Values.heightWindow/numberScreens-40);
        if (ball != null) {
            g.setColor(ball.getColor());
            int xS = 0;
            if(currentScreen <= 0){
                currentScreen = numberScreens;
            } else if (currentScreen > numberScreens){
                currentScreen = 1;
            }
            xS = (Values.widthWindow/numberScreens)*(currentScreen-1);
            g.fillOval(xS+(ball.getX()/numberScreens), y+ball.getY()/numberScreens, ball.getWidth()/numberScreens, ball.getHeight()/numberScreens);            
        }
        for(int i = 0; i<numberScreens; i++){
            g.drawLine((Values.widthWindow/numberScreens)*i, 0, (Values.widthWindow/numberScreens)*i, Values.heightWindow);
        }
        if (racketOne != null) {
            g.setColor(racketOne.getColor());
            g.fillRect(racketOne.getX()/numberScreens, y+racketOne.getY()/numberScreens, racketOne.getWidth()/numberScreens, racketOne.getHeight()/numberScreens);
        }
        if (racketTwo != null) {
            g.setColor(racketTwo.getColor());
            g.fillRect(racketTwo.getX(), y+racketTwo.getY()/numberScreens, racketTwo.getWidth()/numberScreens, racketTwo.getHeight()/numberScreens);
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
    public int getNumberScreens() {
        return numberScreens;
    }
    public void setNumberScreens(int numberScreens) {
        this.numberScreens = numberScreens;
    }
    public int getCurrentScreen() {
        return currentScreen;
    }
    public void setCurrentScreen(int currentScreen) {
        this.currentScreen = currentScreen;
    }
    
}
