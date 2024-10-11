import basic.client.UTF8Client;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException {
//        new BasicClient().start();
        new UTF8Client("localhost", 8080).start();
    }
}