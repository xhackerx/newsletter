package com.classes;

public class User {
	
	public int id;
	public String name;
	public String email;
	public boolean receiveNewsLetter;
	public boolean active;
	public String dateStartSubscription;
	public String dateEndSubscription;
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
	public boolean isReceiveNewsLetter() {
		return receiveNewsLetter;
	}
	public void setReceiveNewsLetter(boolean receiveNewsLetter) {
		this.receiveNewsLetter = receiveNewsLetter;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDateStartSubscription() {
		return dateStartSubscription;
	}
	public void setDateStartSubscription(String dateStartSubscription) {
		this.dateStartSubscription = dateStartSubscription;
	}
	public String getDateEndSubscription() {
		return dateEndSubscription;
	}
	public void setDateEndSubscription(String dateEndSubscription) {
		this.dateEndSubscription = dateEndSubscription;
	}
}
