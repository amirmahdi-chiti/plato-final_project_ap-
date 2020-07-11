
package Chat;

import Login.Sql;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MemberGroup extends Application{
    private static  String group;
    private ObservableList<String> memberItems;
    
    public MemberGroup(String group) {
        this.group = group;
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        ListView<String> membertList = new ListView<String>();
        memberItems = FXCollections.observableArrayList();
        ArrayList<String> member = new Sql().getMember(group);
        System.out.println("member is"+member);
        memberItems.addAll(member);
        membertList.setItems(memberItems);
        membertList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
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
        Pane pane = new Pane();
        pane.getChildren().add(membertList);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Group Info");
        primaryStage.show();
    }
    public static void main(String[] args) {
        System.out.println("name of group is " +group);
        launch(args);
    }

    public String getGroup() {
        return group;
    }
    
}
