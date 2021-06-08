package client.Controller;

import data.JsonMap;
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

public class AuthController extends Controller {
    @FXML
    private AnchorPane toolwindow;
    @FXML
    private Button ButtonAction;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;



    @FXML
    private Label registerLabel;

    @FXML
    private void logIn(ActionEvent event) throws IOException {

        JsonMap map = new JsonMap();
        map.put("phNumber",loginTextField.getText(),String.class);
        map.put("password",passwordTextField.getText(),String.class);
        Client.sendToServer(map);
    }

    @FXML
    private void openRegisterWindow(MouseEvent event) throws IOException {
        Client.setActivePage("/static/register.fxml");
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
