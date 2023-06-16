package com.masai.event;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.masai.FileExists;
import com.masai.exceptions.EventException;
import com.masai.organizer.EventOrganizer;

public class EventServicesImpl implements EventServices{

	@Override
	public String addEvent(Event prod, Map<Integer, Event> Events, String nameOfOrganizer) throws FileNotFoundException, IOException {
		
//as our id's are always unique thats why directly putting into Events
		
		Events.put(prod.getId(), prod);
		ObjectOutputStream evn = new ObjectOutputStream(new FileOutputStream("Event.ser"));
		evn.writeObject(Events);	
		return "Event added successfully";

	}

	@Override
	public void viewAllEvent(Map<Integer, Event> Events) throws EventException {
		
		if (Events != null && Events.size() > 0) {
			boolean flag =false;
			for (Map.Entry<Integer, Event> me : Events.entrySet()) {
				   Event a =me.getValue();
				   
				   if(a.getStatus())
				   {   
					System.out.println(me.getValue());
					flag= true;
				   }
				   
			}
			if(!flag)
			{
				throw new EventException("Event List Is empty");
			}

		} else {
			throw new EventException("Event List is empty");
		}
	}
	@Override
	public void deleteEvent(int id, Map<Integer, Event> Events , String org) throws EventException, FileNotFoundException, IOException {

		// System.out.println(Events);
		if (Events != null && Events.size() > 0) {

			if (Events.containsKey(id)) {
				
                 Event event = Events.get(id);
				
				if(event.getOrgainzer().equals(org) || org.equals("admin"))
				{
					Events.remove(id);
					ObjectOutputStream evn = new ObjectOutputStream(new FileOutputStream("Event.ser"));
					evn.writeObject(Events);
					
				
					System.out.println("Event deleted successfully");
				}else
				{
					throw new EventException("Not the right Organizer");
				}
			} else {
				throw new EventException("Event not found");
			}

		} else {
			throw new EventException("Event list is empty");
		}

	}

	@Override
	public String updateEvent(int id, Event prod, Map<Integer, Event> Events , String name) throws EventException, FileNotFoundException, IOException {

		if (Events != null && Events.size() > 0) {

			if (Events.containsKey(id)) {
				
				Event event = Events.get(id);
				
				if(event.getOrgainzer().equals(name))
				{
					Events.put(id, prod);
					ObjectOutputStream evn = new ObjectOutputStream(new FileOutputStream("Event.ser"));
					evn.writeObject(Events);
					
					return "Event has successfully updated";
				}else
				{
					throw new EventException("Not the right Organizer");
				}
				
				
			} else {
				throw new EventException("Event not found");
			}

		} else {
			throw new EventException("Event list is empty");
		}

	}

	public void viewAllEventForOrganizer(Map<Integer, Event> events) throws EventException {
		if (events != null && events.size() > 0) {
			for (Map.Entry<Integer, Event> me : events.entrySet()) {
				Event a =me.getValue();
				String str = "Pending";
				if(a.getStatus())
				{
					str = "Approved";
				}
				
					System.out.println("Status " + str+" "+me.getValue());
			}

		} else {
			throw new EventException("Event List is empty");
		}
	}

	
	public void viewAllEventForAdmin(Map<Integer, Event> events) throws EventException {
		if (events != null && events.size() > 0) {
			for (Map.Entry<Integer, Event> me : events.entrySet()) {
				Event a =me.getValue();
				String str = "Pending";
				String b = a.getOrgainzer();
				if(a.getStatus())
				{
					str = "Approved";
				}
				
					System.out.println("Organier :" +" "+b+"->"+" "+"Status " + str+" "+me.getValue());
			}

		} else {
			throw new EventException("Event List is empty");
		}
	}
	
	
	@Override
	public void approveEvent(int id, Map<Integer, Event> events) throws EventException, IOException {
		
		if (events != null && events.size() > 0) {

			if (events.containsKey(id)) {
				
                 Event ev = events.get(id);
			    ev.setStatus(true);
			    ObjectOutputStream evn = new ObjectOutputStream(new FileOutputStream("Event.ser"));
				evn.writeObject(events);
			
			    System.out.println("This event has been approved");
			    System.out.println(ev);
				
			} else {
				throw new EventException("Event not found");
			}

		} else {
			throw new EventException("Event list is empty");
		}
		}
	}
