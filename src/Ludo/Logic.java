package Ludo;

import javafx.scene.control.Alert;
import static Ludo.Main.board;

public class Logic {

    public static boolean start(int num, Coin coin, Cell newCell, Cell oldCell) {
        if (num == 6&&newCell.getCoin()==null) {
            coin.setCurrent(true);
            coin.setNumber(0);
            newCell.setCoin(coin);
            oldCell.setCoin(null);
            return true;
        } else {

//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Eror Dialog");
//            alert.setHeaderText(null);
//            alert.setContentText("You can't enter this nut until the number of dice is six");
//
//            alert.showAndWait();
            return false;
        }
    }
    public static void finish(){
       for(int i = 2;i<6;i++){
           if(board[i][7].getCoin()!=null){
               System.out.println("player 1 is winner");
           }
       }
       for(int i = 9;i<13;i++){
           if(board[i][7].getCoin()!=null){
               System.out.println("player 3 is winner");
           }
       }
       for(int j = 9;j<13;j++){
           if(board[7][j].getCoin()!=null){
               System.out.println("player 4 is winner");
           }
       }
       for(int j = 2;j<6;j++){
           if(board[7][j].getCoin()!=null){
               System.out.println("player 2 is winner");
           }
       }
    }
}
