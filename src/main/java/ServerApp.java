import basic.server.tcp.MyServer;

public class ServerApp {
    public static void main(String[] args) throws InterruptedException {
        new MyServer(8080).start();
    }
}