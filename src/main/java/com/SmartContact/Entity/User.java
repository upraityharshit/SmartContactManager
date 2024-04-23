package com.SmartContact.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int uid;
	
	@NotBlank(message = "Name should not be blank")
	@Size(min = 3, max= 30, message = "Name must be between 2 to 30 characters")
	private String name;
	@Column(unique = true)
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message="Invalid Email")
	private String email;
	@NotBlank(message = "Passoword cannot not be blank")
	private String password;
	private String role;
	private String image;
	@AssertTrue(message = "Must agreed terms and condition")
	private boolean enabled;
	@Column(length = 10000)
	private String about;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cid")
	private List<Contact> contact= new ArrayList<Contact>();
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(int uid, String name, String email, String password, String role, String image, boolean enabled,
			String about) {
		super();
		this.uid = uid;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.image = image;
		this.enabled = enabled;
		this.about = about;
	}

	public List<Contact> getContact() {
		return contact;
	}

	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", image=" + image + ", enabled=" + enabled + ", about=" + about + "]";
	}
	
	
	
}
