package com.masai.booking;

import java.util.ArrayList;
import java.util.List;

import com.masai.exceptions.BookingException;

public class BookingServiceImpl implements BookingServices{

	@Override
	public List<Booking> viewCustomerBookings(String email, List<Booking> Bookings)	throws BookingException {
		List<Booking> myBookings = new ArrayList<>();

		boolean flag = false;
		for (Booking tr : Bookings) {
			if (tr.getEmail().equals(email)) {

				myBookings.add(tr);

				flag = true;
			}
		}
		if (!flag) {
			throw new BookingException("you have not Book any event yet");
		}

		return myBookings;
	}

	@Override
	public List<Booking> viewAllBookings(List<Booking> Bookings) throws BookingException {
		if(Bookings != null && Bookings.size()>0) {
			return Bookings;
		}
		else {
			throw new BookingException("no Bookings yet");
		}
		
	}
}

