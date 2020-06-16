
package Login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Recovery {
    public Pane page(){
         GridPane gridPane = new GridPane();
        Image image = null;
        try {
            image = new Image(new FileInputStream("image\\signup.png"));

        } catch (FileNotFoundException ex) {
        }
        gridPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        
        Text text4 = new Text("RECOVER PASSWORD\\USERNAME");
        text4.setFill(Color.WHITE);
        text4.setStyle("-fx-font: 40 arial;");
        Text text2 = new Text("Email");
        text2.setFill(Color.WHITE);
        text2.setStyle("-fx-font: 30 arial;");
        Text text3 = new Text("Selected Question");
        text3.setFill(Color.WHITE);
        text3.setStyle("-fx-font: 24 arial;");
        Text text5 = new Text("Answer");
        text5.setFill(Color.WHITE);
        text5.setStyle("-fx-font: 30 arial;");
        Text hide = new Text();
        hide.setFill(Color.WHITE);
        hide.setStyle("-fx-font: 30 arial;");
        
        TextField textField4 = new TextField();
        textField4.setPrefWidth(400);
        textField4.setPrefHeight(50);
        TextField textField2 = new TextField();
        textField2.setPrefWidth(400);
        textField2.setPrefHeight(50);
        
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("name of first teacher");
        comboBox.getItems().add("your best friend");
        comboBox.getItems().add("your favorite color");
        comboBox.setPrefWidth(400);
        comboBox.setPrefHeight(50);
        Button button1 = new Button("recover");
        button1.setOnAction((event) -> {
            String[] str = new String[2];
            System.out.println((String)comboBox.getValue());
            str = new Sql().recover(textField2.getText(), (String)comboBox.getValue(), textField4.getText());
            if(str[1].equals("0")){
                 hide.setText("This User Does Not Exist ...");
            }
            else
                hide.setText("Your user name =  " + str[0] + " & password =  " + str[1]);
        });
        button1.setStyle("-fx-background-color: #F66565; ");
        button1.setFont(new Font("arial", 30));
        button1.setTextFill(Color.WHITE);
        gridPane.setPadding(new Insets(10, 15, 10, 15));
        gridPane.setVgap(40);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(text4, 1, 1);
        gridPane.add(text2, 0, 2);
        gridPane.add(textField2, 1, 2);
        gridPane.add(comboBox, 1, 3);
        gridPane.add(textField4, 1, 4);
        gridPane.add(text3, 0, 3);
        gridPane.add(text5, 0, 4);
        gridPane.add(button1, 1, 5);
        gridPane.add(hide, 1, 6);
        return gridPane;
    }
}
