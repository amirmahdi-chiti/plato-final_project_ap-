package Chat;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jim on 4/21/17.
 */
public class ChatClient {

    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;

    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    private ArrayList<GameListener> gameListeners = new ArrayList<>();
    private ArrayList<MessageGroupListener> messageGroupListener = new ArrayList<>();

    public ChatClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public static void main(String[] args) throws IOException {
        ChatClient client = new ChatClient("localhost", Server.ServerMain.PORT);

        if (!client.connect()) {
            System.err.println("Connect failed.");
        } else {
            System.out.println("Connect successful");

            if (client.login("guest", "guest")) {
//                System.out.println("Login successful");
//
//            } else {
//                System.err.println("Login failed");
//            }

                //client.logoff();
            }
        }
    }

    public void sendRequestGame(String sendTo) throws IOException {
        String cmd = "#GAME# " + sendTo + "\n";
        System.out.println("in method sendRequestGame");
        serverOut.write(cmd.getBytes());
    }

    public void msg(String sendTo, String msgBody) throws IOException {
        String cmd = "#MSG# " + sendTo + " " + msgBody + "\n";
        System.out.println("in msg method in chatclient server");
        serverOut.write(cmd.getBytes());
    }

    public boolean login(String login, String password) throws IOException {
        String cmd = "#LOGIN# " + login + " " + password + "\n";
        serverOut.write(cmd.getBytes());

        String response = bufferedIn.readLine();
        System.out.println("Response Line:" + response);

        if ("ok login".equalsIgnoreCase(response)) {
            startMessageReader();
            return true;
        } else {
            return false;
        }
    }

    public void logoff() throws IOException {
        String cmd = "#LOGOUT#\n";
        serverOut.write(cmd.getBytes());
    }

//    public void join(String group) throws IOException {
//        String cmd = "#JOIN# " + group + "\n";
//        serverOut.write(cmd.getBytes());
//    }
    private void startMessageReader() {
        Thread t = new Thread() {
            @Override
            public void run() {
                readMessageLoop();
            }
        };
        t.start();
    }

    private void readMessageLoop() {
        try {
            String line;
            while ((line = bufferedIn.readLine()) != null) {
                String[] tokens = StringUtils.split(line);
                if (tokens != null && tokens.length > 0) {
                    String cmd = tokens[0];
                    if ("online".equalsIgnoreCase(cmd)) {
                        handleOnline(tokens);
                    } else if ("offline".equalsIgnoreCase(cmd)) {
                        handleOffline(tokens);
                    } else if ("msg".equalsIgnoreCase(cmd)) {
                        String[] tokensMsg;
                        if (StringUtils.split(line, null, 3)[1].charAt(0) == '#') {
                            tokensMsg = StringUtils.split(line, null, 4);
                        } else {
                            tokensMsg = StringUtils.split(line, null, 3);
                        }
                        handleMessage(tokensMsg);
                    } else if ("game".equalsIgnoreCase(cmd)) {
                        handleGame(tokens);
                    } else if ("group".equalsIgnoreCase(cmd)) {
                        //handleGroup(tokens);
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(String[] tokensMsg) {
        String login = tokensMsg[1];
        String user;
        if (login.charAt(0) == '#') {
            user = tokensMsg[2];
            String msgBody = tokensMsg[3];
            System.out.println("in handle message(group) in chat client class ");
            for (MessageGroupListener listener : messageGroupListener) {
                int i = 0;
                System.out.println(i);
                i++;
                listener.onMessageGroup(login, user, msgBody);
            }
        } else {
            String msgBody = tokensMsg[2];
            user = tokensMsg[1];
            for (MessageListener listener : messageListeners) {
                listener.onMessage(login, msgBody);
            }
        }
    }

    private void handleOffline(String[] tokens) {
        String login = tokens[1];
        for (UserStatusListener listener : userStatusListeners) {
            listener.offline(login);
        }
    }

    private void handleOnline(String[] tokens) {
        String login = tokens[1];
        System.out.println("in handle Online");
        for (UserStatusListener listener : userStatusListeners) {
            listener.online(login);
        }
    }

    public boolean connect() {
        try {
            this.socket = new Socket(serverName, serverPort);
            System.out.println("Client port is " + socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUserStatusListener(UserStatusListener listener) {
        userStatusListeners.add(listener);
    }

    public void removeUserStatusListener(UserStatusListener listener) {
        userStatusListeners.remove(listener);
    }

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }

    public void addGameListener(GameListener listener) {
        gameListeners.add(listener);
    }

    public void removeGameListener(GameListener listener) {
        gameListeners.remove(listener);
    }

    public void addMessageGroupListener(MessageGroupListener listener) {
        messageGroupListener.add(listener);
    }

    private void handleGame(String[] tokens) {
        String login = tokens[1];
        for (GameListener listener : gameListeners) {
            listener.game(login);
        }
    }

//    private void handleGroup(String[] tokens) {
//        String group = tokens[1];
//        for (GroupListener listener : groupListeners) {
//            listener.join(group);
//        }
//    }
}
