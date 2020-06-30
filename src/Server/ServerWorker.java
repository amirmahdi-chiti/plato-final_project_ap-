/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Login.Sql;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author khatam
 */
public class ServerWorker extends Thread{

    private final Server server;
    private final Socket client;
    private OutputStream outPutStram;
    private String login = null;
    ServerWorker(Server server, Socket client) {
        this.server = server;
        this.client = client;
    }

    public String getLogin() {
        return login;
    }
    
    @Override
    public void run() {
        try {
            handleActivity();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void  handleActivity() throws IOException{
        InputStream inputStream = client.getInputStream();
        outPutStram = client.getOutputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = bufferedReader.readLine())!=null){
            String[] split = StringUtils.split(line);

             if (split != null && split.length > 0) {
                String cmd = split[0];

                if (cmd.equals("#LOGOUT#")) {
                    handleLogoff();
                    break;
                } else if (cmd.equals("#LOGIN#")) {
                    handleLogin(outPutStram, split);
                } else if (cmd.equals("#MSG#")) {
                    String[] tokensMsg = StringUtils.split(line, null, 3);
                    System.out.println("handle message in serverWorker class");
                    handleMessage(tokensMsg);
                } else if (cmd.equals("#JOIN#")) {
                    handleJoin(split);
                } else if (cmd.equals("#LEAVE#")) {
                    handleLeave(split);
                
            }
        }
    }
}

    private void handleLogoff() {
        System.out.println("in handlde logoff method");
        server.removeServerWorker(this);
        List<ServerWorker> workerList = server.getServerWorkerList();

        // send other online users current user's status
        String onlineMsg = "offline " + login + "\n";
        for(ServerWorker worker : workerList) {
            if (!login.equals(worker.getLogin())) {
                worker.send(onlineMsg);
            }
        }
        try {
            client.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleLogin(OutputStream outPutStram, String[] split) throws IOException {
        String username = split[1];
        String pass = split[2];
        System.out.println("in handle login method");
        if(new Sql().searchLogin(username, pass)){
            
                String msg = "ok login\n";
                outPutStram.write(msg.getBytes());
                this.login = username;
                System.out.println("User logged in succesfully: " + login);

                List<ServerWorker> workerList = server.getServerWorkerList();

                // send current user all other online logins
                for(ServerWorker worker : workerList) {
                    if (worker.getLogin()!= null) {
                        if (!login.equals(worker.getLogin())) {
                            String msg2 = "online " + worker.getLogin() + "\n";
                            send(msg2);
                        }
                    }
                }

                // send other online users current user's status
                String onlineMsg = "online " + login + "\n";
                for(ServerWorker worker : workerList) {
                    if (!login.equals(worker.getLogin())) {
                        worker.send(onlineMsg);
                    }
                }
            } else {
                String msg = "error login\n";
                outPutStram.write(msg.getBytes());
                System.err.println("Login failed for " + login);
            }
        }

    private void send(String msg) {
        if (login != null) {
            try {
                outPutStram.write(msg.getBytes());
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }
   
    // format: "msg" "login" body...
    // format: "msg" "#topic" body...
    private void handleMessage(String[] tokensMsg) {
         String sendTo = tokensMsg[1];
        String body = tokensMsg[2];

        boolean isTopic = sendTo.charAt(0) == '#';

        List<ServerWorker> workerList = server.getServerWorkerList();
        for(ServerWorker worker : workerList) {
            if (isTopic) {
                if (worker.isMemberOfTopic(sendTo)) {
                    String outMsg = "msg " + sendTo + ":" + login + " " + body + "\n";
                    worker.send(outMsg);
                }
            } else {
                if (sendTo.equalsIgnoreCase(worker.getLogin())) {
                    String outMsg = "msg " + login + " " + body + "\n";

                    worker.send(outMsg);
                       new Sql().saveMessage(body, login, sendTo);
                       System.out.println("in handlemessage method");

                }
            }
        }
    }

    private void handleJoin(String[] split) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void handleLeave(String[] split) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isMemberOfTopic(String sendTo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}