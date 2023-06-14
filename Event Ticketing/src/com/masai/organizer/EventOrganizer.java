package com.masai.organizer;

import java.util.ArrayList;
import java.util.List;

import com.masai.booking.Booking;
import com.masai.event.Event;

public class EventOrganizer {

	
	private String username;
	private String password;
	private List<Event> events;
	private List<Booking> bookingHistory;
	public EventOrganizer(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.events = new ArrayList<>();
		this.bookingHistory = new ArrayList<>();
	}
	
	
	
	
}
