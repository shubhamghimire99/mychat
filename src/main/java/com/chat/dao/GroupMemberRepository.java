package com.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.entities.GroupMembers;

public interface GroupMemberRepository extends JpaRepository<GroupMembers, Integer>{

}
