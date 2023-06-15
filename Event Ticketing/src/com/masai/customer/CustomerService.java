package com.masai.customer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.masai.booking.Booking;
import com.masai.booking.BookingServiceImpl;
import com.masai.event.Event;
import com.masai.exceptions.AuthenticationException;
import com.masai.exceptions.BookingException;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.EventException;
import com.masai.exceptions.InvalidDetailsException;

public interface CustomerService {

	
	public boolean login(String email,String password, Map<String, Customer> customers) throws AuthenticationException, InvalidDetailsException;

	public void signUp(Customer cus, Map<String, Customer> customer) throws DuplicateDataException, FileNotFoundException, IOException;

	public boolean addMoneyToWallet(double money, String email, Map<String, Customer> customers) throws FileNotFoundException, IOException;

	public double viewWalletBalance(String email, Map<String, Customer> customers);

	public Customer viewCustomerDetails(String email, Map<String, Customer> customers);

	public void bookEvent(int id, int qty, String email, Map<Integer, Event> events, Map<String, Customer> customer,
			List<Booking> boo2) throws InvalidDetailsException, EventException, FileNotFoundException, IOException;
	
	public  Map<String, Customer>  viewAllCustomers( Map<String, Customer> customer) throws BookingException;
}
