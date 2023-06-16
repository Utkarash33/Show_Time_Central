package com.masai.organizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.masai.booking.Booking;
import com.masai.event.Event;

public class EventOrganizer implements Serializable {

	private String name;
	private String username;
	private String password;
	private String status;
	private List<Event> events;
	private List<Booking> bookingHistory;
	public EventOrganizer( String name,String username, String password) {
		super();
		this.name = name;
		
		this.username = username;
		this.password = password;
		this.status = "pending";
		this.events = new ArrayList<>();
		this.bookingHistory = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public List<Booking> getBookingHistory() {
		return bookingHistory;
	}
	public void setBookingHistory(List<Booking> bookingHistory) {
		this.bookingHistory = bookingHistory;
	}
	@Override
	public String toString() {
		return "EventOrganizer [username=" + username + ", password=" + password + ", status=" + status + ", events="
				+ events + ", bookingHistory=" + bookingHistory + "]";
	}
	
	
	
	
}
