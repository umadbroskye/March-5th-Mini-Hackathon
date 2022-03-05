package com.example.hackathon.server.objects;

public class Message {
    public int messageId;
    public String message;
    public String user;
    public String[] usersOnline;

    public Message(int id, String message, String user, String[] usersOnline) {
        this.messageId = id;
        this.message = message;
        this.user = user;
        this.usersOnline = usersOnline;
    }
}
