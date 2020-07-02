
package Login;
import Ludo.Main;
import Chat.ChatClient;
import Chat.messagePane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class GamePage {

     private final ChatClient client;
     private final String me;

    public GamePage(ChatClient client,String login) {
        this.client = client;
        this.me = login;
    }
    
    public Pane createGamePage() throws FileNotFoundException{
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        VBox vBox2 = new VBox();

        Image image = new Image(new FileInputStream("image\\background.png"));
        Image image2 = new Image(new FileInputStream("image\\ludo.png"));
        Image image3 = new Image(new FileInputStream("image\\TicTacToe.png"));
        Image image4 = new Image(new FileInputStream("image\\messangerIcon.png"));
        Image image5 = new Image(new FileInputStream("image\\snake.png"));

        borderPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        Rectangle tictactoeIcon = new Rectangle(220, 220);
        Rectangle ludoIcon = new Rectangle(220, 220);
        Rectangle messangerIcon = new Rectangle(220, 220);
        Rectangle snakeIcon = new Rectangle(220, 220);
        messangerIcon.setOnMousePressed((event) -> {
            try {
                Login.Main.scene.setRoot(new messagePane(client,me).sceneChat());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        ludoIcon.setOnMousePressed((event) -> {
            try {
                Login.Main.scene.setRoot(new Main().start());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        tictactoeIcon.setOnMousePressed((event) -> {
            try {
                Login.Main.scene.setRoot(new Tic_Tac_Toe.Main(true,me).sceneBuider());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        
        ludoIcon.setFill(new ImagePattern(image2));
        tictactoeIcon.setFill(new ImagePattern(image3));
        messangerIcon.setFill(new ImagePattern(image4));
        snakeIcon.setFill(new ImagePattern(image5));
        
        vBox.getChildren().addAll(messangerIcon,snakeIcon);
        vBox2.getChildren().addAll(tictactoeIcon,ludoIcon);
        vBox2.setSpacing(30);
        vBox.setSpacing(30);

        vBox.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        
        borderPane.setRight(vBox2);
        borderPane.setLeft(vBox);
        vBox.setPadding(new Insets(50));
        vBox2.setPadding(new Insets(50));
        
        return borderPane;
    }
}
