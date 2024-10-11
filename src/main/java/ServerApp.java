import basic.server.tcp.TCPEchoServer;

public class ServerApp {
    public static void main(String[] args) throws InterruptedException {
        new TCPEchoServer(8080).start();
    }
}