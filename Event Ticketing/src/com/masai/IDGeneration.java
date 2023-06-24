package com.masai;

import java.util.Map;

import com.masai.event.Event;

public class IDGeneration {

	public static int generateId() {
		Map<Integer, Event> event = FileExists.eventFile();
		
		
		
		int id = (int) (Math.random() * 100000);
		boolean flag =true;
		for (Map.Entry<Integer, Event> me : event.entrySet()) {
			   Event a =me.getValue();
			   
			   if(a.getId()==id)
			   {   
				flag= false;
			   }   
		}
		if(flag)
		{
			return  (int) (Math.random() * 100000);
		}
		else
		{
			return id;
		}
	}

}
