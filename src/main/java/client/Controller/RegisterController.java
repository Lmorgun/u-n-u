package client.Controller;

import data.JsonMap;
import data.entity.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegisterController extends Controller{
    @FXML
    private AnchorPane toolwindow;
    @FXML
    private Button onRegister;

    @FXML
    private Label openLogInWindow;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phNumberTextField;

    @FXML
    private PasswordField passwordTextField2;

    @FXML
    private TextField dateOfBirthTextField;

    @FXML
    private TextField nickTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private void onRegistry(ActionEvent event) throws IOException, ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User newUser = new User(phNumberTextField.getText(),nameTextField.getText(),surnameTextField.getText(),
                nickTextField.getText(),passwordTextField.getText(),
                LocalDateTime.parse(dateOfBirthTextField.getText(), formatter));
        JsonMap map = new JsonMap();
        map.put("ruser",newUser,User.class);
        Client.sendToServer(map);

    }

    @FXML
    private void openLogin(MouseEvent event) throws IOException {
        Client.setActivePage("/static/auth.fxml");
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

}
