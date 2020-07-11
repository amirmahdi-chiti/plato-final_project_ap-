package Login;

import Chat.ChatClient;
import Chat.messagePane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    public static Scene scene;
    public static Pane temp;
    private ChatClient client;
    public static Stage originalStage;

    @Override
    public void init() throws Exception {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        this.client = new ChatClient("localhost", Server.ServerMain.PORT);
        client.connect();
    }

    @Override
    public void start(Stage stage) {
        originalStage = stage;
        Text text1 = new Text("Username");
        text1.setFill(Color.WHITE);
        text1.setStyle("-fx-font: 24 arial;");
        Text text5 = new Text("Forgot Username/Password");
        text5.setFill(Color.WHITE);
        text5.setStyle("-fx-font: 20 arial;");
        text5.setOnMousePressed((event) -> {
            scene.setRoot(new Recovery().page());
        });
        Text text2 = new Text("Password");
        text2.setFill(Color.WHITE);
        text2.setStyle("-fx-font: 24 arial;");
        TextField textField1 = new TextField();
        textField1.setPrefWidth(300);
        textField1.setPrefHeight(35);
        TextField textField2 = new TextField();
        textField2.setPrefWidth(300);
        textField2.setPrefHeight(35);
        Button button1 = new Button("sign in");
        button1.setStyle("-fx-background-color: #F66565; ");
        button1.setFont(new Font("arial", 20));
        button1.setTextFill(Color.WHITE);
        Text text4 = new Text("");
        button1.setOnAction((event) -> {
            try {
                if (client.login(textField1.getText(), textField2.getText())) {
                    System.out.println("wellcome to chat");
                    //scene.setRoot(new messagePane(client,textField1.getText()).sceneChat());
                    scene.setRoot(new GamePage(client, textField1.getText()).createGamePage());
                } else {
                    System.out.println("im sory ...");
                    text4.setText("Your Username Or Password Is Incorrect");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        BorderPane borderPane = new BorderPane();
        Image image = null;
        Image image2 = null;
        try {
            image = new Image(new FileInputStream("image\\login.png"));
            image2 = new Image(new FileInputStream("image\\login2.png"));

        } catch (FileNotFoundException ex) {
        }

        GridPane gridPane = new GridPane();
        GridPane gridPane1 = new GridPane();
        gridPane.setBackground(new Background(new BackgroundImage(image2, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        gridPane1.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        Text text = new Text("WELLCOME TO PLATO");
        Text text3 = new Text("Final Project Of Advanced Programming ...");
        Button button = new Button("sign up");
        button.setOnAction((event) -> {
            scene.setRoot(SignUp.createPane());
        });
        text.setFill(Color.WHITE);
        text3.setFill(Color.WHITE);
        text.setStyle("-fx-font: 24 arial;");
        text3.setStyle("-fx-font: 24 arial;");
        button.setStyle("-fx-background-color: #F66565; ");
        button.setFont(new Font("arial", 20));
        button.setTextFill(Color.WHITE);
        gridPane1.setPadding(new Insets(10, 15, 10, 15));
        gridPane1.setVgap(35);
        gridPane1.setHgap(20);
        borderPane.setCenter(gridPane1);
        borderPane.setRight(gridPane);
        gridPane.setPrefWidth(600);
        gridPane.setPadding(new Insets(10, 15, 10, 15));
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        
        text4.setStyle("-fx-font: 20 arial;");
        text4.setFill(Color.WHITE);
        
        hBox.getChildren().addAll(button1, text5);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane1.setAlignment(Pos.CENTER);
        gridPane.add(text1, 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(text2, 0, 1);
        gridPane.add(textField2, 1, 1);
        gridPane.add(hBox, 1, 2);
        gridPane.add(text4, 1, 3);
        gridPane1.add(text, 0, 0);
        gridPane1.add(text3, 0, 1);
        gridPane1.add(button, 0, 2);
        temp = borderPane;
        scene = new Scene(borderPane);
        originalStage.setTitle("PLATO");
        originalStage.setFullScreen(true);
        originalStage.setScene(scene);
        stage.setOnCloseRequest((event) -> {
            System.out.println("hehehhehe");
            try {
                client.logoff();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("hehehhehe2");

        });
        originalStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
