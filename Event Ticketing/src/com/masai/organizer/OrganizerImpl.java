package com.masai.organizer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.masai.customer.Customer;
import com.masai.exceptions.BookingException;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;

public class OrganizerImpl {

	
	
	public void signUp(EventOrganizer cus, Map<String, EventOrganizer> org) throws DuplicateDataException, FileNotFoundException, IOException {

		if (org.containsKey(cus.getUsername())) {
			throw new DuplicateDataException("Customer already exists , please login");
		} else {

			org.put(cus.getUsername(), cus);
			ObjectOutputStream coos = new ObjectOutputStream(new FileOutputStream("Organizer.ser"));
			coos.writeObject(org);


		}

	}
	public boolean login(String username,String password, Map<String, EventOrganizer> org) throws InvalidDetailsException {

		if (org.containsKey(username) ) {
			
			if(org.get(username).getStatus().equals("pending"))
			{
				throw new InvalidDetailsException("Organizer is not approved yet!");
			}
			else
			{
			if(org.get(username).getPassword().equals(password)) {
				return true;
			}
			else {
				throw new InvalidDetailsException("Invalid Credentials");
			}
			}
			
		} else {
			throw new InvalidDetailsException("you have not sign up yet, please signup");
		}

	}
	
	
	public Map<String, EventOrganizer> viewAllOrganizer(Map<String, EventOrganizer> orgs) throws BookingException {
	
		
		if(orgs != null && orgs.size()>0) {
			return orgs;
		}
		else {
			throw new BookingException("no Organizer yet");
		}
	}

	
	public void delete(String username, String pass, Map<String, Customer> customer) throws InvalidDetailsException {
if (customer.containsKey(username) ) {
			
			if(customer.get(username).getPassword().equals(pass)) {
				customer.remove(username);
			}
			else {
				throw new InvalidDetailsException("Invalid Credentials");
			}
			
		} else {
			throw new InvalidDetailsException("you have not account to delete");
		}
	}


}
