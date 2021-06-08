package server;

import data.entity.User;
import server.connection.ConnectionPool;
import server.connection.QueryHandler;
import server.service.MessageService;
import server.service.UserService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Server {
    public static void main(String[] args) {
        Server server = getInstance();
        server.run();
    }

    private List<ClientOnServer> clients = new ArrayList<>();
    private MessageService msService;
    private UserService uService;
    static private Server instance = null;

    public static Server getInstance() {
        if (instance == null) {
            try {
                instance = new Server();
            } catch (SQLException throwAbles) {
                throwAbles.printStackTrace();
            }
        }
        return instance;
    }

    void removeClient(ClientOnServer client) {
        clients.remove(client);
    }

    void addClient(Socket socket) throws IOException {
        ClientOnServer nuw = new ClientOnServer(socket,
                new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),
                        true), msService, uService);
        clients.add(nuw);
        nuw.start();
    }

    private Server() throws SQLException {
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        QueryHandler qHandler = new QueryHandler(new ConnectionPool("jdbc:sqlserver://localhost;database=MessengerDB;integratedSecurity=true", 5));
        msService = new MessageService(qHandler);
        uService = new UserService(qHandler);
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

    public List<ClientOnServer> getClientsByUser(User sender) {
        return clients.stream().filter(x ->
                (x.getAccount() != null &&
                        x.getAccount().equals(sender))).collect(Collectors.toList());
    }
}

