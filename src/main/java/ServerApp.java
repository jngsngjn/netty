import basic.server.tcp.EchoServer;

public class ServerApp {
    public static void main(String[] args) throws InterruptedException {
         new EchoServer(8080).start();
        // new HttpServer(8080).start();

//        new KoreanEncodingTestServer(8081, Charset.forName("EUC-KR")).start();
//        new KoreanServer(8082, StandardCharsets.UTF_8).start();
    }
}