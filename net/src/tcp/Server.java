package tcp;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kuchen.Obstkuchen;
import kuchenImp.ObstkuchenImp;
import util.Command;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;
import kuchen.Allergen;



public class Server {
    private int port;
    private verkaufsAutomat automat;
    private Command c;

    public Server(int port, int kapazitaet) {
        this.port = port;
        this.automat = new verkaufsAutomat(kapazitaet);
        this.c = new Command();
    }

    private List<Allergen> parseAllergene(String[] text) {
        List<Allergen> allergene = new ArrayList<>();
        if (Objects.equals(text[0], "")) {
            return null;
        }
        for (String s : text) {
            for (Allergen a : Allergen.values()) {
                if (a.name().equals(s)) {
                    allergene.add(a);
                }
            }
        }
        return allergene;
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
                        out.writeUTF(checkForExit(input));
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