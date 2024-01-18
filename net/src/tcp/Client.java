package tcp;

import util.Command;
import verwaltungsImp.verkaufsAutomat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    private String serverName;
    private int port;
    public Client(String serverName, int port){
        this.serverName = serverName;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getServerName() {
        return serverName;
    }
    public void startClient(){
        try(Socket socket = new Socket(this.serverName, this.port)){
            Scanner inputScanner = new Scanner(System.in);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            boolean run = true;
            while(run){
                out.writeUTF(inputScanner.nextLine());
                String input = in.readUTF();
                if(input.equals("beenden")){
                    System.out.println("beende den Client");
                    run = false;
                }else{
                    System.out.println("nicht beenden");
                }
            }
            inputScanner.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}