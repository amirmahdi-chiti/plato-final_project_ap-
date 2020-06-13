package Tic_Tac_Toe;

import static Tic_Tac_Toe.Computer.opponent;

import static Tic_Tac_Toe.Computer.player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

 class Logic {

    public static int isFinish() {
        Main.Cell[][] b = Main.Board;
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
    public static void click(Main.Cell cell,Main m){
        if (m!=null&&cell.nut==null && m.getMyTurn()) {
                paint(cell, m.getMyToken());
                m.setMyTurn(false);
                m.setRowSelected(cell.getRow());
                m.setColumnSelected(cell.getColumn());
                m.lblStatus.setText("Waiting for the other player to move");
                m.setWaiting(false); // Just completed a successful move
            }
        if(m==null)paint(cell, Main.turn);
    }
    public static void paint(Main.Cell cell,Nut nut){
        FileInputStream o = null;
            FileInputStream x = null;
            try {
                o = new FileInputStream("image\\circle.png");
                x = new FileInputStream("image\\x.png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
                
                    cell.nut = nut;
                if (nut.equals(Nut.O)) {
                    cell.setFill(new ImagePattern(new Image(o)));
                    Main.turn = Nut.X;
                } else {
                    cell.setFill(new ImagePattern(new Image(x)));
                     Main.turn = Nut.O;

                }
    }
          /**
         * Determine if the cells are all occupied
         */
        public static boolean isFull(char[][] cell) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cell[i][j] == ' ') {
                        return false; // At least one cell is not filled
                    }
                }
            }
            // All cells are filled
            return true;
        }

        /**
         * Determine if the player with the specified token wins
         */
        public static  boolean isWon(char token,char[][] cell) {
            // Check all rows
            for (int i = 0; i < 3; i++) {
                if ((cell[i][0] == token)
                        && (cell[i][1] == token)
                        && (cell[i][2] == token)) {
                    return true;
                }
            }

            /**
             * Check all columns
             */
            for (int j = 0; j < 3; j++) {
                if ((cell[0][j] == token)
                        && (cell[1][j] == token)
                        && (cell[2][j] == token)) {
                    return true;
                }
            }

            /**
             * Check major diagonal
             */
            if ((cell[0][0] == token)
                    && (cell[1][1] == token)
                    && (cell[2][2] == token)) {
                return true;
            }

            /**
             * Check subdiagonal
             */
            if ((cell[0][2] == token)
                    && (cell[1][1] == token)
                    && (cell[2][0] == token)) {
                return true;
            }
            /**
             * All checked, but no winner
             */
            return false;
        }
}
