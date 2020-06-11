package Tic_Tac_Toe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

class Cell extends Rectangle {

    Nut nut;

    public Cell() {
        super(200, 200);
        setStroke(Color.BLACK);
        setFill(Color.ALICEBLUE);
        setOnMousePressed((event) -> {
            Logic.click(this,null);
            if(Logic.isFinish()==2){
            new Computer().play();
            }
            
        });
    }

    @Override
    public String toString() {
        return  "" + nut ;
    }

    

    
    
}

enum Nut {
    O,
    X
    
}
