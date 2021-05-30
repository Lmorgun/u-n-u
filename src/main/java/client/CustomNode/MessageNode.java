package client.CustomNode;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MessageNode extends HBox {
    private final static String msgLabelStyle = "message_text";
    private final static String messageAndDateLeft = "recepientSMS";
    private final static String messageAndDateRight = "senderSMS";

    private final static Insets messageAndDateBoxPadding = new Insets(5, 5, 5, 5);
    private final static Insets messageAndDateBoxMargin = new Insets(5, 5, 5, 5);

    private final static double spacingBetweenMessages = 2;
    private final static double messagesMaxWidth = 250;

    public MessageNode(String message, String date, boolean isRight) {
        this.setFillHeight(false);

        VBox vbox = new VBox();
        vbox.setSpacing(spacingBetweenMessages);
        vbox.setMaxWidth(messagesMaxWidth);
        HBox.setMargin(vbox, messageAndDateBoxMargin);

        vbox.setPadding(messageAndDateBoxPadding);
        Label msgLabel = new Label(message);
        msgLabel.getStyleClass().add(msgLabelStyle);
        msgLabel.setWrapText(true);
        Label dateLabel = new Label(date);
        dateLabel.setFont(Font.font("system",10));
        dateLabel.getStyleClass().add("textColor");
        msgLabel.getStyleClass().add("textColor");
        HBox hBoxText = new HBox(msgLabel);
        HBox hBoxDate = new HBox(dateLabel);
        if (isRight) {
            vbox.getStyleClass().add(messageAndDateRight);
            hBoxText.setAlignment(Pos.CENTER_RIGHT);
            hBoxDate.setAlignment(Pos.CENTER_RIGHT);
            this.setAlignment(Pos.CENTER_RIGHT);
        } else {
            vbox.getStyleClass().add(messageAndDateLeft);
            hBoxText.setAlignment(Pos.CENTER_LEFT);
            hBoxDate.setAlignment(Pos.CENTER_LEFT);
            this.setAlignment(Pos.CENTER_LEFT);
        }
        vbox.getChildren().addAll(hBoxText, hBoxDate);
        this.getChildren().add(vbox);
    }
}
