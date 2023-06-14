package com.masai.event;

import java.util.Map;

import com.masai.exceptions.EventException;

public class EventServicesImpl implements EventServices{

	@Override
	public String addEvent(Event prod, Map<Integer, Event> Events) {
		
//as our id's are always unique thats why directly putting into Events
		
		Events.put(prod.getId(), prod);

		return "Event added successfully";

	}

	@Override
	public void viewAllEvent(Map<Integer, Event> Events) throws EventException {
		
		if (Events != null && Events.size() > 0) {
			for (Map.Entry<Integer, Event> me : Events.entrySet()) {
				System.out.println(me.getValue());
			}

		} else {
			throw new EventException("Event List is empty");
		}
	}
	@Override
	public void deleteEvent(int id, Map<Integer, Event> Events , String org) throws EventException {

		// System.out.println(Events);
		if (Events != null && Events.size() > 0) {

			if (Events.containsKey(id)) {
				
                 Event event = Events.get(id);
				
				if(event.getOrgainzer().equals(org))
				{
					Events.remove(id);
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
	public String updateEvent(int id, Event prod, Map<Integer, Event> Events , String name) throws EventException {

		if (Events != null && Events.size() > 0) {

			if (Events.containsKey(id)) {
				
				Event event = Events.get(id);
				
				if(event.getOrgainzer().equals(name))
				{
					Events.put(id, prod);
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
}
