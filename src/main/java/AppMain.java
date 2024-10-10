import basic.server.TCPServer;

public class AppMain {
    public static void main(String[] args) throws InterruptedException {
        new TCPServer(8080).start();
    }
}