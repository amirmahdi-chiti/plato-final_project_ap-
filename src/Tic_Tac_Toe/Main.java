package Tic_Tac_Toe;

import static Tic_Tac_Toe.TicTacToeConstants.PLAYER1;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main implements TicTacToeConstants {

    static Nut turn = Nut.X;
    static Cell[][] Board = new Cell[3][3];
    Cell[][] cells = new Cell[3][3];
    static Text text;
    private boolean myTurn = false;
    // Indicate the token for the player
    private Nut myToken = null;
    private Nut otherToken = null;
    // Indicate selected row and column by the current move
    private int rowSelected;
    private int columnSelected;

    // Input and output streams from/to server
    private DataInputStream fromServer;
    private DataOutputStream toServer;

    // Continue to play?
    private boolean continueToPlay = true;

    Label lblTitle = new Label();
    // Create and initialize a status label
    Label lblStatus = new Label();

    // Wait for the player to mark a cell
    private boolean waiting = true;

    // Host name or ip
    private String host = "localhost";

    public Main() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Board[i][j] = new Cell(i, j);
                cells[i][j] = new Cell(i, j);
            }
        }
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

    public Scene sceneBuider() {
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();
        HBox hBox = new HBox();
        borderPane.setTop(hBox);
        text = new Text();
        hBox.getChildren().addAll(lblStatus, lblTitle, text);
        hBox.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < Board.length; i++) {
            for (int j = 0; j < Board.length; j++) {
                //gridPane.add(Board[i][j], i, j);
                gridPane.add(cells[i][j], i, j);
            }
        }
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        start();
        return scene;
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
                        lblTitle.setText("Player 1 with token 'X'");
                        lblStatus.setText("Waiting for player 2 to join");
                    });

                    // Receive startup notification from the server
                    fromServer.readInt(); // Whatever read is ignored

                    // The other player has joined
                    Platform.runLater(()
                            -> lblStatus.setText("Player 2 has joined. I start first"));

                    // It is my turn
                    myTurn = true;
                } else if (player == PLAYER2) {
                    myToken = Nut.O;
                    otherToken = Nut.X;
                    Platform.runLater(() -> {
                        lblTitle.setText("Player 2 with token 'O'");
                        lblStatus.setText("Waiting for player 1 to move");
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
            } else if (myToken == Nut.O) {
                Platform.runLater(()
                        -> lblStatus.setText("Player 1 (X) has won!"));
                receiveMove();
            }
        } else if (status == PLAYER2_WON) {
            // Player 2 won, stop playing
            continueToPlay = false;
            if (myToken == Nut.O) {
                Platform.runLater(() -> lblStatus.setText("I won! (O)"));
            } else if (myToken == Nut.X) {
                Platform.runLater(()
                        -> lblStatus.setText("Player 2 (O) has won!"));
                receiveMove();
            }
        } else if (status == DRAW) {
            // No winner, game is over
            continueToPlay = false;
            Platform.runLater(()
                    -> lblStatus.setText("Game is over, no winner!"));

            if (myToken == Nut.O) {
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
                Logic.click(this,Main.this);
             /*   if(Logic.isFinish()==2){
            new Computer().play();
            }*/

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
