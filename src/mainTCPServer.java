import tcp.Server;

public class mainTCPServer {
    public static void main(String[] args) {
        Server server = new Server(7000, 10);
        server.startServer();
    }
}
