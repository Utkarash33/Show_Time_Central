package com.masai.booking;

import java.util.List;

import com.masai.exceptions.BookingException;

public interface BookingServices {
public List<Booking> viewCustomerBookings(String email, List<Booking> transactions)throws BookingException;
	
	public List<Booking> viewAllBookings(List<Booking> transactions) throws BookingException;
}
