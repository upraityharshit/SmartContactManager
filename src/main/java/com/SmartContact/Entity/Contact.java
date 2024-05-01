package com.SmartContact.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int cid;
	
	@NotBlank(message = "Name should not be blank")
	@Size(min = 3, max= 30, message = "Name must be between 2 to 30 characters")
	private String name;
	@NotBlank(message = "NickName should not be blank")
	private String nickname;
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message="Invalid Email")
	private String email;
	@NotBlank(message = "Phone should not be blank")
	private String phone;
	@NotBlank(message = "Work should not be blank")
	private String work;
	private String image;
	@Column(length = 5000)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	@JsonIgnore
	private User user;
	
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Contact(int cid, String name, String nickname, String email, String phone, String work, String image,
			String description) {
		super();
		this.cid = cid;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.phone = phone;
		this.work = work;
		this.image = image;
		this.description = description;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Contact [cid=" + cid + ", name=" + name + ", nickname=" + nickname + ", email=" + email + ", phone="
				+ phone + ", work=" + work + ", image=" + image + ", description=" + description + "]";
	}
	
	
	
	
}
