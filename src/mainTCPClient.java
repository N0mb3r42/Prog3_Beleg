import tcp.Client;
import util.Command;
import viewControl.Console;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class mainTCPClient {
    public static void main(String[] args) {
        Client client = new Client("localhost", 7000);
        client.startClient();
    }
}
