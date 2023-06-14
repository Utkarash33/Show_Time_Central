package com.masai.event;

import java.util.Map;

import com.masai.exceptions.EventException;

public interface EventServices {
	public String addEvent(Event pro, Map<Integer, Event> products);

	public void viewAllEvent(Map<Integer, Event> products) throws EventException;

	public void deleteEvent(int id, Map<Integer, Event> products , String org) throws EventException;

	String updateEvent(int id, Event prod, Map<Integer, Event> Events, String name) throws EventException;

}
