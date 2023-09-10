package com.chat.entities;

import jakarta.persistence.Column;
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

	// unique = true for group_name and not null
	@Column(unique = true, nullable = false)
	private String group_name;

	// group admin
	private int admin;

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

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
		return "Room [id=" + id + ", group_name=" + group_name + ", admin=" + admin +"]";
	}

	
}
