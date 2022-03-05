package com.example.hackathon.server.dbmappings;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @GenericGenerator(name = "generator", strategy = "increment")
    @Id
    @GeneratedValue(generator = "generator")
    private int id;
    @Column
    private String userName;
    @Column
    private String content;

    public Message() {}

    public Message(String user, String content) {
        this.userName = user;
        this.content = content;
    }

    public String getUser() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }
}