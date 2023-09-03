package com.chat.entities;

import jakarta.persistence.*;

@Entity
public class Friend {
    private Integer receiver;

    private Integer sender;
    private String status;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Friend() {
    }

    public Friend(Integer receiver, Integer sender, String status, Integer id) {
        this.receiver = receiver;
        this.sender = sender;
        this.status = status;
        this.id = id;
    }

    public Friend(Integer receiver, Integer sender, String status) {
        this.receiver = receiver;
        this.sender = sender;
        this.status = status;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
