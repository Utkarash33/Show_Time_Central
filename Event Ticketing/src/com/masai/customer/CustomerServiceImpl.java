package com.masai.customer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
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

public class CustomerServiceImpl  implements CustomerService
{

	@Override
	public void signUp(Customer cus, Map<String, Customer> customers) throws DuplicateDataException, FileNotFoundException, IOException {

		if (customers.containsKey(cus.getUserName())) {
			throw new DuplicateDataException("Customer already exists , please login");
		} else {

			customers.put(cus.getUserName(), cus);
			ObjectOutputStream coos = new ObjectOutputStream(new FileOutputStream("Customer.ser"));
			coos.writeObject(customers);


		}

	}

	@Override
	public boolean login(String username,String password, Map<String, Customer> customers) throws InvalidDetailsException {

		if (customers.containsKey(username) ) {
			
			if(customers.get(username).getPassword().equals(password)) {
				return true;
			}
			else {
				throw new InvalidDetailsException("Invalid Credentials");
			}
			
		} else {
			throw new InvalidDetailsException("you have not sign up yet, please signup");
		}

	}

	@Override
	public boolean addMoneyToWallet(double amount, String email, Map<String, Customer> customers) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		Customer cus = customers.get(email);

		cus.setBalance(cus.getBalance() + amount);

		customers.put(email, cus);
		ObjectOutputStream coos = new ObjectOutputStream(new FileOutputStream("Customer.ser"));
		coos.writeObject(customers);

		return true;
	}

	@Override
	public double viewWalletBalance(String email, Map<String, Customer> customers) {
		
		Customer cus = customers.get(email);

		return cus.getBalance();
	}

	@Override
	public Customer viewCustomerDetails(String email, Map<String, Customer> customers) {

		if (customers.containsKey(email)) {

			return customers.get(email);

		}

		return null;
	}

	public void bookEvent(int id, int qty, String email, Map<Integer, Event> events, Map<String, Customer> customer,
			List<Booking> boo) throws InvalidDetailsException, EventException, FileNotFoundException, IOException {

			if (events.size() == 0)
				throw new EventException("Product list is empty");

			if (events.containsKey(id)) {

				Event env = events.get(id);
               
				if (env.getTotalSeats() >= qty) {

					Customer cus = customer.get(email);

					 List<Booking>  book=  cus.getBookingHistory();
					 
					double buyingPrice = qty * env.getTicketPrice();

					if (cus.getBalance() >= buyingPrice) {
						cus.setBalance(cus.getBalance() - buyingPrice);

						env.setTotalSeats(env.getTotalSeats() - qty);

						events.put(id, env);
                        
						Booking br = new Booking(cus.getUserName(), email, id,env.getEventName(), qty, env.getTicketPrice(),
								env.getTicketPrice() * qty, LocalDate.now());

						book.add(br);
						cus.setBookingHistory(book);
						boo.add(br);
						ObjectOutputStream boos = new ObjectOutputStream(new FileOutputStream("Transactions.ser"));
						boos.writeObject(boo);
						

					} else {
						throw new InvalidDetailsException("wallet balance is not sufficient");
					}

				} else {
					throw new InvalidDetailsException("product quantity is not suffiecient");
				}

			} else {
				throw new InvalidDetailsException("product not available with id: " + id);
			}

		
		

		
	}

	@Override
	public Map<String, Customer> viewAllCustomers(Map<String, Customer> customer) throws BookingException {
	
		
		if(customer != null && customer.size()>0) {
			return customer;
		}
		else {
			throw new BookingException("no Customers yet");
		}
	}


}
