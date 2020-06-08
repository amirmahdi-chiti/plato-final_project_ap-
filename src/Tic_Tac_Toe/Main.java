
package Tic_Tac_Toe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main {
    static Nut turn = Nut.X;
    static Cell[][] Board = new Cell[3][3];
    public Main() {
        for(int i = 0 ; i<3;i++){
            for(int j = 0;j<3;j++){
                Board[i][j]= new Cell();
            }
        }
    }
    

    public Scene sceneBuider() {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        for(int i = 0;i<Board.length;i++){
            for(int j = 0;j<Board.length;j++){
                gridPane.add(Board[i][j], i, j);
            }
        }
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        return scene;
        
    }
    
}
