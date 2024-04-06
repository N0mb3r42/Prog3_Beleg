package tcp;

import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import kuchen.Obstkuchen;
import kuchenImp.KuchenImp;
import kuchenImp.ObstkuchenImp;
import util.Command;
import verwaltungsImp.HerstellerImp;
import verwaltungsImp.verkaufsAutomat;
import kuchen.Allergen;
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
    private String printException(Exception e, Command.Mode mode){
        switch (mode) {
            case CREATE -> {
                return "Fehlerhafte Eingabe " + e.getMessage() + "\nInput sollte Format: [String] [String] [Decimal] [Integer] [Integer] [String, String] [String] haben";
            }
            case READ -> {
                return "Fehlerhafte Eingabe " + e.getMessage() + "\nInput sollte entweder 'all' oder [Integer] sein";
            }
            case UPDATE, DELETE -> {
                return "Fehlerhafte Eingabe " + e.getMessage() + "\nInput sollte Format: [integer] haben";
            }
        }
        return null;
    }
    private String command_handling(String input) {
        if (input.startsWith(":")){
            return c.nextCommand(input);
        }else{
            String[] cakeData = input.split(" ");
            System.out.println(cakeData);
            switch (c.getMode()) {
                case CREATE -> { //Obstkuchen Alice 4,50 386 36 Gluten,Erdnuss Apfel
                    return this.console.createInputHandling(cakeData, this.automat);
                }
                case READ -> {
                    return this.console.readInputHandling(cakeData, this.automat);
                }
                case UPDATE -> {
                    return this.console.updateInputHandling(cakeData, this.automat);
                }
                case DELETE -> {
                    return this.console.deleteInputHandling(cakeData, this.automat);
                }
                case NOMODE -> {
                    return "No Mode selected\n";
                }
            }
        }
        return null;
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
                        String return_value = command_handling(input);
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