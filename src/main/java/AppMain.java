import basic.server.EchoServer;

public class AppMain {
    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8080).start();
    }
}