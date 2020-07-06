package Tic_Tac_Toe;

import Chat.ChatClient;
import Login.Sql;
import static Tic_Tac_Toe.TicTacToeConstants.PLAYER1;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Chat.MessageListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main implements TicTacToeConstants,MessageListener{

    static Nut turn = Nut.X;
    static Cell[][] Board = new Cell[3][3];
    Cell[][] cells = new Cell[3][3];
    static Text text;
    String me;
    private String namePlayer1;
    private String namePlayer2;
    private boolean myTurn = false;
    // Indicate the token for the player
    private Nut myToken = null;
    private Nut otherToken = null;
    // Indicate selected row and column by the current move
    private int rowSelected;
    private int columnSelected;
    public boolean computer = true;
    // Input and output streams from/to server
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    // Continue to play?
    private boolean continueToPlay = true;
    
    private ObservableList<String> messageItems;
    
    Label lblTitle = new Label();
    // Create and initialize a status label
    Label lblStatus = new Label();

    // Wait for the player to mark a cell
    private boolean waiting = true;
    private ChatClient client;

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

    // Host name or ip
    private String host = "localhost";

    public Main(boolean Computer, String login, String player1, String player2,ChatClient client) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Board[i][j] = new Cell(i, j);
                cells[i][j] = new Cell(i, j);
            }
        }

        this.computer = Computer;
        if (!computer) {
            this.namePlayer1 = player1;
            this.namePlayer2 = player2;
            this.client = client;
            this.client.addMessageListener(this);
        }
        this.me = login;
        Logic.me = login;
    }
    
    
    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public void setMyToken(Nut myToken) {
        this.myToken = myToken;
    }

    public void setOtherToken(Nut otherToken) {
        this.otherToken = otherToken;
    }

    public void setColumnSelected(int columnSelected) {
        this.columnSelected = columnSelected;
    }

    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }

    public static void setTurn(Nut turn) {
        Main.turn = turn;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public Nut getMyToken() {
        return myToken;
    }

    public boolean getMyTurn() {
        return myTurn;
    }

    public Nut getOtherToken() {
        return otherToken;
    }

    public int getRowSelected() {
        return rowSelected;
    }

    public int getColumnSelected() {
        return columnSelected;
    }

    public Pane sceneBuider() throws FileNotFoundException {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        if (!computer) {
            VBox right = new VBox();
            HBox hBox = new HBox();
            right.getChildren().add(hBox);
            Circle circle = new Circle(50);
            Circle circle2 = new Circle(50);
            Circle circle3 = new Circle(50);
            FileInputStream fileInputStream = null;
            FileInputStream fileInputStream2 = null;
            FileInputStream fileInputStream3 = null;
            if (namePlayer1.equals("amirmahdi") || namePlayer2.equals("amirmahdi")) {
                fileInputStream = new FileInputStream("image\\amirmahdi2.png");
            } else {
                fileInputStream = new FileInputStream("image\\user.png");
            }
            fileInputStream2 = new FileInputStream("image\\vs.png");
            fileInputStream3 = new FileInputStream("image\\user2.png");
            circle.setFill(new ImagePattern(new Image(fileInputStream)));
            circle2.setFill(new ImagePattern(new Image(fileInputStream2)));
            circle3.setFill(new ImagePattern(new Image(fileInputStream3)));
            hBox.getChildren().addAll(circle, circle2, circle3);
            ListView<String> messageList = new ListView<String>();
            messageItems = FXCollections.observableArrayList();
            messageList.setItems(messageItems);
            ArrayList<String> message = new Sql().getMessage(namePlayer1, namePlayer2);
            messageItems.addAll(message);
            HBox sendBox = new HBox();
            TextField messageField = new TextField();
            messageField.setPrefWidth(200);
            Button button = new Button("send");
            button.setOnAction((event) -> {
                String text2 = messageField.getText();
                try {
                    client.msg(namePlayer2, text2);
                    this.messageItems.add(me + ": " + text2);
                    messageField.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            
            
            sendBox.getChildren().addAll(messageField, button);
            right.getChildren().addAll(messageList, sendBox);
            right.setSpacing(20);
            right.setAlignment(Pos.CENTER);
            right.setPadding(new Insets(20));
            borderPane.setRight(right);
        }
        VBox vbox = new VBox();
        borderPane.setTop(vbox);
        text = new Text();
        text.setFill(Color.WHITE);
        text.setFont(new Font(45));
        lblStatus.setTextFill(Color.WHITE);
        lblTitle.setTextFill(Color.WHITE);
        lblStatus.setFont(new Font(30));
        lblTitle.setFont(new Font(30));
        vbox.getChildren().addAll(lblStatus, lblTitle, text);
        vbox.setPadding(new Insets(50, 0, 0, 0));
        vbox.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board.length; j++) {
                if (computer) {
                    gridPane.add(Board[i][j], i, j);
                } else {
                    gridPane.add(cells[i][j], i, j);
                }
            }
        }
        borderPane.setCenter(gridPane);

        Image image = new Image(new FileInputStream("image\\tictactoeBack.png"));
        borderPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        System.out.println("boolean computer is " + computer);
        if (!computer) {
            start();
        }
        return borderPane;
    }

    public void start() {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(host, 8000);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // Control the game on a separate thread
        new Thread(() -> {
            try {
                // Get notification from the server
                int player = fromServer.readInt();

                // Am I player 1 or 2?
                if (player == PLAYER1) {
                    myToken = Nut.X;
                    otherToken = Nut.O;
                    Platform.runLater(() -> {
                        lblTitle.setText(namePlayer1);
                        lblStatus.setText("Waiting for " + namePlayer2.trim() + " to join");
                    });

                    // Receive startup notification from the server
                    fromServer.readInt(); // Whatever read is ignored

                    // The other player has joined
                    Platform.runLater(()
                            -> lblStatus.setText(namePlayer2.trim() + " has joined. I start first"));

                    // It is my turn
                    myTurn = true;
                } else if (player == PLAYER2) {
                    String str = namePlayer1;
                    namePlayer1 = namePlayer2;
                    namePlayer2 = str;
                    myToken = Nut.O;
                    otherToken = Nut.X;
                    Platform.runLater(() -> {
                        lblTitle.setText(namePlayer2.trim());
                        lblStatus.setText("Waiting for " + namePlayer1.trim() + " to move");
                    });
                }

                // Continue to play
                while (continueToPlay) {
                    if (player == PLAYER1) {
                        waitForPlayerAction(); // Wait for player 1 to move
                        sendMove(); // Send the move to the server
                        receiveInfoFromServer(); // Receive info from the server
                    } else if (player == PLAYER2) {
                        receiveInfoFromServer(); // Receive info from the server
                        waitForPlayerAction(); // Wait for player 2 to move
                        sendMove(); // Send player 2's move to the server
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Wait for the player to mark a cell
     */
    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }

        waiting = true;
    }

    /**
     * Send this player's move to the server
     */
    private void sendMove() throws IOException {
        toServer.writeInt(rowSelected); // Send the selected row
        toServer.writeInt(columnSelected); // Send the selected column
    }

    /**
     * Receive info from the server
     */
    private void receiveInfoFromServer() throws IOException {
        // Receive game status
        int status = fromServer.readInt();

        if (status == PLAYER1_WON) {
            // Player 1 won, stop playing
            continueToPlay = false;
            if (myToken == Nut.X) {
                Platform.runLater(() -> lblStatus.setText("I won! (X)"));
                new Sql().saveRecord(namePlayer1, namePlayer2, "win");
                new Sql().saveRecord(namePlayer2, namePlayer1, "lose");

            } else if (myToken == Nut.O) {
                Platform.runLater(()
                        -> lblStatus.setText(namePlayer1.trim() + " (X) has won!"));
                receiveMove();
            }
        } else if (status == PLAYER2_WON) {

            // Player 2 won, stop playing
            continueToPlay = false;
            if (myToken == Nut.O) {
                Platform.runLater(() -> lblStatus.setText("I won! (O)"));
                new Sql().saveRecord(namePlayer1, namePlayer2, "lose");
                new Sql().saveRecord(namePlayer2, namePlayer1, "win");
            } else if (myToken == Nut.X) {
                Platform.runLater(()
                        -> lblStatus.setText(namePlayer2.trim() + " (O) has won!"));
                receiveMove();
            }
        } else if (status == DRAW) {

            // No winner, game is over
            continueToPlay = false;
            Platform.runLater(()
                    -> lblStatus.setText("Game is over, no winner!"));

            if (myToken == Nut.O) {
                new Sql().saveRecord(namePlayer1, namePlayer2, "draw");
                new Sql().saveRecord(namePlayer2, namePlayer1, "draw");
                receiveMove();
            }
        } else {
            receiveMove();
            Platform.runLater(() -> lblStatus.setText("My turn"));
            myTurn = true; // It is my turn
        }
    }

    private void receiveMove() throws IOException {
        // Get the other player's move
        int row = fromServer.readInt();
        int column = fromServer.readInt();

        Platform
                .runLater(() -> {
                    Logic.paint(cells[row][column], otherToken);
                });

    }

    @Override
    public void onMessage(String fromLogin, String msgBody) {
        System.out.println("in on message method in Tic Tac Toe main");
        String line = fromLogin + ": " + msgBody;
        messageItems.add(line);
    }

    class Cell extends Rectangle {

        Nut nut;
        private int row;
        private int column;

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public Cell(int row, int column) {
            super(200, 200);
            this.column = column;
            this.row = row;
            setStroke(Color.BLACK);
            setFill(Color.ALICEBLUE);
            setOnMousePressed((event) -> {
                if (!computer) {
                    Logic.click(this, Main.this);
                } else {
                    Logic.click(this, null);
                }
                if (computer) {
                    if (Logic.isFinish() == 2) {
                        new Computer().play();
                    }
                }
            });
        }

        @Override
        public String toString() {
            return "" + nut;
        }

    }

}

enum Nut {
    O,
    X

}
