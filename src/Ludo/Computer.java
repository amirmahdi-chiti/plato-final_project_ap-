
package Ludo;
import static Ludo.Main.board;

public class Computer {
    public boolean play(Type type){
        Dice dice = new Dice();
        dice.createRandom();
        
        for(int i = 0;i<board.length;i++){
            for(int j = 0 ;j< board.length;j++){
                if(board[i][j].getCoin().type.equals(type)){
                    if(Move.move(i, j)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
