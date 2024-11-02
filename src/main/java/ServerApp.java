import basic.server.http.HttpServer;

public class ServerApp {
    public static void main(String[] args) throws InterruptedException {
        new HttpServer(8080).start();
//        new UTF8Server(8080).start();
    }
}