package com.SmartContact.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SmartContact.Entity.Contact;
import com.SmartContact.Entity.User;

public interface ContactRepository extends JpaRepository<Contact, Integer>{
	
	@Query("from Contact as c where c.user.id=:userid")
	public Page<Contact> findContactByUser(@Param("userid") int userId, Pageable pageable); 

	public List<Contact> findByNameContainingAndUser(String name, User user);

}
