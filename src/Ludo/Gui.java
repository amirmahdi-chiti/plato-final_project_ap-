
package Ludo;

import static Ludo.Main.turn;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Gui {
    static Rectangle rectangle = new Rectangle(200, 100,Color.BLUE);
    static Rectangle dice = (Rectangle) new Dice().createDice();
    public static Shape turn(){
        Type type = Main.turn;
        if(type.equals(Type.BLUE))rectangle.setFill(Color.BLUE);
        if(type.equals(Type.GREEN))rectangle.setFill(Color.CHARTREUSE);
        if(type.equals(Type.RED))rectangle.setFill(Color.RED);
        if(type.equals(Type.YELLOW))rectangle.setFill(Color.YELLOW);
        return rectangle;
    }
    public static Node createRightBox(){
        VBox VBox = new VBox();
        VBox.getChildren().add(rectangle);
        VBox.getChildren().add(dice);
        VBox.setAlignment(Pos.CENTER_RIGHT);
        VBox.setSpacing(80);
        VBox.setPadding(new Insets(0, 100, 0, 0));
        return VBox;
    }
}
