
package test048;

import static test048.Test048.board;
import static test048.Test048.colorBackground;


public class Move {
    public static void move(int i,int j){
        if(board[i][j].getCoin()!=null){
                    int row  = board[i][j].getCoin().getRow();
                      colorBackground = board[i][j].color;
                    if(board[i][j].getCoin().isCurrent()){
                    System.out.println(board[i][j].getCoin().getNumber());
                 board[i][j].getCoin().setNumber(board[i][j].getCoin().getNumber()+ Dice.num);
                 int x = Path.pathX[row][board[i][j].getCoin().getNumber()];
                  int y = Path.pathY[row][board[i][j].getCoin().getNumber()];
               
                  board[x][y].setCoin(board[i][j].getCoin());
                  board[i][j].setCoin(null);
                  colorBackground = board[x][y].color;
                   }else{
                      int x = Path.pathX[row][0];
                      int y = Path.pathY[row][0];
                      Logic.start(Dice.num, board[i][j].getCoin(),board[x][y],board[i][j]);
                   }
                }
    }
}
