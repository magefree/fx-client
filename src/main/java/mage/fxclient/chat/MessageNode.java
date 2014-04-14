package mage.fxclient.chat;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import mage.view.ChatMessage;

/**
 *
 * @author North
 */
public class MessageNode extends VBox {

    private static final Color OPPONENT_COLOR = Color.rgb(238, 230, 133);
    private static final Color MY_COLOR = Color.rgb(0, 230, 64);
    private static final Color TIMESTAMP_COLOR = Color.rgb(255, 255, 0);
    private static final Color MESSAGE_COLOR = Color.WHITE;
    private static final Color USER_INFO_COLOR = Color.YELLOW;
    private static final Color STATUS_COLOR = Color.CYAN;

    public MessageNode(ChatMessage chatMessage, String userName) {
        HBox nameAndTime = new HBox();
        nameAndTime.setSpacing(10d);

        if (chatMessage.getTime() != null) {
            Text timeLabel = new Text();
            timeLabel.setFill(TIMESTAMP_COLOR);
            timeLabel.setText(chatMessage.getTime());

            nameAndTime.getChildren().add(timeLabel);
        }

        Color userColor;
        Color textColor;
        String messageUserName = chatMessage.getUsername();

        switch (chatMessage.getMessageType()) {
            case STATUS: // a message to all chat user
                textColor = STATUS_COLOR;
                userColor = STATUS_COLOR;
                break;
            case USER_INFO: // a personal message
                textColor = USER_INFO_COLOR;
                userColor = USER_INFO_COLOR;
                break;
            default:
                userColor = userName.equals(messageUserName) ? MY_COLOR : OPPONENT_COLOR;
                textColor = MESSAGE_COLOR;
        }
        switch (chatMessage.getColor()) {
            case ORANGE:
                textColor = Color.ORANGE;
                break;
            case YELLOW:
                textColor = Color.YELLOW;
                break;
        }
        if (messageUserName != null && !messageUserName.isEmpty()) {
            Text nameLabel = new Text();
            nameLabel.setText(messageUserName);
            nameLabel.setFill(userColor);
            nameAndTime.getChildren().add(nameLabel);
        }

        Text message = new Text();
        message.setText(chatMessage.getMessage());
        message.setFill(textColor);

        this.getChildren().addAll(nameAndTime, message);
        this.setStyle("-fx-background-color: #555;");
    }
}
