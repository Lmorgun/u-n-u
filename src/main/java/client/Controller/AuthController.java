package client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AuthController extends Controller {

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
        Client.setActivePage("/static/main.fxml");
    }

    @FXML
    private void openRegisterWindow(MouseEvent event) throws IOException {
        Client.setActivePage("/static/register.fxml");
    }

    @Override
    public void initialize() {
      super.initialize();
    }
}
