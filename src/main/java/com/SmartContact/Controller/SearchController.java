package com.SmartContact.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.SmartContact.Entity.Contact;
import com.SmartContact.Entity.User;
import com.SmartContact.repository.ContactRepository;
import com.SmartContact.repository.UserRepository;

@RestController
public class SearchController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/search/{name}")
	public ResponseEntity<?> searchContact(@PathVariable("name") String name, Principal principal){
		
		User user = this.userRepository.getUserByUserName(principal.getName());
		
		List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(name, user); 
		
		return ResponseEntity.ok(contacts);
	
	}
}
