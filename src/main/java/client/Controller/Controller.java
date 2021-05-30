package client.Controller;

import client.ClientApplication;
import data.entity.Message;
import data.entity.User;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.List;


public abstract class Controller {
    protected ClientApplication Client;
    @FXML
    private ImageView exitIcon;

    @FXML
    void onCloseWindow(MouseEvent event) {
        Platform.exit();
    }

    protected static final PseudoClass light_ = PseudoClass.getPseudoClass("light");
    public void setClient(ClientApplication client) {
        Client = client;
    }
    @FXML
    public void initialize(){
        exitIcon.addEventHandler(MouseEvent.MOUSE_CLICKED,this::onCloseWindow);
    }


    public void onFindUser(List<User> users){ }
    public void onReceiveMessage(Message message){ }

    public void afterInit(){}
    protected boolean themeState = false;
    protected void applyTheme(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            node.pseudoClassStateChanged(light_, themeState);
            if (node instanceof Parent)
                applyTheme((Parent)node);
        }
    }
}
