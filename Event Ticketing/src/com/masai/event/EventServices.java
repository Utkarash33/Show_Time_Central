package com.masai.event;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.masai.exceptions.EventException;

public interface EventServices {
	public String addEvent(Event pro, Map<Integer, Event> products, String nameOfOrganizer) throws FileNotFoundException, IOException;

	public void viewAllEvent(Map<Integer, Event> products) throws EventException;

	public void deleteEvent(int id, Map<Integer, Event> products , String org) throws EventException, FileNotFoundException, IOException;

	String updateEvent(int id, Event prod, Map<Integer, Event> Events, String name) throws EventException, FileNotFoundException, IOException;

	
	public void viewAllEventForOrganizer(Map<Integer, Event> events) throws EventException ;
	
	public void approveEvent(int id,Map<Integer , Event> events) throws EventException, IOException;
}
