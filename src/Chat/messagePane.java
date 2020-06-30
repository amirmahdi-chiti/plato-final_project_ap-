package Chat;

import Login.Sql;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class messagePane implements MessageListener {

    private final ChatClient client;
    private String login ;
    private ObservableList<String> messageItems;
    private ObservableList<String> contactItems;
    private String me;

    public messagePane(ChatClient client,String login) {
        this.client = client;
        this.me = login;
        client.addMessageListener(this);
    }

    public Pane sceneChat() {
        BorderPane borderPane = new BorderPane();
        BorderPane borderPane1 = new BorderPane();
        BorderPane borderPane2 = new BorderPane();
        VBox vBox = new VBox();
        ScrollPane contactPane = new ScrollPane();
        ListView<String> messageList = new ListView<String>();
        ListView<String> contactList = new ListView<String>();
        messageItems = FXCollections.observableArrayList();
        contactItems = FXCollections.observableArrayList();
       
        contactItems.add("amir");
        
        messageList.setItems(messageItems);
        contactList.setItems(contactItems);
        contactList.setOnMousePressed((event) -> {
            System.out.println("my name is " + me);
            login = contactList.getSelectionModel().getSelectedItem();
            ArrayList<String> message = new Sql().getMessage(me,login);
            for(String str:message){
            messageItems.add(str);
            }
            System.out.println(login);
        });
        contactPane.setContent(contactList);
        ScrollPane messagePane = new ScrollPane(messageList);
        GridPane gridPane = new GridPane();
        vBox.setPrefWidth(500);
        borderPane.setLeft(vBox);
        HBox sendBox = new HBox();
        TextField messageField = new TextField();
        Button button = new Button("send");
        button.setOnAction((event) -> {
            try {
                String text = messageField.getText();
                client.msg(login, text);
                this.messageItems.add(me+": " + text);
                messageField.setText("");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        sendBox.getChildren().addAll(messageField, button);
        borderPane1.setCenter(messagePane);
        borderPane1.setBottom(sendBox);
        borderPane.setCenter(borderPane1);
        vBox.getChildren().addAll(gridPane, contactPane);
        
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
}
