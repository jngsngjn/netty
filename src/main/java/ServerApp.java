import basic.server.tcp.EchoServer;

public class ServerApp {
    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8080).start();
    }
}