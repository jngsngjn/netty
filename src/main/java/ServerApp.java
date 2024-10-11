import basic.server.http.HttpServer;

public class ServerApp {
    public static void main(String[] args) throws InterruptedException {
        // new TCPEchoServer(8080).start();
        new HttpServer(8080).start();
    }
}