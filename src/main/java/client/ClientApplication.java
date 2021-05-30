package client;

import client.Controller.Controller;
import com.google.gson.reflect.TypeToken;
import data.JsonMap;
import data.entity.Message;
import data.entity.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientApplication extends Application {
    private Stage window;
    private Controller controller;
    private Map<User, List<Message>> urSMS;
    private User pip;
    private PrintWriter serverWriter;
    private Socket socket;

    public Map<User, List<Message>> getUrSMS() {
        return urSMS;
    }

    public User getPip() {
        return pip;
    }

    @Override
    public void start(Stage stage) throws IOException {
        urSMS = new HashMap<>();

        User person = new User("3800000", "Nick", "n", "n", "sssssss");
        pip = person;
        User person1 = new User("3800400", "No", "n", "n", "sssssss");
        User person11 = new User("3800040", "yr", "n", "n", "sssssss");
        User person111 = new User("3800004", "lllll", "n", "n", "sssssss");
        urSMS.put(person1, List.of(
                new Message(1, person, person1, "ddddddd", LocalDateTime.now()),
                new Message(2, person, person1, "ddddddd", LocalDateTime.now()),
                new Message(22, person1, person, "ddddddd", LocalDateTime.now()),
                new Message(3, person1, person, "ddddddd", LocalDateTime.now()),
                new Message(4, person, person1, "ddddddd", LocalDateTime.now()),
                new Message(5, person1, person, "ddddddd", LocalDateTime.now()),
                new Message(6, person, person1, "ddddddd", LocalDateTime.now())
        ));
        urSMS.put(person11, List.of(
                new Message(1, person, person11, "ddddddd", LocalDateTime.now()),
                new Message(2, person, person11, "ddddddd", LocalDateTime.now()),
                new Message(22, person11, person, "ddddddd", LocalDateTime.now()),
                new Message(3, person11, person, "ggg", LocalDateTime.now()),
                new Message(4, person, person11, "ddy", LocalDateTime.now()),
                new Message(5, person11, person, "ujk", LocalDateTime.now()),
                new Message(6, person, person11, "ddddddd", LocalDateTime.now())
        ));
        urSMS.put(person111, List.of(
                new Message(1, person, person111, "ddddddd", LocalDateTime.now()),
                new Message(2, person, person111, "ddddddd", LocalDateTime.now()),
                new Message(22, person111, person, "ddddddd", LocalDateTime.now()),
                new Message(3, person111, person, "dggggggk irjfoerjgvrei fkcvmed.olvjkreing erofkjemornfveo " +
                        "erofjeorjeor eorjfoeirjeruhjeroiuh eroifjeoirh ", LocalDateTime.now()),
                new Message(4, person, person111, "ddddddd", LocalDateTime.now()),
                new Message(5, person111, person, "ddddddd", LocalDateTime.now()),
                new Message(6, person, person111, "ddddddd", LocalDateTime.now())
        ));
       socket = new Socket("localhost", 4004);
        serverWriter = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader waiter = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread serverReader = new Waiter(waiter, this);
        serverReader.start();
        stage.initStyle(StageStyle.TRANSPARENT);
        window = stage;
        setActivePage("/static/auth.fxml");
        window.show();
    }

    @Override
    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Socket closed");
        }
        System.out.println("Application closed");
        System.exit(0);
    }

    public void addMessage(Message message) {
        User otherUser;
        if (message.getSender().getPhone_number().equals(pip.getPhone_number())) {
            otherUser = message.getReceiver();
        } else {
            otherUser = message.getSender();
        }
        if (urSMS.containsKey(otherUser)) {
            urSMS.get(otherUser).add(message);
        } else {
            List<Message> list = new ArrayList<>();
            list.add(message);
            urSMS.put(otherUser, list);
        }
    }

    public void sendMessage(Message message) {
        JsonMap obj = new JsonMap();
        obj.put("new message", message, message.getClass());
        sendToServer(obj);
    }

    public void sendToServer(JsonMap obj) {
        serverWriter.println(obj);
    }

    public void onGetFromServer(JsonMap obj) {
        if (obj.contains("new message")) {
            Message message = obj.get("new message", Message.class);
            addMessage(message);
            controller.onReceiveMessage(message);
        }
        if(obj.contains("user")){
            pip = obj.get("user",User.class);
        }
        if(obj.contains("old messages")){
            TypeToken<Map<User, List<Message>>> token = new TypeToken<>() {
            };
            urSMS = obj.get("old messages", token.getType());
        }

        if(obj.contains("search")){
            TypeToken<List<User>> token = new TypeToken<>() {
            };
            controller.onFindUser(obj.get("search",token.getType()));
        }
    }

    public void doSearch(String searchField){
        JsonMap obj = new JsonMap();
        obj.put("search", searchField, String.class);
        sendToServer(obj);
    }

    public void setActivePage(String pathPage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(pathPage));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();
        controller.setClient(this);
        controller.afterInit();
        Platform.runLater(() -> window.setScene(scene));
    }

    public static void main(String[] args) {
        Application.launch();
    }
}
