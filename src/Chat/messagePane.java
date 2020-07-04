package Chat;

import Login.Sql;
import Login.Main;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;

public class messagePane implements MessageListener, UserStatusListener, GameListener {

    private final ChatClient client;
    private String login;
    private ObservableList<String> messageItems;
    private ObservableList<String> contactItems;
    private String me;

    public messagePane(ChatClient client, String login) {
        this.client = client;
        this.me = login;
        client.addMessageListener(this);
        client.addGameListener(this);
        client.addUserStatusListener(this);
    }

    public Pane sceneChat() throws FileNotFoundException {
        BorderPane borderPane = new BorderPane();
        BorderPane borderPane1 = new BorderPane();
        VBox vBox = new VBox();
        VBox contactPane = new VBox();
        ListView<String> messageList = new ListView<String>();
        ListView<String> contactList = new ListView<String>();
        messageItems = FXCollections.observableArrayList();
        contactItems = FXCollections.observableArrayList();

        ArrayList<String> contact = new Sql().getContact(me);
        for (String st : contact) {
            System.out.println("************************");
            System.out.println(Server.ServerMain.server.getServerWorkerList());
            System.out.println("**************************");
         if(Server.ServerMain.server.getServerWorkerList().contains(st))
            contactItems.add(st.trim() + " (Online)");
         else{
             
            contactItems.add(st.trim() + " (Offline)");
         }
        }

        messageList.setItems(messageItems);
        messageList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> p) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);

                            // decide to add a new styleClass
                            // getStyleClass().add("costume style");
                            // decide the new font size
                            setStyle("-fx-font: 25 arial;-fx-background-color: #D92E0A; -fx-text-fill: white;");

                        }
                    }
                };
            }
        });
        contactList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> p) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item);

                            setStyle("-fx-font: 18 arial;-fx-background-color: #060000; -fx-text-fill: white;");

                        }
                    }
                };
            }
        });
        contactList.setItems(contactItems);
        contactList.setOnMousePressed((event) -> {
            login = StringUtils.split(contactList.getSelectionModel().getSelectedItem(), null, 2)[0];
            messageItems.clear();
            ArrayList<String> message = new Sql().getMessage(me, login);
            for (String str : message) {
                messageItems.add(str);
            }
            System.out.println(login);
        });
        contactPane.getChildren().add(contactList);
        contactPane.setFillWidth(true);

        BorderPane messagePane = new BorderPane(messageList);
        contactPane.setAlignment(Pos.CENTER);
        messagePane.setPrefHeight(600);
        VBox vBox1 = new VBox();
        vBox1.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream("image\\login.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        Circle circle = new Circle(100);
        if (me.equals("amirmahdi")) {
            circle.setFill(new ImagePattern(new Image(new FileInputStream("image\\amirmahdi.png"))));
        } else {
            circle.setFill(new ImagePattern(new Image(new FileInputStream("image\\user.png"))));
        }

        Text username = new Text(me);
        username.setTextAlignment(TextAlignment.CENTER);
        username.setStyle("-fx-font: 35 arial;");
        username.setFill(Color.rgb(215, 23, 23));
        Rectangle rectangleContact = new Rectangle(90, 70);
        rectangleContact.setFill(new ImagePattern(new Image(new FileInputStream("image\\contact.png"))));
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(30);
        vBox1.setPadding(new Insets(25));
        vBox1.getChildren().addAll(circle, username);
        vBox.setPrefWidth(500);
        VBox gameBox = new VBox();
        gameBox.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream("image\\login.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));

        gameBox.setSpacing(50);
        gameBox.setPadding(new Insets(30));

        Circle ludoIcon = new Circle(65);
        Circle TicTacToeIcon = new Circle(65);
        Circle resultIcon = new Circle(65);

        ludoIcon.setFill(new ImagePattern(new Image(new FileInputStream("image\\ludoIcon.png"))));
        TicTacToeIcon.setFill(new ImagePattern(new Image(new FileInputStream("image\\TicTacToeIcon.png"))));
        resultIcon.setFill(new ImagePattern(new Image(new FileInputStream("image\\result.png"))));
        TicTacToeIcon.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    client.sendRequestGame(login);
                    Login.Main.scene.setRoot(new Tic_Tac_Toe.Main(false, me, me, login).sceneBuider());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
        resultIcon.setOnMousePressed((event) -> {
           int[] arr = new int[6];
                 arr =  new Sql().getRecord(me);
                 messageItems.add("Number of wins from friends = "+ arr[0]);
                 messageItems.add("Number of lose from friends = "+ arr[2]);
                 messageItems.add("Number of draw with friends = "+ arr[1]);
                 messageItems.add("Number of wins from computer = "+ arr[3]);
                 messageItems.add("Number of lose from computer = "+ arr[5]);
                 messageItems.add("Number of draw whit computer = "+ arr[4]);
        });

        gameBox.getChildren().addAll(ludoIcon, TicTacToeIcon,resultIcon);

        borderPane.setRight(gameBox);

        borderPane.setLeft(vBox);
        HBox sendBox = new HBox();
        TextField messageField = new TextField();
        messageField.setPrefWidth(1300);
        messageField.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        Circle sendIcon = new Circle(35);
        sendIcon.setFill(new ImagePattern(new Image(new FileInputStream("image\\sendIcon.png"))));
        sendIcon.setOnMousePressed((event) -> {
            try {
                String text = messageField.getText();
                client.msg(login, text);
                this.messageItems.add(me + ": " + text);
                messageField.setText("");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        sendBox.getChildren().addAll(messageField, sendIcon);
        borderPane1.setCenter(messagePane);
        //borderPane1.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream("image\\chatback.png")), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        borderPane1.setBottom(sendBox);
        sendBox.setAlignment(Pos.CENTER);

        borderPane.setCenter(borderPane1);
        vBox.getChildren().addAll(vBox1, rectangleContact, contactPane);

        return borderPane;
    }

    @Override
    public void onMessage(String fromLogin, String msgBody) {
        //  if (login.equalsIgnoreCase(fromLogin)) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                String line = fromLogin + ": " + msgBody;
                messageItems.add(line);
            }
        });

        // }
    }

    @Override
    public void online(String login) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                contactItems.remove(login + " (Offline)");
                contactItems.remove(login + " (Online)");
                contactItems.add(login + " (Online)");
            }
        });
    }

    @Override
    public void offline(String login) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                contactItems.remove(login + " (Online)");
                contactItems.remove(login + " (Offline)");
                contactItems.add(login + " (Offline)");
            }
        });
    }

    @Override
    public void game(String login) {
        System.out.println("in game metod in message pane");
        Platform.runLater(new Runnable() {
            
            @Override
            public void run() {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Request a game");

                alert.setContentText("Do you want to play with " + login + " ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                    client.sendRequestGame(login);
                    Login.Main.scene.setRoot(new Tic_Tac_Toe.Main(false, me, me, login).sceneBuider());
                    } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    } catch (IOException ex) {
                    ex.printStackTrace();
                    }
                } else {
                    System.out.println("no i dont want");
                }
            }

        });

    }
}
