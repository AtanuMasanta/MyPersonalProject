package com.contact.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
public class User 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@jakarta.validation.constraints.NotBlank
	@Size(min=2,max=20,message="in between 2 to 12")
	@NotBlank(message="name can't be blank")
	private String name;
	@Column(unique =true)
	private String email;
	private String password;
	private String role;
	private boolean enable;
	private String imageUrl;
	@Column(length=500)
	private String about;
	@OneToMany(cascade = CascadeType.ALL,mappedBy ="user" )
	private List<Contact>contacts=new ArrayList<Contact>();
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(int id, String name, String email, String password, String role, boolean enable, String imageUrl,
			String about, List<Contact> contacts) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enable = enable;
		this.imageUrl = imageUrl;
		this.about = about;
		this.contacts = contacts;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}


	public List<Contact> getContacts() {
		return contacts;
	}


	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", enable=" + enable + ", imageUrl=" + imageUrl + ", about=" + about + ", contacts=" + contacts + "]";
	}
	
	
	
	
	
	

}
