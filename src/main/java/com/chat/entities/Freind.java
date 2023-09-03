package com.chat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Freind {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int sender;
    int reciver;
    String status;
    
    
	public Freind() {
		super();
	}

	public Freind(int id, int sender, int reciver, String status) {
		super();
		this.id = id;
		this.sender = sender;
		this.reciver = reciver;
		this.status = status;
	}
	//getters and setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSender() {
		return sender;
	}
	public void setSender(int sender) {
		this.sender = sender;
	}
	public int getReciver() {
		return reciver;
	}
	public void setReciver(int reciver) {
		this.reciver = reciver;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    //to String method
	@Override
	public String toString() {
		return "Freind [id=" + id + ", sender=" + sender + ", reciver=" + reciver + ", status=" + status + "]";
	}


    
}
