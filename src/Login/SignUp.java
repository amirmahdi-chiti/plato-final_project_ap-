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
import sun.security.util.Password;

public class SignUp {

    public static Pane createPane() {
        GridPane gridPane = new GridPane();
        Image image = null;
        try {
            image = new Image(new FileInputStream("image\\signup.png"));

        } catch (FileNotFoundException ex) {
        }
        gridPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        Text text1 = new Text("Username");
        text1.setFill(Color.WHITE);
        text1.setStyle("-fx-font: 30 arial;");
        Text text4 = new Text("CREATE NEW ACOUNT");
        text4.setFill(Color.WHITE);
        text4.setStyle("-fx-font: 40 arial;");
        Text hideText = new Text();
        hideText.setFill(Color.WHITE);
        hideText.setStyle("-fx-font: 40 arial;");
        Text text2 = new Text("Email");
        Text text = new Text("Password");
        text2.setFill(Color.WHITE);
        text2.setStyle("-fx-font: 30 arial;");
        text.setFill(Color.WHITE);
        text.setStyle("-fx-font: 30 arial;");
        TextField textField1 = new TextField();
        textField1.setPrefWidth(400);
        textField1.setPrefHeight(50);
        TextField textField4 = new TextField();
        textField4.setPrefWidth(400);
        textField4.setPrefHeight(50);
        TextField textField2 = new TextField();
        textField2.setPrefWidth(400);
        textField2.setPrefHeight(50);
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(400);
        passwordField.setPrefHeight(50);
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("name of first teacher");
        comboBox.getItems().add("your best friend");
        comboBox.getItems().add("your favorite color");
        comboBox.setPrefWidth(150);
        comboBox.setPrefHeight(50);
        Button button1 = new Button("sign up");
        button1.setOnAction((event) -> {
            boolean bool = new Sql().insert(textField1.getText(), textField2.getText(), (String) comboBox.getValue(), textField4.getText(), passwordField.getText());
            if(bool)hideText.setText("The Account Was Successfully Created");
            else hideText.setText("Please enter the information correctly");
        });
        button1.setStyle("-fx-background-color: #F66565; ");
        button1.setFont(new Font("arial", 30));
        button1.setTextFill(Color.WHITE);
        gridPane.setPadding(new Insets(10, 15, 10, 15));
        gridPane.setVgap(40);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(text4, 1, 0);
        gridPane.add(text1, 0, 1);
        gridPane.add(textField1, 1, 1);
        gridPane.add(text2, 0, 2);
        gridPane.add(textField2, 1, 2);
        gridPane.add(text, 0, 3);
        gridPane.add(passwordField, 1, 3);
        gridPane.add(comboBox, 0, 4);
        gridPane.add(textField4, 1, 4);
        gridPane.add(button1, 1, 5);
        gridPane.add(hideText, 1, 6);
        return gridPane;
    }
}
