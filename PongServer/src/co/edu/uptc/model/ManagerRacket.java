package co.edu.uptc.model;

import java.awt.Color;

import co.edu.uptc.Utils.Values;
import co.edu.uptc.pojos.Element;

public class ManagerRacket {
    private Element racket;

    public ManagerRacket(int player){
        createRacket();
        racketPosition(player);
    }
    public void createRacket(){
        racket = new Element();
        racket.setWidth(Values.racketWidth);
        racket.setHeight(Values.racketHeight);
        racket.setType(2);
        racket.setColor(new Color(0x27CF2E));
    }
    public void racketPosition(int player){
        if(player == 1){
            racket.setX(3);
        } else {
            racket.setX(Values.widthWindow-racket.getWidth()-19);
        }
        racket.setY(Values.heightWindow/2-racket.getHeight()/2-15);
    }
    public void down(){
        if(racket.getY()+racket.getWidth()<Values.heightWindow-racket.getHeight()-racket.getHeight()/2+10){
            racket.setY(racket.getY()+10);
        }
    }
    public void up(){
        if(racket.getY()>0){
            racket.setY(racket.getY()-10);
        }
    }
    public int getType(){
        return racket.getType();
    }
    public void setType(int type){
        racket.setType(type);
    }
    public Element getRacket() {
        return racket;
    }
    public void setRacket(Element racket) {
        this.racket = racket;
    }
}
