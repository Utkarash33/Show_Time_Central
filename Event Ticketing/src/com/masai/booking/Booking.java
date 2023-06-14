package com.masai.booking;

import java.io.Serializable;
import java.time.LocalDate;

public class Booking implements Serializable {

	private String username;
	private String email;
	private int EventId;
	private String EventName;
	private double price;
	private double total;
	private int noOfSeats;
	private LocalDate dt;
	
	public Booking() {
		
	}
	public Booking(String username, String email, int productId, String productName, int qty, double price, double total,
			LocalDate dt) {
		super();
		this.username = username;
		this.email = email;
		this.EventId = productId;
		this.EventName = productName;
		this.noOfSeats = qty;
		this.price = price;
		this.total = total;
		this.dt = dt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getEventId() {
		return EventId;
	}

	public void setEventId(int eventId) {
		EventId = eventId;
	}

	public String getEventName() {
		return EventName;
	}

	public void setEventName(String eventName) {
		EventName = eventName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDate getDt() {
		return dt;
	}

	public void setDt(LocalDate dt) {
		this.dt = dt;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
	@Override
	public String toString() {
		return "Booking [username=" + username + ", email=" + email + ", EventId=" + EventId + ", EventName="
				+ EventName + ", price=" + price + ", total=" + total + ", dt=" + dt + "]";
	}

	
	
	
	
}
