package com.chat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROOMS")
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

	private String group_name;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", group_name=" + group_name + "]";
	}

	
}
