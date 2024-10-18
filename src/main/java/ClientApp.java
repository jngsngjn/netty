import basic.client.ChannelOptionTestClient;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException {
//        new BasicClient().start();
//        new UTF8Client("localhost", 8080).start();
//        new EUCKRClient("localhost", 8080).start();
        new ChannelOptionTestClient(8080).connect();
    }
}