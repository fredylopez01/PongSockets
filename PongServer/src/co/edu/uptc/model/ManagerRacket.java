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
        racket.setColor(Color.WHITE);
        racket.setSpeed(Values.racketSpeed);
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
        if(racket.getY()+racket.getHeight()+racket.getSpeed()<Values.heightWindow-39){
            racket.setY(racket.getY()+racket.getSpeed());
        }
    }
    public void up(){
        if(racket.getY()>0){
            racket.setY(racket.getY()-+racket.getSpeed());
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
