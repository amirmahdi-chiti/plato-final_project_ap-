package SeaBattle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Board1 extends Application {

    static int size; // size Board
    Cell[][] array; //original array player1
    Cell[][] array1; // array player1,which is displayed to player2
    Cell[][] enemy;  // original array player2
    Cell[][] enemy1; // array player2,which is displayed to player1
    static Stage original;
    String winner = null;   // to show winnner
    static int countNutEnemy, countNutMy;   // the number of remaining nuts of Player2 and vice versa;
    static int moneyEnemy, moneyMy;         // to show money player1 & player2
    static String player1, player2;      // name player1 && player2
    public int oldX = 0;       // to show the privious location of nuts 
    public int oldY = 0;
    Nut nuts = null;    // to show privious nut
    int HAttack = 0;    // to show size of 
    int WAttack = 0;
    Text txtMy = new Text("$" + moneyMy);
    Text txtEnemy = new Text("$" + moneyEnemy);
    static int king1, king2, horse1, horse2, soldier1, soldier2, castle1, castle2;
    final static int KING = 1, HORSE = 3, SOLDIER = 5, CASTLE = 2;
    static int kingAttack1 = KING, kingAttack2 = KING, horseAttack1 = HORSE, horseAttack2 = HORSE,
            soldierAttack1 = SOLDIER, soldierAttack2 = SOLDIER, castleAttack1 = CASTLE, castleAttack2 = CASTLE;
    private Nut nutNow = new Nut(Type.SOLDIER); // to show nut
    boolean buy = false; // enable buy mode
    boolean min = false; // enable min mode
    int swich = 0; // for show the number of scene

    public static void main(String[] args) {
        launch(args);
    }

    /*
        To handle
        activities that involve placing the nut on the map and moving it to the second and third scenes.
     */
    private EventHandler<MouseEvent> eventHandle(int i, int j, Cell[][] array, String name) {

        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //if(event.getEventType().equals(MouseEvent.))
                if (array[i][j].nut != null && !Cell.moveTurn) {

                    oldX = i;
                    oldY = j;
                    System.out.println(oldX + "***" + oldY);
                    nuts = new Nut(array[i][j].nut.getHealth());
                    Cell.moveTurn = true;
                    System.out.println("pick up");
                    System.out.println("nuts " + nuts.type + "** size " + array[i][j].nut.getHealth());
                } else if (Cell.moveTurn) {
                    if (array[i][j].move(i, j, oldX, oldY, nuts, array)) {

                        Cell.moveTurn = false;
                    }
                    //  System.out.println("put");

                } else {

                    if (array[i][j].putnut(i, j, getNutNow(), array)) {
                        if (name.equals("enemy")) {
                            countNutEnemy++;
                            if (getNutNow().type.equals(Type.SOLDIER)) {
                                soldier2++;
                            }
                            if (getNutNow().type.equals(Type.CASTLE)) {
                                castle2++;
                            }
                            if (getNutNow().type.equals(Type.KING)) {
                                king2++;
                            }
                            if (getNutNow().type.equals(Type.HORSEBACK)) {
                                horse2++;
                            }
                            System.out.println(soldier2 + "**" + castle2 + "**" + king2 + "**" + horse2);
                        } else {
                            countNutMy++;
                            if (getNutNow().type.equals(Type.SOLDIER)) {
                                soldier1++;
                            }
                            if (getNutNow().type.equals(Type.CASTLE)) {
                                castle1++;
                            }
                            if (getNutNow().type.equals(Type.KING)) {
                                king1++;
                            }
                            if (getNutNow().type.equals(Type.HORSEBACK)) {
                                horse1++;
                            }
                            System.out.println(soldier1 + "**" + castle1 + "**" + king1 + "**" + horse1);

                        }
                    }
                    System.out.println(getNutNow().type);
                    //        System.out.println("nuts in  new "+nuts.type);
                    System.out.println("new");
                }

            }
        };
        return event;

    }

    /*
     To handle attack activities
     */
    private EventHandler<MouseEvent> eventHandle2(int i, int j, Cell[][] original, Cell[][] cell, Cell[][] cell3, String name) {

        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (i + WAttack <= size && j + HAttack <= size) {
                    if (updateCountAttack(name)) {
                        if (cell[i][j].shoot(original, cell, cell3, name, WAttack, HAttack, i, j)) {
                            if (name.equals("enemy")) {
                                if (countNutMy == 0) {
                                    winner = player2;
                                    try {
                                        createScene6(winner);
                                    } catch (FileNotFoundException ex) {
                                    }
                                }

                                txtEnemy.setText(String.format(" $%d", moneyEnemy));
                                System.out.println("pooooooooooooooool enemy " + moneyEnemy);
                            } else {
                                if (countNutEnemy == 0) {
                                    winner = player1;
                                    try {
                                        createScene6(winner);
                                    } catch (FileNotFoundException ex) {
                                    }
                                }

                                txtMy.setText(String.format(" $%d", moneyMy));
                                System.out.println("poooooooooooool " + moneyMy);

                            }
                        } else {
                            if (WAttack == 0) {
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Eror");
                                alert.setHeaderText(null);
                                alert.setContentText("Please first select a game bead (beads at the bottom corner of the screen)");
                                alert.showAndWait();
                            } else {
                                if (name.equals("enemy")) {
                                    createScene4();

                                } else {
                                    createScene5();
                                }
                            }

                        }
                        WAttack = 0;
                        HAttack = 0;

                    }
                    System.out.println("soldier1 " + soldier1 + " king1 " + king1 + " castle1 " + castle1 + " horse1 " + horse1 + "sum " + countNutMy);
                    System.out.println("soldier2 " + soldier2 + " king2 " + king2 + " castle2 " + castle2 + " horse2 " + horse2 + "sum " + countNutEnemy);
                }
            }

        };
        return event;
    }

    /*
         To handle 
         activities related to clearing and moving the bead and buying a new bead and putting it on the map
     */
    private EventHandler<MouseEvent> eventHandleMove(int i, int j, Cell[][] array, String name) {

        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if (array[i][j].nut != null && !Cell.moveTurn && array[i][j].nut.completeHealth) {
                    //System.out.println("in if");
                    Nut nut2 = new Nut(array[i][j].nut.type);
                    if (array[i][j].delete(i, j, array)) {
                        nuts = nut2;
                        Cell.moveTurn = true;
                        System.out.println("delete");
                        oldX = i;
                        oldY = j;
                        updateCount(name, "-");
                        buy = false;
                    }
                } else if (Cell.moveTurn) {
                    if (array[i][j].putMove(i, j, oldX, oldY, array, nuts)) {

                        Cell.moveTurn = false;
                        if (name.equals("enemy")) {
                            createScene4();

                        } else {
                            createScene5();
                        }
                        updateCount(name, "+");
                        System.out.println("put");

                    }
                }

                if (array[i][j].nut == null && buy && !min) {
                    Nut nutnew = new Nut(WAttack * HAttack);
                    System.out.println("cost " + WAttack * HAttack * 10);
                    if (checkBuy(WAttack * HAttack * 10, name)) {
                        if (array[i][j].putnut(i, j, nutnew, array)) {
                            if (name.equals("enemy")) {
                                countNutEnemy++;
                                txtEnemy.setText(String.format(" $%d", moneyEnemy));
                                System.out.println("My Money " + moneyEnemy);

                                if (WAttack * HAttack == 1) {
                                    soldier2++;
                                }
                                if (WAttack * HAttack == 4) {
                                    castle2++;
                                }
                                if (WAttack * HAttack == 9) {
                                    king2++;
                                }
                                if (WAttack * HAttack == 2) {
                                    horse2++;
                                }
                                System.out.println(soldier2 + "**" + castle2 + "**" + king2 + "**" + horse2);
                            } else {
                                countNutMy++;
                                txtMy.setText(String.format(" $%d", moneyMy));
                                System.out.println("MoneyEnemy" + moneyMy);

                                if (WAttack * HAttack == 1) {
                                    soldier1++;
                                }
                                if (WAttack * HAttack == 4) {
                                    castle1++;
                                }
                                if (WAttack * HAttack == 9) {
                                    king1++;
                                }
                                if (WAttack * HAttack == 2) {
                                    horse1++;
                                }
                                System.out.println(soldier1 + "**" + castle1 + "**" + king1 + "**" + horse1);

                            }
                            if (name.equals("enemy")) {
                                original.setScene(createScene4());
                            } else {
                                original.setScene(createScene5());
                            }
                            buy = false;
                        }
                    }
                }
                if (array[i][j].nut == null && buy && min) {
                    if (checkBuy(20, name)) {

                        array[i][j].min = true;
                        FileInputStream input2 = null;
                        try {
                            input2 = new FileInputStream("images\\min.png");
                        } catch (FileNotFoundException ex) {
                        }
                        Image img2 = new Image(input2);
                        array[i][j].setFill(new ImagePattern(img2));
                        min = false;
                        buy = false;
                        if (name.equals("enemy")) {
                            txtEnemy.setText(String.format(" $%d", moneyEnemy));
                        } else {
                            txtMy.setText(String.format(" $%d", moneyMy));
                        }
                        if (name.equals("enemy")) {
                            original.setScene(createScene4());
                        } else {
                            original.setScene(createScene5());
                        }
                    }
                }
                System.out.println("soldier1 " + soldier1 + " king1 " + king1 + " castle1 " + castle1 + " horse1 " + horse1 + "sum " + countNutMy);
                System.out.println("soldier2 " + soldier2 + " king2 " + king2 + " castle2 " + castle2 + " horse2 " + horse2 + "sum " + countNutEnemy);

            }
        };
        return event;
    }
    /*
        to handle 
        create scene1
    */
    private EventHandler<ActionEvent> eventHandleCreateScene(TextField textField1, TextField textField2, TextField textField3) {

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                boolean eror = false;
                try {
                    size = Integer.parseInt(textField3.getText());
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Eror");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter the number in the correct format");

                    alert.showAndWait();
                    eror = true;
                }
                array = new Cell[size][size];
                array1 = new Cell[size][size];
                enemy = new Cell[size][size];
                enemy1 = new Cell[size][size];
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array.length; j++) {
                        array[i][j] = new Cell();
                        array1[i][j] = new Cell();
                        enemy[i][j] = new Cell();
                        enemy1[i][j] = new Cell();
                    }
                }
                player1 = textField1.getText();
                player2 = textField2.getText();
                if (!eror) {
                    original.setScene(createScene2());
                } else {
                    try {
                        original.setScene(createScene1());
                    } catch (FileNotFoundException ex) {
                    }
                }

            }
        };
        return event;
    }
    /*
        for load map of game
    */
    private EventHandler<MouseEvent> load() {

        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                try (FileInputStream file = new FileInputStream("save2.bin"); ObjectInputStream object = new ObjectInputStream(file)) {
                    size = object.readInt();
                    countNutMy = object.readInt();
                    countNutEnemy = object.readInt();
                    moneyMy = object.readInt();
                    moneyEnemy = object.readInt();
                    txtMy.setText("$" + moneyMy);
                    txtEnemy.setText("$" + moneyEnemy);
                    soldier1 = object.readInt();
                    soldier2 = object.readInt();
                    horse1 = object.readInt();
                    horse2 = object.readInt();
                    castle1 = object.readInt();
                    castle2 = object.readInt();
                    king1 = object.readInt();
                    king2 = object.readInt();
                    WAttack = object.readInt();
                    HAttack = object.readInt();
                    player1 = object.readUTF();
                    player2 = object.readUTF();
                    swich = object.readInt();
                    array = new Cell[size][size];
                    array1 = new Cell[size][size];
                    enemy = new Cell[size][size];
                    enemy1 = new Cell[size][size];
                    for (int i = 0; i < array.length; i++) {
                        for (int j = 0; j < array.length; j++) {
                            try {
                                array[i][j] = new Cell((Cell) object.readObject(), true, false);
                                enemy[i][j] = new Cell((Cell) object.readObject(), true, false);
                                if (array[i][j].nut != null) {
                                    array1[i][j] = new Cell((Cell) object.readObject(), false, true);
                                } else {
                                    array1[i][j] = new Cell((Cell) object.readObject(), false, false);
                                }
                                if (enemy[i][j].nut != null) {
                                    enemy1[i][j] = new Cell((Cell) object.readObject(), false, true);
                                } else {
                                    enemy1[i][j] = new Cell((Cell) object.readObject(), false, false);
                                }
                            } catch (ClassNotFoundException ex) {
                                System.out.println("nashod ke beshe");
                            }
                        }
                    }

                } catch (IOException e) {
                    System.out.println("ridiiiiiiii2");
                    e.printStackTrace();

                }
                switch (swich) {
                    case 2:
                        original.setScene(createScene2());
                        break;
                    case 3:
                        original.setScene(createScene3());
                        break;
                    case 4:
                        original.setScene(createScene4());
                        break;
                    case 5:
                        original.setScene(createScene5());
                        break;
                    default:
                        original.setScene(createScene0());
                }
            }
        };
        return event;
    }
    /*
        To handle
        previews of beads in the game map
    */
    private EventHandler<MouseEvent> periview(int i, int j, Cell[][] array) {

        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (array[i][j].canPlaceNut(i, j, nutNow, array)) {
                    for (int x = i; x < i + nutNow.width; x++) {
                        for (int y = j; y < j + nutNow.height; y++) {
                            array[x][y].setFill(Color.BLUE);
                            System.out.println(x + "***" + y);
                        }
                    }
                }
            }
        };
        return event;
    }

    private EventHandler<MouseEvent> deletePeriview(int i, int j, Cell[][] array) {

        EventHandler<MouseEvent> event = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (array[i][j].canPlaceNut(i, j, nutNow, array)) {
                    for (int x = i; x < i + nutNow.width; x++) {
                        for (int y = j; y < j + nutNow.height; y++) {
                            array[x][y].setFill(Color.ANTIQUEWHITE);

                        }
                    }
                }
            }
        };
        return event;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        original = primaryStage;
        original.setTitle("SEA BATTLE");

        original.setScene(createScene0());
        original.setOnCloseRequest((event) -> {
            alert();
        });
        original.show();
    }

    public Nut getNutNow() {
        return nutNow;
    }
    /*
        created first scsne
    */
    private Scene createScene0() {
        BorderPane borderPane = new BorderPane();
        FileInputStream input2 = null;
        try {
            input2 = new FileInputStream("images\\menu0.png");
        } catch (FileNotFoundException ex) {
        }

        Image image = new Image(input2);
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        borderPane.setBackground(background);
        VBox vBox = new VBox();
        borderPane.setLeft(vBox);
        vBox.setSpacing(30);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(40, 40, 100, 180));
        FileInputStream input = null;
        FileInputStream input3 = null;
        FileInputStream input4 = null;

        try {
            input = new FileInputStream("images\\new.png");
            input3 = new FileInputStream("images\\load.png");
            input4 = new FileInputStream("images\\exit.png");

        } catch (FileNotFoundException ex) {
        }

        Image image2 = new Image(input);
        Rectangle rectangle = new Rectangle(180, 95);
        rectangle.setOnMousePressed((event) -> {

            try {
                original.setScene(createScene1());
            } catch (FileNotFoundException ex) {
            }
        });
        rectangle.setFill(new ImagePattern(image2));
        Rectangle rectangle1 = new Rectangle(180, 95, new ImagePattern(new Image(input3)));
        rectangle1.setOnMousePressed(load());

        Rectangle rectangle2 = new Rectangle(180, 95, new ImagePattern(new Image(input4)));
        rectangle2.setOnMousePressed((event) -> {
            original.close();
        });
        rectangle.setOnMouseEntered((event) -> {
            rectangle.setStroke(Color.WHITE);
        });
        rectangle1.setOnMouseEntered((event) -> {
            rectangle1.setStroke(Color.WHITE);
        });
        rectangle2.setOnMouseEntered((event) -> {
            rectangle2.setStroke(Color.WHITE);
        });
        rectangle.setOnMouseExited((event) -> {
            rectangle.setStroke(null);
        });
        rectangle1.setOnMouseExited((event) -> {
            rectangle1.setStroke(null);
        });
        rectangle2.setOnMouseExited((event) -> {
            rectangle2.setStroke(null);
        });
        vBox.getChildren().addAll(rectangle, rectangle1, rectangle2);
        return new Scene(borderPane, 850, 550);
    }

    private Scene createScene1() throws FileNotFoundException {
        BorderPane borderPane = new BorderPane();

        FileInputStream input = new FileInputStream("images\\back.png");
        GridPane gridpane = new GridPane();
        borderPane.setLeft(gridpane);
        Text text1 = new Text("player 1 name :");
        Text text2 = new Text("player 2 name :");
        Text text3 = new Text("size of Board :");

        TextField textField1 = new TextField(); // textfiled for get first player name 
        TextField textField2 = new TextField(); // textfiled for get second player name    
        TextField textField3 = new TextField(); // textfiled for get size of board
        Button button = new Button("PLAY");
        button.setOnAction(eventHandleCreateScene(textField1, textField2, textField3));

        gridpane.setPadding(new Insets(20, 20, 20, 20));
        gridpane.add(text1, 0, 0);
        gridpane.add(text2, 0, 1);
        gridpane.add(text3, 0, 2);

        gridpane.add(textField1, 1, 0);
        gridpane.add(textField2, 1, 1);
        gridpane.add(textField3, 1, 2);

        gridpane.add(button, 1, 3);
        gridpane.setVgap(10);
        gridpane.setHgap(10);

        Image image = new Image(input);
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        borderPane.setBackground(background);
        original.setTitle("SEA BATTLE");

        Scene scene = new Scene(borderPane, 850, 550);
        return scene;
    }

    private Scene createScene2() {
        BorderPane borderPane = new BorderPane();
        Text text = new Text(player1); // to show name of first player
        GridPane gridPane = new GridPane();
        borderPane.setLeft(gridPane);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j].setOnMousePressed(eventHandle(i, j, array, "my"));
                array[i][j].setOnMouseEntered(periview(i, j, array));
                array[i][j].setOnMouseExited(deletePeriview(i, j, array));
                gridPane.add(array[i][j], i, j);
            }
        }
        Button button = new Button("NEXT");

        button.setOnAction((event) -> {
            if (checkStart("player1")) {
                original.setScene(createScene3());
            } else {
                createAlert();
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array.length; j++) {
                        array[i][j] = new Cell();
                        array1[i][j] = new Cell();
                    }
                }
                /*
                 Initial assignment to the first player variables
                */
                countNutMy = 0;     
                king1 = 0;
                soldier1 = 0;
                castle1 = 0;
                horse1 = 0;
                original.setScene(createScene2());
            }

        });
        borderPane.setTop(text);
        text.setFont(new Font("Arial", 24));
        borderPane.setAlignment(text, Pos.TOP_LEFT);
        borderPane.setPadding(new Insets(20));
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(createImageBox(), button);
        borderPane.setRight(hBox);
        borderPane.setAlignment(button, Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setPadding(new Insets(30));
        button.setAlignment(Pos.CENTER_RIGHT);
        button.setPadding(new Insets(20, 40, 20, 40));
        swich = 2;
        FileInputStream input = null;
        try {
            input = new FileInputStream("images\\wood2.png");
        } catch (FileNotFoundException ex) {
        }
        Image image = new Image(input);
        borderPane.setBackground(new Background(new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(borderPane);
        return scene;

    }

    private Scene createScene3() {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        Text text = new Text(player2);  // to show name of second player
        borderPane.setLeft(gridPane);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(createImageBox());
        for (int i = 0; i < enemy.length; i++) {
            for (int j = 0; j < array.length; j++) {
                enemy[i][j].setOnMousePressed(eventHandle(i, j, enemy, "enemy"));
                enemy[i][j].setOnMouseEntered(periview(i, j, enemy));
                enemy[i][j].setOnMouseExited(deletePeriview(i, j, enemy));
                gridPane.add(enemy[i][j], i, j);
            }
        }
        Button button = new Button("NEXT");
        button.setOnAction((event) -> {

            if (checkStart("player2")) {
                original.setScene(createScene4());
            } else {
                createAlert();
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array.length; j++) {
                        enemy[i][j] = new Cell();
                        enemy1[i][j] = new Cell();
                    }
                }
                /*
                 Initial assignment to the second player variables
                */
                countNutEnemy = 0;
                king2 = 0;
                soldier2 = 0;
                castle2 = 0;
                horse2 = 0;
                original.setScene(createScene3());
            }
        });
        hBox.getChildren().add(button);
        borderPane.setTop(text);
        text.setFont(new Font("Arial", 24));
        borderPane.setAlignment(text, Pos.TOP_LEFT);
        borderPane.setPadding(new Insets(20));
        borderPane.setRight(hBox);
        borderPane.setAlignment(button, Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setPadding(new Insets(30));
        button.setAlignment(Pos.CENTER_RIGHT);
        button.setPadding(new Insets(20, 40, 20, 40));
        FileInputStream input = null;
        try {
            input = new FileInputStream("images\\wood2.png");
        } catch (FileNotFoundException ex) {
        }
        Image image = new Image(input);
        borderPane.setBackground(new Background(new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(borderPane);
        swich = 3;
        return scene;

    }
    /*
        return pane to display the bottom of scene 4 & secen 5
    */
    private Pane createBottom(String name) {
        HBox hBox = new HBox();
        Text text = new Text("Your Money : ");
        FileInputStream input1 = null;
        FileInputStream input2 = null;
        FileInputStream input3 = null;
        FileInputStream input4 = null;
        FileInputStream input5 = null;

        try {
            input1 = new FileInputStream("images\\soldier.png");
            input2 = new FileInputStream("images\\king.png");
            input3 = new FileInputStream("images\\horse.png");
            input4 = new FileInputStream("images\\castle.png");
            input5 = new FileInputStream("images\\min.png");

        } catch (FileNotFoundException ex) {
        }
        Image img1 = new Image(input1);
        Image img2 = new Image(input2);
        Image img3 = new Image(input3);
        Image img4 = new Image(input4);
        Image img5 = new Image(input5);

        Rectangle rectangle1 = new Rectangle((50 - 0.8 * Board1.size) * size / 5, (50 - 0.8 * Board1.size) * size / 5, new ImagePattern(img1));
        Rectangle rectangle2 = new Rectangle((50 - 0.8 * Board1.size) * size / 5, (50 - 0.8 * Board1.size) * size / 5, new ImagePattern(img2));
        Rectangle rectangle3 = new Rectangle((50 - 0.8 * Board1.size) * size / 5, (50 - 0.8 * Board1.size) * size / 5, new ImagePattern(img3));
        Rectangle rectangle4 = new Rectangle((50 - 0.8 * Board1.size) * size / 5, (50 - 0.8 * Board1.size) * size / 5, new ImagePattern(img4));
        Rectangle rectangle5 = new Rectangle((50 - 0.8 * Board1.size) * size / 5, (50 - 0.8 * Board1.size) * size / 5, new ImagePattern(img5));
        rectangle1.setStroke(Color.BLACK);
        rectangle2.setStroke(Color.BLACK);
        rectangle3.setStroke(Color.BLACK);
        rectangle4.setStroke(Color.BLACK);
        rectangle5.setStroke(Color.BLACK);

        rectangle1.setOnMousePressed((event) -> {
            HAttack = 1;
            WAttack = 1;
            rectangle2.setStroke(Color.BLACK);
            rectangle3.setStroke(Color.BLACK);
            rectangle4.setStroke(Color.BLACK);
            rectangle5.setStroke(Color.BLACK);
            rectangle1.setStroke(Color.RED);

            buy = true;

        });
        rectangle2.setOnMousePressed((event) -> {
            rectangle1.setStroke(Color.BLACK);
            rectangle3.setStroke(Color.BLACK);
            rectangle4.setStroke(Color.BLACK);
            rectangle5.setStroke(Color.BLACK);
            rectangle2.setStroke(Color.RED);

            buy = true;

            HAttack = 3;
            WAttack = 3;

        });
        rectangle3.setOnMousePressed((event) -> {
            rectangle2.setStroke(Color.BLACK);
            rectangle1.setStroke(Color.BLACK);
            rectangle4.setStroke(Color.BLACK);
            rectangle5.setStroke(Color.BLACK);
            rectangle3.setStroke(Color.RED);

            buy = true;

            HAttack = 2;
            WAttack = 1;

        });
        rectangle4.setOnMousePressed((event) -> {
            rectangle2.setStroke(Color.BLACK);
            rectangle3.setStroke(Color.BLACK);
            rectangle1.setStroke(Color.BLACK);
            rectangle5.setStroke(Color.BLACK);
            rectangle4.setStroke(Color.RED);

            buy = true;

            HAttack = 2;
            WAttack = 2;

        });
        rectangle5.setOnMousePressed((event) -> {
            rectangle2.setStroke(Color.BLACK);
            rectangle3.setStroke(Color.BLACK);
            rectangle1.setStroke(Color.BLACK);
            rectangle4.setStroke(Color.BLACK);
            rectangle5.setStroke(Color.RED);

            buy = true;
            min = true;

        });
        hBox.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4, rectangle5);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().add(text);
        if (name.equals("player2")) {
            hBox.getChildren().add(txtEnemy);
            txtEnemy.setFont(new Font(24));
        } else {
            hBox.getChildren().add(txtMy);
            txtMy.setFont(new Font(24));
        }
        hBox.setPadding(new Insets(20, 20, 20, 50));
        text.setFont(new Font(24));
        return hBox;
    }
    
    private Pane createImageBox() {
        VBox vBox = new VBox();
        FileInputStream input1 = null;
        FileInputStream input2 = null;
        FileInputStream input3 = null;
        FileInputStream input4 = null;
        System.out.println("in createImageBox method");
        try {
            input1 = new FileInputStream("images\\soldier.png");
            input2 = new FileInputStream("images\\king.png");
            input3 = new FileInputStream("images\\horse.png");
            input4 = new FileInputStream("images\\castle.png");

        } catch (FileNotFoundException ex) {
        }
        Image img1 = new Image(input1);
        Image img2 = new Image(input2);
        Image img3 = new Image(input3);
        Image img4 = new Image(input4);

        Rectangle rectangle1 = new Rectangle((50 - 0.8 * Board1.size) * size / 4, (50 - 0.8 * Board1.size) * size / 4, new ImagePattern(img1));
        rectangle1.setOnMousePressed((event) -> {
            nutNow = new Nut(Type.SOLDIER);
            System.out.println(nutNow.type + "********* size" + nutNow.getHealth());
        });
        Rectangle rectangle2 = new Rectangle((50 - 0.8 * Board1.size) * size / 4, (50 - 0.8 * Board1.size) * size / 4, new ImagePattern(img2));
        rectangle2.setOnMousePressed((event) -> {
            nutNow = new Nut(Type.KING);

            System.out.println(nutNow.type + "********* size" + nutNow.getHealth());

        });
        Rectangle rectangle3 = new Rectangle((50 - 0.8 * Board1.size) * size / 4, (50 - 0.8 * Board1.size) * size / 4, new ImagePattern(img3));
        rectangle3.setOnMousePressed((event) -> {
            nutNow = new Nut(Type.HORSEBACK);
            System.out.println(nutNow.type + "********* size" + nutNow.getHealth());

        });
        Rectangle rectangle4 = new Rectangle((50 - 0.8 * Board1.size) * size / 4, (50 - 0.8 * Board1.size) * size / 4, new ImagePattern(img4));
        rectangle4.setOnMousePressed((event) -> {
            nutNow = new Nut(Type.CASTLE);

        });
        rectangle1.setStroke(Color.BLACK);
        rectangle2.setStroke(Color.BLACK);
        rectangle3.setStroke(Color.BLACK);
        rectangle4.setStroke(Color.BLACK);

        vBox.setPadding(new Insets(40, 30, 40, 30));
        vBox.getChildren().addAll(rectangle1, rectangle2, rectangle3, rectangle4);
        return vBox;

    }

    private Scene createScene4() {

        BorderPane borderPane = new BorderPane();
        BorderPane borderPane2 = new BorderPane();
        borderPane.setCenter(borderPane2);
        GridPane gridPane1 = new GridPane();
        GridPane gridPane2 = new GridPane();
        borderPane.setBottom(createBottom("player1"));

        FileInputStream input = null;
        try {
            input = new FileInputStream("images\\e.png");
        } catch (FileNotFoundException ex) {
        }

        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setOnMousePressed((event) -> {
            createScene5();
        });
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        borderPane2.setCenter(imageView);

        MenuBar menuBar = new MenuBar(createMenuBar());
        borderPane.setTop(menuBar);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                enemy1[i][j].setOnMousePressed(eventHandle2(i, j, enemy, enemy1, array, "my"));

                gridPane2.add(enemy1[i][j], i, j);
                array[i][j].setOnMousePressed(null);
                array[i][j].setOnMousePressed(eventHandleMove(i, j, array, "my"));
                array[i][j].setOnMouseEntered(null);
                array[i][j].setOnMouseExited(null);
                gridPane1.add(array[i][j], i, j);
            }
        }

        borderPane2.setLeft(gridPane1);
        borderPane2.setRight(gridPane2);
        gridPane1.setPadding(new Insets(20, 50, 20, 50));
        gridPane2.setPadding(new Insets(20, 50, 20, 50));

        gridPane1.setAlignment(Pos.CENTER_RIGHT);
        gridPane2.setAlignment(Pos.CENTER_LEFT);
        Scene scene2 = new Scene(borderPane);
        original.setTitle(player1);
        swich = 4;
        if (horseAttack1 <= 0) {
            horseAttack1++;
        }
        if (horseAttack2 <= 0) {
            horseAttack2++;
        }
        if (soldierAttack1 <= 0) {
            soldierAttack1++;
        }
        if (soldierAttack2 <= 0) {
            soldierAttack2++;
        }
        if (kingAttack1 <= 0) {
            kingAttack1++;
        }
        if (kingAttack2 <= 0) {
            kingAttack2++;
        }
        if (castleAttack1 <= 0) {
            castleAttack1++;
        }
        if (castleAttack2 <= 0) {
            castleAttack2++;
        }

        original.setScene(scene2);
        return scene2;
    }
    
    private Menu createMenuBar(){
        Menu fileMenu = new Menu("File");
        MenuItem menuItem1 = new MenuItem("New ...");
        menuItem1.setOnAction((event) -> {
            try {
                original.setScene(createScene1());
            } catch (FileNotFoundException ex) {
                System.out.println("cannot");
            }
        });
        fileMenu.getItems().add(menuItem1);
        fileMenu.getItems().add(new MenuItem("Open..."));
        fileMenu.getItems().add(new MenuItem("Setting..."));
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem menuItem = new MenuItem("Exit");
        menuItem.setOnAction((event) -> {
            original.close();
        });
        fileMenu.getItems().add(menuItem);
        return fileMenu;
    }
    private Scene createScene5() {
        BorderPane borderPane = new BorderPane();
        BorderPane borderPane2 = new BorderPane();
        borderPane.setCenter(borderPane2);
        GridPane gridPane1 = new GridPane();
        GridPane gridPane2 = new GridPane();
        FileInputStream input = null;
        borderPane.setBottom(createBottom("player2"));

        try {
            input = new FileInputStream("images\\e.png");
        } catch (FileNotFoundException ex) {
        }

        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setOnMousePressed((event) -> {
            createScene4();
        });
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        borderPane2.setCenter(imageView);
        
        MenuBar menuBar = new MenuBar(createMenuBar());
        borderPane.setTop(menuBar);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array1[i][j].setOnMousePressed(eventHandle2(i, j, array, array1, enemy, "enemy"));

                gridPane2.add(array1[i][j], i, j);
                enemy[i][j].setOnMousePressed(null);
                enemy[i][j].setOnMousePressed(eventHandleMove(i, j, enemy, "enemy"));
                enemy[i][j].setOnMouseEntered(null);
                enemy[i][j].setOnMouseExited(null);
                gridPane1.add(enemy[i][j], i, j);

            }
        }

        borderPane2.setLeft(gridPane1);
        borderPane2.setRight(gridPane2);
        gridPane1.setPadding(new Insets(20, 50, 20, 50));
        gridPane2.setPadding(new Insets(20, 50, 20, 50));

        gridPane1.setAlignment(Pos.CENTER_RIGHT);
        gridPane2.setAlignment(Pos.CENTER_LEFT);
        Scene scene2 = new Scene(borderPane);
        original.setTitle(player2);
        swich = 5;
        if (horseAttack1 <= 0) {
            horseAttack1++;
        }
        if (horseAttack2 <= 0) {
            horseAttack2++;
        }
        if (soldierAttack1 <= 0) {
            soldierAttack1++;
        }
        if (soldierAttack2 <= 0) {
            soldierAttack2++;
        }
        if (kingAttack1 <= 0) {
            kingAttack1++;
        }
        if (kingAttack2 <= 0) {
            kingAttack2++;
        }
        if (castleAttack1 <= 0) {
            castleAttack1++;
        }
        if (castleAttack2 <= 0) {
            castleAttack2++;
        }
        original.setScene(scene2);
        return scene2;
    }
    /*
     this this method to create scene to show winner player
    */
    private Scene createScene6(String name) throws FileNotFoundException {
        BorderPane borderPane = new BorderPane();

        FileInputStream input = new FileInputStream("images\\d.png");

        Image image = new Image(input);
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(image), CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        Label label = new Label(name + " is winner !");
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPadding(new Insets(40));
        label.setFont(new Font(60));

        borderPane.setLeft(label);
        borderPane.setBackground(background);
        Scene scene = new Scene(borderPane, 900, 580);
        original.setScene(scene);
        return scene;
    }
    /*
     this method return eror massage box
        to Display the number of pieces needed to start the game 
    */
    private Dialog<ButtonType> createAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Eror Dialog");
        alert.setHeaderText("notice !");
        alert.setContentText("You Have To Put 1 King, 2 Castle, 3 Horse, 5 Soldier On The Board");
        alert.showAndWait();
        return alert;
    }

    private Dialog<ButtonType> alert() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit of the game?");
        ButtonType exit = new ButtonType("exit");
        ButtonType save = new ButtonType("save");

        alert.getButtonTypes().setAll(exit, save);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == exit) {
            original.close();
        } else if (result.get() == save) {
            try {
                save();
            } catch (FileNotFoundException ex) {
            }
        }

        return alert;
    }
    /*
     check start game
    */
    private boolean checkStart(String name) {
        if (name.equals("player1")) {
            if (soldier1 == SOLDIER && horse1 == HORSE && king1 == KING && castle1 == CASTLE) {
                return true;
            } else {
                return false;
            }
        } else {
            if (soldier2 == SOLDIER && horse2 == HORSE && king2 == KING && castle2 == CASTLE) {
                return true;
            } else {
                return false;
            }
        }
    }
    /*
       Update the number of game pieces
    */
    private void updateCount(String name, String type) {
        if (type.equals("-")) {
            if (name.equals("enemy")) {
                if (nuts.type.equals(Type.SOLDIER)) {
                    soldier2--;
                }
                if (nuts.type.equals(Type.CASTLE)) {
                    castle2--;
                }
                if (nuts.type.equals(Type.KING)) {
                    king2--;
                }
                if (nuts.type.equals(Type.HORSEBACK)) {
                    horse2--;
                }
                countNutEnemy--;
            } else {
                if (nuts.type.equals(Type.SOLDIER)) {
                    soldier1--;
                }
                if (nuts.type.equals(Type.CASTLE)) {
                    castle1--;
                }
                if (nuts.type.equals(Type.KING)) {
                    king1--;
                }
                if (nuts.type.equals(Type.HORSEBACK)) {
                    horse1--;
                }
                countNutMy--;

            }
        } else {
            if (name.equals("enemy")) {
                if (nuts.type.equals(Type.SOLDIER)) {
                    soldier2++;
                }
                if (nuts.type.equals(Type.CASTLE)) {
                    castle2++;
                }
                if (nuts.type.equals(Type.KING)) {
                    king2++;
                }
                if (nuts.type.equals(Type.HORSEBACK)) {
                    horse2++;
                }
                countNutEnemy++;
            } else {
                if (nuts.type.equals(Type.SOLDIER)) {
                    soldier1++;
                }
                if (nuts.type.equals(Type.CASTLE)) {
                    castle1++;
                }
                if (nuts.type.equals(Type.KING)) {
                    king1++;
                }
                if (nuts.type.equals(Type.HORSEBACK)) {
                    horse1++;
                }
                countNutMy++;

            }
        }
    }

    private Dialog<ButtonType> createAlertForCount() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("EROR");
        alert.setHeaderText("");
        alert.setContentText("You can't use this game nut to attack");
        alert.showAndWait();
        return alert;
    }
    /*
       Update the number of game pieces used for the attack
    */
    private boolean updateCountAttack(String name) {
        System.out.println("My name is :::::::::::::::::" + name);
        if (WAttack == 1 && HAttack == 1) {
            if (name.equals("enemy")) {
                if (soldierAttack2 <= 0) {
                    createAlertForCount();
                    return false;
                } else {
                    soldierAttack2 -= 2;
                    return true;
                }
            } else {
                if (soldierAttack1 <= 0) {
                    createAlertForCount();
                    return false;
                } else {
                    soldierAttack1 -= 2;
                    return true;
                }
            }
        }
        if (WAttack == 1 && HAttack == 2) {
            if (name.equals("enemy")) {
                if (horseAttack2 <= 0) {
                    createAlertForCount();
                    return false;
                } else {
                    horseAttack2 -= 4;
                    return true;
                }
            } else {
                if (horseAttack1 <= 0) {
                    createAlertForCount();
                    return false;
                } else {
                    horseAttack1 -= 4;
                    return true;
                }
            }
        }
        if (WAttack == 2 && HAttack == 2) {
            if (name.equals("enemy")) {
                if (castleAttack2 <= 0) {
                    createAlertForCount();
                    return false;
                } else {
                    castleAttack2 -= 6;
                    return true;
                }
            } else {
                if (castleAttack1 <= 0) {
                    createAlertForCount();
                    return false;
                } else {
                    castleAttack1 -= 6;
                    return true;
                }
            }
        }
        if (WAttack == 3 && HAttack == 3) {
            if (name.equals("enemy")) {
                if (kingAttack2 <= 0) {
                    createAlertForCount();
                    return false;
                } else {
                    kingAttack2 -= 8;
                    return true;
                }
            } else {
                if (kingAttack1 <= 0) {
                    createAlertForCount();
                    return false;
                } else {
                    kingAttack1 -= 8;
                    return true;
                }
            }

        }
        return true;
    }
    /*
     Check the necessary conditions to buy beads
    */
    private boolean checkBuy(int cost, String name) {
        String description = null;
        if (name.equals("enemy")) {
            if (moneyEnemy >= cost) {
                moneyEnemy -= cost;
            } else {
                description = "You do not have the money to buy this bead";
            }
            if (castle2 == 0) {
                description = "You can't buy a bead";
            }
        } else {
            if (moneyMy >= cost) {
                moneyMy -= cost;
            } else {
                description = "You do not have the money to buy this bead";
            }
            if (castle1 == 0) {
                description = "You can't buy a bead";
            }
        }
        if (description != null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Eror Dialog");
            alert.setHeaderText(null);
            alert.setContentText(description);

            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    private void save() throws FileNotFoundException {
        try (FileOutputStream file = new FileOutputStream("save2.bin"); ObjectOutputStream object = new ObjectOutputStream(file)) {
            object.writeInt(size);
            object.writeInt(countNutMy);
            object.writeInt(countNutEnemy);
            object.writeInt(moneyMy);
            object.writeInt(moneyEnemy);
            object.writeInt(soldier1);
            object.writeInt(soldier2);
            object.writeInt(horse1);
            object.writeInt(horse2);
            object.writeInt(castle1);
            object.writeInt(castle2);
            object.writeInt(king1);
            object.writeInt(king2);
            object.writeInt(WAttack);
            object.writeInt(HAttack);
            object.writeUTF(player1);
            object.writeUTF(player2);
            object.writeInt(swich);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    object.writeObject(array[i][j]);
                    object.writeObject(enemy[i][j]);
                    object.writeObject(array1[i][j]);
                    object.writeObject(enemy1[i][j]);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
