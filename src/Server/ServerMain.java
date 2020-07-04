
package Server;

public class ServerMain {
    public static final int PORT = 4000;
    public static Server server = new Server();
    public static void main(String[] args) {
//        Server server = new Server();
        server.start();
    }
            
}
