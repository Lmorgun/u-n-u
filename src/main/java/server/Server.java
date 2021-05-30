package server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


    public class Server {
        public static void main(String[] args) {
            Server server = getInstance();
            server.run();
        }

        private List<ClientOnServer> clients = new ArrayList<>();
        private int countId = 1;

        static private Server instance = null;

        public static Server getInstance() {
            if (instance == null) {
                instance = new Server();
            }
            return instance;
        }

        void removeClient(ClientOnServer client){
            clients.remove(client);
        }

        void addClient(Socket socket) throws IOException {
            clients.add(new ClientOnServer(socket, countId++,
                    new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),
                            true)));
        }

        private Server() {
        }

        public void run() {
            try (ServerSocket server = new ServerSocket(4004)) {
                System.out.println("Starting server...");
                while (!server.isClosed()) {
                    Socket client = server.accept();
                    addClient(client);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println("Server has been closed.");
            }
        }
    }

