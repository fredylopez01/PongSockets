package co.edu.uptc.pojos;

import java.io.Serializable;

public class SendedPackage implements Serializable {
    private Element ball;
    private Element racketOne;
    private Element racketTwo; 
    private boolean isButtonPlay;
    private boolean isGameOver;
    private int ballPosition;
    private int firstPlayer;
    private int lastPlayer;
    private int myPosition;
    public int getMyPosition() {
        return myPosition;
    }
    public void setMyPosition(int myPosition) {
        this.myPosition = myPosition;
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
    public boolean isButtonPlay() {
        return isButtonPlay;
    }
    public void setButtonPlay(boolean isButtonPlay) {
        this.isButtonPlay = isButtonPlay;
    }
    public boolean isGameOver() {
        return isGameOver;
    }
    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }
    public int getBallPosition() {
        return ballPosition;
    }
    public void setBallPosition(int ballPosition) {
        this.ballPosition = ballPosition;
    }
    public int getFirstPlayer() {
        return firstPlayer;
    }
    public void setFirstPlayer(int firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
    public int getLastPlayer() {
        return lastPlayer;
    }
    public void setLastPlayer(int lastPlayer) {
        this.lastPlayer = lastPlayer;
    }
}