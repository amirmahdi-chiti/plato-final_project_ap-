/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author khatam
 */
public class ServerWorker extends Thread{

    private final Server server;
    private final Socket client;
    private OutputStream outPutStram;

    ServerWorker(Server server, Socket client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void run() {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void handleLogin(OutputStream outPutStram, String[] split) {
        String username = split[1];
        String pass = split[2];
        
    }
    }

    private void handleMessage(String[] tokensMsg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void handleJoin(String[] split) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void handleLeave(String[] split) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
