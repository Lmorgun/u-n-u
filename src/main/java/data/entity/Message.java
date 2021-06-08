package data.entity;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private User sender;
    private User receiver;
    private String text;
    private LocalDateTime date;
    private boolean status_unread;

    public int getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public Message(int id, User sender, User receiver, String text, LocalDateTime date, boolean status_unread) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = date;
        this.status_unread = status_unread;
    }

    public Message(User sender, User receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = LocalDateTime.now();
        this.status_unread = false;
    }
    public User getOtherUser(User user){
        return user.getPhone_number().equals(receiver.getPhone_number()) ? sender : receiver;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStatus_unread(boolean status_unread) {
        this.status_unread = status_unread;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isStatus_unread() {
        return status_unread;
    }
}
