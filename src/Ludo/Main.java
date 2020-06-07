package Ludo;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static Cell[][] board = new Cell[15][15];
    StackPane stackPane;
    Stage stage;
    BorderPane borderPane;
    GridPane gridPane;
    static Type turn = Type.BLUE;
    static Color colorBackground = Color.ALICEBLUE;
    public static void main(String[] args) {
        Board.initial();
        launch(args);
    }
    private EventHandler<MouseEvent> eventHandle(int i, int j) {

        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(board[i][j].getCoin()!= null&&board[i][j].getCoin().type.equals(turn)){
                    Move.move(i, j);
                    Logic.finish();
                    while(!turn.equals(Type.BLUE)){
                    new Computer().play(turn);
                       /* try{
                             try {
                            TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException ex) {
                            }
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }*/
                      Logic.finish();
                    }
                }
                
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
        borderPane.setRight(Gui.createRightBox());
        

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
