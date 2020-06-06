package test048;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAttribute;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Test048 extends Application {
    static Cell[][] board = new Cell[15][15];
    StackPane stackPane;
    Stage stage;
    BorderPane borderPane;
    GridPane gridPane;
    static Color colorBackground = Color.ALICEBLUE;
    public static void main(String[] args) {
        Board.initial();
        launch(args);
    }
    private EventHandler<MouseEvent> eventHandle(int i, int j) {

        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                Move.move(i, j);
            }
        };
        return event;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       stage = primaryStage;
        borderPane = new BorderPane();
       
        stackPane = new StackPane(borderPane);
        gridPane = new GridPane();
        borderPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(new Dice().createDice());
        borderPane.setRight(hBox);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(100, 100, 100, 0));

        for(int i = 0;i<15;i++){
            for(int j = 0;j<15;j++){
                board[i][j].setOnMousePressed(eventHandle(i, j));
                 
                
                gridPane.add(board[i][j],i,j);
            }
        }
        Scene scene = new Scene(stackPane);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}
