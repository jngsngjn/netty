import basic.client.MyClient;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException {
        new MyClient("localhost", 8080).connect();
    }
}