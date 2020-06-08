package Tic_Tac_Toe;

import static Tic_Tac_Toe.Computer.opponent;

import static Tic_Tac_Toe.Computer.player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

 class Logic {

    public static int isFinish() {
        Cell[][] b = Main.Board;
        for (int row = 0; row < 3; row++) {
            if (b[row][0].nut != null && b[row][0].nut == b[row][1].nut
                    && b[row][1].nut == b[row][2].nut) {
                if (b[row][0].nut.equals(Nut.X)) {
                    Main.text.setText("Player 1 is winner");
                    return +1;
                } else if (b[row][0].nut.equals(Nut.O)) {
                    Main.text.setText("Player 2 is winner");

                    return -1;
                }
            }
        }

        // Checking for Columns for X or O victory. 
        for (int col = 0; col < 3; col++) {
            if (b[0][col].nut != null && b[0][col].nut == b[1][col].nut
                    && b[1][col].nut == b[2][col].nut) {
                if (b[0][col].nut == Nut.X) {
                    Main.text.setText("Player 1 is winner");
                    return +1;
                } else if (b[0][col].nut == Nut.O) {
                    Main.text.setText("Player 2 is winner");
                    return -1;
                }
            }
        }

        // Checking for Diagonals for X or O victory. 
        if (b[0][0].nut != null && b[0][0].nut == b[1][1].nut && b[1][1].nut == b[2][2].nut) {
            if (b[0][0].nut == Nut.X) {
                Main.text.setText("Player 1 is winner");
                return +1;
            } else if (b[0][0].nut == Nut.O) {
                Main.text.setText("Player 2 is winner");
                return -1;
            }
        }

        if (b[1][1].nut != null && b[0][2].nut == b[1][1].nut && b[1][1].nut == b[2][0].nut) {
            if (b[0][2].nut == Nut.X) {
                Main.text.setText("Player 1 is winner");
                return +1;
            } else if (b[0][2].nut == Nut.O) {
                Main.text.setText("Player 2 is winner");
                return -1;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (b[i][j].nut == null) {
                    
                    return 2;
                }
            }

        }
        // Else if none of them have won then return 0
        Main.text.setText("DRAW");
        return 0;
    }
    public static void click(Cell cell){
        FileInputStream o = null;
            FileInputStream x = null;
            try {
                o = new FileInputStream("image\\circle.png");
                x = new FileInputStream("image\\x.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (cell.nut == null) {
                
                if (Main.turn.equals(Nut.O)) {
                    cell.setFill(new ImagePattern(new Image(o)));
                    cell.nut = Main.turn;
                    Main.turn = Nut.X;
                } else {
                    cell.setFill(new ImagePattern(new Image(x)));
                    cell.nut = Main.turn;
                     Main.turn = Nut.O;

                }
            }
        
    }
}
