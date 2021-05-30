package client.CustomNode;

import data.entity.User;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ChatNode extends VBox {
    Label name;
    Label lastMess;
    User user;

    public User getUser() {
        return user;
    }

    public ChatNode(User user, String lastMessage) {
        name = new Label(user.getName());
        lastMess = new Label(lastMessage);
        this.user = user;
        getChildren().addAll(name,lastMess);
        this.getStyleClass().add("friend");
        name.getStyleClass().add("textColor");
        lastMess.getStyleClass().add("textColor");
        name.setFont( Font.font("Cambria", FontWeight.BOLD,16));
        this.setPadding(new Insets(0,0,0,5));
        this.setSpacing(1);
        VBox.setMargin(lastMess,new Insets(0,0,0,10));
    }
}
