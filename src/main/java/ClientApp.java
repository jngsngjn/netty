import basic.client.EUCKRClient;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException {
//        new BasicClient().start();
//        new UTF8Client("localhost", 8080).start();
        new EUCKRClient("localhost", 8080).start();
    }
}