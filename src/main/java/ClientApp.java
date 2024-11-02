import basic.client.EUCKRClient;
import basic.client.UTF8Client;

public class ClientApp {
    public static void main(String[] args) {
        utf8Client.start();
        euckrClient.start();
    }

    static Thread utf8Client = new Thread(() -> {
        try {
            new UTF8Client("localhost", 8080).connect();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    });

    static Thread euckrClient = new Thread(() -> {
        try {
            new EUCKRClient("localhost", 8080).connect();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    });
}