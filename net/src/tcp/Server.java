package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import util.Command;
import verwaltungsImp.verkaufsAutomat;
import viewControl.Console;


public class Server{
    private int port;
    private verkaufsAutomat automat;
    private Command c;
    private Console console;

    public Server(int port, int kapazitaet) {
        this.port = port;
        this.automat = new verkaufsAutomat(kapazitaet);
        this.c = new Command();
        this.console = new Console(this.automat);
    }

    private String checkForExit(String input) {
        if (input.equals(":e")) {
            return "beenden";
        } else {
            return "nicht beenden";
        }
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(this.port);) {
            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    System.out.println("client: " + socket.getInetAddress() + ":" + socket.getPort());
                    while (true) {
                        String input = in.readUTF();
                        System.out.println("recieved: " + input);
                        String return_value = this.console.inputHandling(input);
                        if (return_value != null){
                            out.writeUTF(return_value);
                        }
                    }
                } catch (SocketException | EOFException e) {
                    System.out.println("client disconnect");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}