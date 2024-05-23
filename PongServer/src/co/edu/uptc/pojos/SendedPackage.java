package co.edu.uptc.pojos;

import java.io.Serializable;

public class SendedPackage implements Serializable {
    private Element ball;
    private Element racket;
    private boolean isButtonPlay;
    private boolean isGameOver;

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

    
}
