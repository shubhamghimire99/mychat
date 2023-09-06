package com.chat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "GROUP_MEMBERS")
public class GroupMembers {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
    
    private int user_id;
  
    private int room_id;
    
    

	public int getUser_id() {
		return user_id;
	}



	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}



	public int getRoom_id() {
		return room_id;
	}



	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}



	@Override
	public String toString() {
		return "GroupMembers [user_id=" + user_id + ", room_id=" + room_id + "]";
	}
	
	

}
