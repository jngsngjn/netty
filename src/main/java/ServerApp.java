import basic.server.tcp.EchoServer;

public class ServerApp {
    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8080).start();
//         new HttpServer(8080).start();

//        new StaticEncodingServer(8081, Charset.forName("EUC-KR")).start();
//        new StaticEncodingServer(8082, StandardCharsets.UTF_8).start();

//        new DynamicEncodingServer(8080).start();
    }
}