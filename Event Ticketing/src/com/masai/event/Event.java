package com.masai.event;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.masai.organizer.EventOrganizer;

public class Event implements Serializable {
     
	private int id;
	private String eventName;
	private String venue;
	private LocalDateTime dateTime;
	private double ticketPrice;
	private int totalSeats;
	private EventType eventType;
	private boolean status;
	private String orgainzer;
//	private EventOrganizer eventOrgainzer;
	public Event(int id,String eventName, String venue, LocalDateTime dateTime, double ticketPrice, int totalSeats,
			EventType eventType, String org) {
		super();
		this.id = id;
		this.eventName = eventName;
		this.venue = venue;
		this.dateTime = dateTime;
		this.ticketPrice = ticketPrice;
		this.totalSeats = totalSeats;
		this.eventType = eventType;
		this.status = false;
		this.orgainzer = org;
//		this.eventOrgainzer = eventOrgainzer;
//		this.rating = new ArrayList<>();
//		this.reviews = new ArrayList<>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	public String getOrgainzer() {
		return orgainzer;
	}
	public void setOrgainzer(String orgainzer) {
		this.orgainzer = orgainzer;
	}
	@Override
	
public int hashCode() {
		return Objects.hash(dateTime, eventName, eventType, id, ticketPrice, totalSeats, venue);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(dateTime, other.dateTime) && Objects.equals(eventName, other.eventName)
				&& eventType == other.eventType && id == other.id
				&& Double.doubleToLongBits(ticketPrice) == Double.doubleToLongBits(other.ticketPrice)
				&& totalSeats == other.totalSeats && Objects.equals(venue, other.venue);
	}
	@Override
	public String toString() {
		return "Event [id=" + id + ", eventName=" + eventName + ", venue=" + venue + ", dateTime=" + dateTime
				+ ", ticketPrice=" + ticketPrice + ", totalSeats=" + totalSeats + ", eventType=" + eventType + "]";
	}

}
