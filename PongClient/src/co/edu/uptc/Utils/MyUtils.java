package co.edu.uptc.Utils;

import java.awt.Rectangle;

import co.edu.uptc.pojos.Element;

public class MyUtils {

    public static void sleep(int duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Rectangle calculatePositions(Element element, Rectangle window){
        int xP = (element.getX()*100)/Values.widthWindow;
        int yP = (element.getY()*100)/Values.heightWindow;
        int wP = (element.getWidth()*100)/Values.widthWindow;
        int hP = (element.getHeight()*100)/Values.heightWindow;
        return new Rectangle(
            (int)((xP*window.getWidth())/100), 
            (int)((yP*window.getHeight())/100),
            (int)((wP*window.getWidth())/100), 
            (int)((hP*window.getHeight())/100)
        );
    }
}
