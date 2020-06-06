package test048;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    private Coin coin;
    Color color;
    public Cell(Color fill, Color stroke) {
        super(60, 60, fill);
        setStroke(stroke);
        color = fill;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        if (coin == null) {
            this.coin = coin;
            this.setFill(Test048.colorBackground);

        } else {
            this.coin = coin;
            this.setFill(coin.image);
        }
    }

}
