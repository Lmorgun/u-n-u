package server;

import com.google.gson.reflect.TypeToken;
import data.JsonMap;
import data.entity.Message;
import data.entity.User;
import server.service.MessageService;
import server.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class ClientOnServer extends Thread {
    private final Socket socket;
    private User account;
    private final PrintWriter stream;
    private final MessageService messSrv;
    private final UserService urSrv;


    public User getAccount() {
        return account;
    }

    public PrintWriter getStream() {
        return stream;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    public ClientOnServer(Socket socket, PrintWriter stream, MessageService msService, UserService urService) {
        this.socket = socket;

        this.stream = stream;
        account = null;
        messSrv = msService;
        urSrv = urService;
    }

    @Override
    public void run() {
        try {
            BufferedReader waiter = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("ClientOnServer new instance");

            while (!socket.isClosed()) {
                try {
                    String json = waiter.readLine();
                    System.out.println(json);
                    JsonMap obj = JsonMap.fromJson(json);
                    if (obj.contains("new message")) {
                        Message mess = obj.get("new message", Message.class);
                        messSrv.createMessage(mess);
                        User receiver = mess.getReceiver();
                        User sender = mess.getSender();
                        List<ClientOnServer> clients = Server.getInstance().getClientsByUser(receiver);
                        if (!receiver.equals(sender)) {
                            clients.addAll(Server.getInstance().getClientsByUser(sender));
                        }
                        for (var client : clients
                        ) {
                            client.getStream().println(obj);
                        }
                    }

                    if (obj.contains("search")) {
                        String str = obj.get("search", String.class);
                        List<User> finder = urSrv.getAllUsersByStr(str);
                        JsonMap ght = new JsonMap();
                        ght.put("search", finder, new TypeToken<List<User>>() {
                        }.getClass());
                        stream.println(ght);
                    }

                    if (obj.contains("phNumber")) {
                        String phNumber = obj.get("phNumber", String.class);
                        String password = obj.get("password", String.class);
                        User user = urSrv.authorization(phNumber, password);
                        if (user != null) {
                            this.account = user;
                            JsonMap ght = new JsonMap();
                            ght.put("user", user, User.class);
                            ght.put("allmessages", messSrv.allMessages(user), new TypeToken<List<Message>>() {
                            }.getType());
                            stream.println(ght);
                        }
                    }

                    if (obj.contains("ruser")) {
                        User user = obj.get("ruser", User.class);
                        User alreadyHere = urSrv.getByPhNumber(user.getPhone_number());
                        if (alreadyHere == null) {
                            urSrv.register(user);
                            JsonMap ght = new JsonMap();
                            user = urSrv.authorization(user.getPhone_number(), user.getPassword());
                            ght.put("ruser", user, User.class);


                            stream.println(ght);
                        }

                    }
                }catch(IOException | SQLException ex){
                    System.err.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            System.out.println("ClientOnServer disconnected");
            Server.getInstance().removeClient(this);
        }


    }
}
