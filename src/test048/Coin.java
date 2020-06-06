package test048;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Coin {

    static double redius = 30;
    Type type;
    ImagePattern image;
     private int number;
     private int row;
     private boolean current;
    public Coin(Type type,int num) {
        setNumber(num);
        number = -6;
        current = false;
        FileInputStream red = null;
        FileInputStream blue = null;
        FileInputStream green = null;
        FileInputStream yellow = null;
        try{
        red = new FileInputStream("image\\nutRED.png");
         blue = new FileInputStream("image\\nutBLUE.png");
        green = new FileInputStream("image\\nutGREEN.png");
        yellow = new FileInputStream("image\\nutYELLOW.png");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        this.type = type;
        if (type.equals(Type.RED)) {
            image = new ImagePattern(new Image(red));
            row = 1;
        } else if (type.equals(Type.YELLOW)) {
           image = new ImagePattern(new Image(yellow));
           row = 3;
        } else if (type.equals(Type.GREEN)) {
            row = 2;
            image = new ImagePattern(new Image(green));
        } else if (type.equals(Type.BLUE)) {
            row = 0;
            image = new ImagePattern(new Image(blue));
        }
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int getRow() {
        return row;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
    
}

enum Type {
    RED,
    BLUE,
    YELLOW,
    GREEN
}
