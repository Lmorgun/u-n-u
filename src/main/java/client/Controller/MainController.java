package client.Controller;

import client.CustomNode.ChatNode;
import client.CustomNode.MessageNode;
import data.entity.Message;
import data.entity.User;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController extends Controller {

    @FXML
    private AnchorPane toolwindow;
    @FXML
    private BorderPane mainWindow;

    @FXML
    private ImageView minimizeIcon;

    @FXML
    private Label friendName;

    @FXML
    private TextField textForSearch;

    @FXML
    private ImageView searchIcon;

    @FXML
    private Label urName;

    @FXML
    private Label urPhNumb;

    @FXML
    private Label urNick;

    @FXML
    private ImageView editNameIcon;

    @FXML
    private ImageView OnSwitchThemeIcon;

    @FXML
    private ImageView editNickIcon;

    @FXML
    private TextField textMessField;

    @FXML
    private ImageView sendIcon;

    @FXML
    private VBox friendList;

    @FXML
    private VBox messList;

    private User friend = null;

    private boolean isSearching = false;
    @FXML
    private ImageView backButton;

    @FXML
    void backIcon(MouseEvent event) {
        isSearching = false;
        drawDialogs(Client.getUrSMS());
        backButton.setDisable(true);
    }

    @FXML
    private void updateName(MouseEvent event) {

    }

    @FXML
    private void updateNick(MouseEvent event) {

    }

    @FXML
    private void changeTheme(MouseEvent event) {
        themeState = !themeState;
        applyTheme(mainWindow);

    }

    @FXML
    private void onMinimize(MouseEvent event) {
        Stage obj = (Stage) minimizeIcon.getScene().getWindow();
        obj.setIconified(true);
    }


    @FXML
    private void onSending(Event event) {

        if (!textMessField.getText().isBlank() && friend!=null) {
            Message mess = new Message(Client.getPip(), friend, textMessField.getText());
            Client.sendMessage(mess);
        }
        textMessField.clear();

    }

    @Override
    public void onFindUser(List<User> users) {
        if (users.isEmpty()) {
            return;
        }
        isSearching = true;
        backButton.setDisable(false);
        Map<User,List<Message>> fondUser = new HashMap<>();
        for (User user : users) {
            Message lastMessage = null;
            if (Client.getUrSMS().containsKey(user)) {
                List<Message> msgsUser = Client.getUrSMS().get(user);
                if(msgsUser.size() > 0)
                {
                    lastMessage = msgsUser.get(msgsUser.size() - 1);
                }
            }
            List<Message> g = new ArrayList<>();
            if(lastMessage!=null){
                g.add(lastMessage);
            }
            fondUser.put(user,g);

        }
        drawDialogs(fondUser);
    }

    @Override
    public void onReceiveMessage(Message message) {
        if (!isSearching) {
            drawDialogs(Client.getUrSMS());
        }
        if (message.getOtherUser(Client.getPip()).equals(friend)) {
            var b = new MessageNode(message.getText(),
                    message.getDate().format(DateTimeFormatter.ofPattern("HH:mm")),
                    Client.getPip().getPhone_number().equals(message.getSender().getPhone_number()));
            applyTheme(b);
            messList.getChildren().add(b);
        }

    }

    double xOffset = 0;
    double yOffset = 0;

    @Override
    public void initialize() {
        super.initialize();
        Platform.runLater(() -> {
            if (toolwindow != null) {
                toolwindow.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> {
                    Window window = Client.getStage();
                    window.setX(e.getScreenX() + xOffset);
                    window.setY(e.getScreenY() + yOffset);
                });

                toolwindow.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                    Window window = Client.getStage();
                    xOffset = window.getX() - e.getScreenX();
                    yOffset = window.getY() - e.getScreenY();
                });

            }
        });

    }

    @Override
    public void afterInit() {
        urName.setText(Client.getPip().getName());
        urNick.setText(Client.getPip().getNick());
        urPhNumb.setText(Client.getPip().getPhone_number());
        drawDialogs(Client.getUrSMS());

    }

    public void drawDialogs(Map<User, List<Message>> userList) {
        friendList.getChildren().clear();

        for (var user : userList.entrySet()
        ) {
            var a = new ChatNode(user.getKey(),
                    user.getValue().get(user.getValue().size() - 1).getText());
            applyTheme(a);
            a.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    var m = (ChatNode) mouseEvent.getSource();
                    drawMessages(m.getUser());
                }
            });
            friendList.getChildren().add(a);
        }

    }

    public void drawMessages(User user) {
        friendName.setText(user.getName());
        messList.getChildren().clear();
        Map<User, List<Message>> userList = Client.getUrSMS();
        for (var message : userList.get(user)
        ) {
            var b = new MessageNode(message.getText(),
                    message.getDate().format(DateTimeFormatter.ofPattern("HH:mm")),
                    Client.getPip().getPhone_number().equals(message.getSender().getPhone_number()));
            applyTheme(b);
            messList.getChildren().add(b);
        }
        friend = user;
    }

    @FXML
    private void searchAction(Event event) {
        if (!textForSearch.getText().isBlank()) {
            Client.doSearch(textForSearch.getText());
        }

    }

}