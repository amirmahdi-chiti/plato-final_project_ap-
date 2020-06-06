
package test048;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Dice {
    Random rd = new Random();
    FileInputStream one = null;
    FileInputStream two = null;
    FileInputStream three = null;;
    FileInputStream four = null;
    FileInputStream five = null;
    FileInputStream six = null;
    FileInputStream dice = null;
    static int num;
    Rectangle rectangle;
    public Dice() {
        try{
            
             dice = new FileInputStream("image\\dice.png");
        }catch(FileNotFoundException e){
            System.out.println("ridi");
        }
        rectangle = new Rectangle(150, 150);
        rectangle.setFill(new ImagePattern(new Image(dice)));
    }
    public Shape createDice(){
        rectangle.setOnMousePressed((event) -> {
            createRandom();
            
        });
       return rectangle; 
        
    }
    public int  createRandom(){
        try{
            one = new FileInputStream("image\\1.png");
            two = new FileInputStream("image\\2.png");
            three = new FileInputStream("image\\3.png");
            four = new FileInputStream("image\\4.png");
            five = new FileInputStream("image\\5.png");
            six = new FileInputStream("image\\6.png");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    
            num = rd.nextInt(6)+1;
            System.out.println(num);
        if(num==1){
            rectangle.setFill(new ImagePattern(new Image(one)));
        }
        if(num==2){
            rectangle.setFill(new ImagePattern(new Image(two)));
        }if(num==3){
            rectangle.setFill(new ImagePattern(new Image(three)));
        }if(num==4){
            rectangle.setFill(new ImagePattern(new Image(four)));
        }if(num==5){
            rectangle.setFill(new ImagePattern(new Image(five)));
        }if(num==6){
            rectangle.setFill(new ImagePattern(new Image(six)));
        }
        return num;
    }
    
}   

