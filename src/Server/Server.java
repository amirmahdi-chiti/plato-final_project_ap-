package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread{
    public  static  ArrayList<ServerWorker> serverWorkerList = new ArrayList<ServerWorker>();
    public Server() {
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(ServerMain.PORT);
            while(true){
                Socket client = serverSocket.accept();
                System.out.println("accept connection from " + client);
                ServerWorker serverWorker = new ServerWorker(this,client);
                serverWorkerList.add(serverWorker);
                serverWorker.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<ServerWorker> getServerWorkerList() {
        return serverWorkerList;
    }
    public void removeServerWorker(ServerWorker serverWork){
       serverWorkerList.remove(serverWork);
    }
}
