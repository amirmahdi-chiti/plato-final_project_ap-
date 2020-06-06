
package test048;

import javafx.scene.control.Alert;

public class Logic {
    public static void start(int num,Coin coin,Cell newCell,Cell oldCell){
        if(num==6){
            coin.setCurrent(true);
            coin.setNumber(0);
            newCell.setCoin(coin);
            oldCell.setCoin(null);
        }else{
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Eror Dialog");
//            alert.setHeaderText(null);
//            alert.setContentText("You can't enter this nut until the number of dice is six");
//
//            alert.showAndWait();
        }
    }
}
