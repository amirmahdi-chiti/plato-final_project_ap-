
package test048;

import javafx.scene.paint.Color;
import static test048.Test048.board;

public class Board {
    public static void initial(){
        for(int i = 0;i<board.length;i++){
            for(int j = 0;j<board.length;j++){
                if(i<6&&j<6)
                 board[i][j] = new Cell(Color.BLUE,null);
                else if(i>8&&j<6)
                 board[i][j] = new Cell(Color.RED,null);
                else if(i<6&&j>8)    
                 board[i][j] = new Cell(Color.YELLOW,null);
                else if(i>8&&j>8)
                 board[i][j] = new Cell(Color.CHARTREUSE,null);
                else if(j==7&&i>0&&i<6||j==6&&i==1)
                      board[i][j] = new Cell(Color.BLUE,Color.BLACK);
                 else if(i==7&&j>0&&j<6||i==8&&j==1)
                      board[i][j] = new Cell(Color.RED,Color.BLACK);
                 else if(j==7&&i>8&&i<14||i==13&&j==8)
                      board[i][j] = new Cell(Color.CHARTREUSE,Color.BLACK);
                 else if(i==7&&j>8&&j<14||i==6&&j==13)
                      board[i][j] = new Cell(Color.YELLOW,Color.BLACK);

                else{
                board[i][j] = new Cell(Color.ALICEBLUE,Color.BLACK);
                }
                if(i==1&&j==13||i==4&&j == 13||i==1&&j==10||i==4&&j==10){
                    board[i][j].setCoin(new Coin(Type.YELLOW,0));

                }
                if(i==10&&j==10||i==13&&i == j||i==10&&j==13||i==13&&j==10){
                     board[i][j].setCoin(new Coin(Type.GREEN,0));

                }if(i==1&&j==1||i==4&&i == j||i==1&&j==4||i==4&&j==1){
                     board[i][j].setCoin(new Coin(Type.BLUE,0));

                }if(i==10&&j==1||i==10&&j==4||i==13&&j==4||i==13&&j==1){
                      board[i][j].setCoin(new Coin(Type.RED,0));
                }
            }
        }
    }
}
