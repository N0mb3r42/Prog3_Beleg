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



public class Server{
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
                    if (cakeData.length == 1) {
                        HerstellerImp hersteller = new HerstellerImp(cakeData[0]);
                        this.automat.addHersteller(hersteller.getName());
                    } else {
                        try {
                            HerstellerImp hersteller = new HerstellerImp(cakeData[1]);
                            BigDecimal preis = new BigDecimal(cakeData[2].replace(",", "."));
                            int naehrwert = Integer.parseInt(cakeData[3]);
                            Duration haltbarkeit = Duration.ofDays(Long.parseLong(cakeData[4]));
                            List<Allergen> allergene = null;
                            if (!Objects.equals(cakeData[5], ",")) {
                                allergene = this.parseAllergene(cakeData[5].split(","));
                            }
                            this.automat.addHersteller(hersteller.getName());
                            boolean response = this.automat.create(
                                    cakeData[0],
                                    hersteller,
                                    preis,
                                    naehrwert,
                                    haltbarkeit,
                                    allergene,
                                    (cakeData[0].equals("Obstkuchen") || cakeData[0].equals("Obsttorte")) ? cakeData[6] : null,
                                    (cakeData[0].equals("Kremkuchen") || cakeData[0].equals("Obsttorte")) ? cakeData[7] : null
                            );
                            if (response){
                                return "Kuchen wurde eingefügt!\n";
                            }else{
                                return "Irgendwas hat nicht funktioniert (Limit oder Kuchen war fehlerhaft)\n";
                            }
                        } catch (Exception e) {
                            return this.printException(e, c.getMode());
                        }
                    }
                }
                case READ -> {
                    try {
                        if (cakeData[0].equals("all")) {
                            StringBuilder return_string = new StringBuilder("Maximal " + this.automat.getAnzahlFaecher() + " Fächer\n");
                            for (KuchenImp k : automat.readKuchen()) {
                                return_string.append(k.toString()).append("\n");

                            }
                            return return_string.toString();
                        } else {
                            KuchenImp k = automat.readKuchen(Integer.parseInt(cakeData[0]));
                            if (k != null) {
                                return k.toString() + "\n";
                            } else {
                                return "Fachnummer " + cakeData[0] + " hat keinen Kuchen\n";
                            }
                        }
                    } catch (Exception e) {
                        this.printException(e, c.getMode());
                    }
                }
                case UPDATE -> {
                    try {
                        if (this.automat.update(Integer.parseInt(cakeData[0]))) {
                            return "Das Inspektionsdatum des Kuchen in Fachnummer: " + cakeData[0] + " wurde auf heute gesetzt.\n";
                        } else {
                            return "In Fachnummer: " + cakeData[0] + " befindet sich kein Kuchen.\n";
                        }
                    } catch (Exception e) {
                        this.printException(e, c.getMode());
                    }
                }
                case DELETE -> {
                    try {
                        if (this.automat.delete(Integer.parseInt(cakeData[0]))) {
                            return "Der Kuchen in Fachnummer: " + cakeData[0] + " wurde entfernt\n";
                        } else {
                            return "In Fachnummer: " + cakeData[0] + " befindet sich kein Kuchen.\n";
                        }
                    } catch (Exception e) {
                        this.printException(e, c.getMode());
                    }
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