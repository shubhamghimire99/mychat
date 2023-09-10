package com.chat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "GROUP_MEMBERS")
public class GroupMembers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    private boolean isGroup;

    // Constructors, other fields, and methods

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }
    
    // Other methods as needed
	@Override
	public String toString() {
		return "GroupMembers [user=" + user + ", room=" + room + ", isGroup = " + isGroup +"]";
	}
}

