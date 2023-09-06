package com.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.chat.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@Param("email")String email);

	// to get user by id
	@Query("select u from User u where u.id = :id")
	public User findById(int id);


}
