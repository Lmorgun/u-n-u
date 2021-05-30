package client.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class RegisterController extends Controller{
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
    private void onRegistry(ActionEvent event) throws IOException {
        Client.setActivePage("/static/main.fxml");
    }

    @FXML
    private void openLogin(MouseEvent event) throws IOException {
        Client.setActivePage("/static/auth.fxml");
    }

}
