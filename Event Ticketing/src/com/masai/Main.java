package com.masai;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import com.masai.admin.Administrator;
import com.masai.booking.Booking;
import com.masai.booking.BookingServiceImpl;
import com.masai.booking.BookingServices;
import com.masai.customer.Customer;
import com.masai.customer.CustomerService;
import com.masai.customer.CustomerServiceImpl;
import com.masai.event.Event;
import com.masai.event.EventServices;
import com.masai.event.EventServicesImpl;
import com.masai.event.EventType;
import com.masai.exceptions.AuthenticationException;
import com.masai.exceptions.BookingException;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.EventException;
import com.masai.exceptions.InvalidChoiceException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.organizer.EventOrganizer;
import com.masai.organizer.OrganizerImpl;


public class Main {

	
	private static Scanner scanner = new Scanner(System.in);
	
	
	static EventServicesImpl  eventService = new EventServicesImpl();
	static BookingServiceImpl boo = new BookingServiceImpl();
	static CustomerService cusService = new CustomerServiceImpl();
	
	public static void main(String[] args)
	{
		//Map and list of all the files////////////;
		Map<String, Customer> customer = FileExists.customerFile();
		Map<Integer, Event> event = FileExists.eventFile();
		List<Booking> bookings = FileExists.bookingFile();
		try {
			
			boolean flag = true;
			
			///To make the first choice
			while(flag)
			{
				System.out.println("Welcome to ShowTime Central");
				System.out.println("1. Administrator");
				System.out.println("2. Event Organizer");
				System.out.println("3. Customer");
				System.out.println("4. Quit");
				System.out.println("Enter your choice: ");
				
				
				int choice = scanner.nextInt();
				
				scanner.nextLine();
				
				switch(choice)
				{
				case 1:
					 
					//admin
					handleAdminstrator(scanner,customer,event,bookings);
					break;
				
				case 2:
					 //Event Organizer
					
					try {
						handleEventOrgainzer(scanner,customer,event,bookings);
					} catch (AuthenticationException | InvalidDetailsException e) {
						e.printStackTrace();
					}
					break;
				
				case 3:
						//Customer 
						
					try {
						handleCustomer(scanner,customer,event,bookings);
					} catch (InvalidChoiceException | DuplicateDataException e) {
						e.printStackTrace();
					} catch (AuthenticationException e) {
						
						e.printStackTrace();
					} catch (InvalidDetailsException e) {
						
						e.printStackTrace();
					}
					break;
				
				case 4:
					 
					
					System.out.println("Thank you for using ShowTime Centeral.");
					flag = false;
					break;
				
				default:
					 
					
					System.out.println("Invalid Choice. Please try again.");
					break;
				}
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	////Method to handle the functionality related to Customer
	
	
	private static void handleCustomer(Scanner sc, Map<String, Customer> customer , Map<Integer , Event> events , List<Booking> book) throws InvalidChoiceException, DuplicateDataException, AuthenticationException, InvalidDetailsException {

		
		boolean flag = true;
		while(flag)
		{
			
			///make choice 
			System.out.println("1. SignUp");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.println("4. Delete Account");
			System.out.println("Enter your choice: ");
			
			
			int choice = scanner.nextInt();
			
			scanner.nextLine();
			
			switch(choice)
			{
			case 1:
				 
				try {
					//customer signup
					customerSignUp(sc,customer);
				} catch (DuplicateDataException | IOException e) {
					e.printStackTrace();
				}
				break;
				
			
			case 2:
				//login
				 
				customerFunctionality(sc, events , customer,book);
				break;
			case 3:
				 //exit to previous menu
				
				flag = false;
				return;
			
			case 4:
				//delete account
				deleteAccount(sc,customer);
				break;
			default:
				 
				//invalid choice
				System.out.println("invalid Choice. Please try again");
				break;
			}
		}
      
		
	}
	

	//Sign in part.//////////
	
	private static void customerSignUp(Scanner sc, Map<String, Customer> customer) throws DuplicateDataException, FileNotFoundException, IOException {
		System.out.println("please enter the following details to Signup");
		System.out.println("please enter the first name");
		String firstName = sc.next();
		System.out.println("Enter the last name");
		String lastName = sc.next();
		System.out.println("enter the address");
		String address = sc.next();
		System.out.println("Enter the Mobile Number");
		String mobile = sc.next();
		System.out.println("Enter the email");
		String userName = sc.next();
		System.out.println("Enter moeny to Wallet");
		double money = sc.nextDouble();
		System.out.println("Enter the password");
		String password = sc.next();
		
		
		Customer cus = new Customer(firstName, lastName,address, mobile, userName, password, money);

		CustomerService cusService = new CustomerServiceImpl();
		
		//Pass the customer object and the customer map to verify if the customer user name is unique of not 
		cusService.signUp(cus, customer);
		System.out.println("customer has Succefully sign up");

	}

	
//	Login part/////////////////
	
	private static void customerFunctionality(Scanner sc, Map<Integer, Event> events, Map<String, Customer> customer, List<Booking> book) throws AuthenticationException, InvalidDetailsException {
	
		

		System.out.println("please enter the following details to login");
		System.out.println("please enter the username");
		String email = sc.next();
		System.out.println("Enter the password");
		String pass = sc.next();
		
		///it's basically the Authentication of the user if any issue occur then the message or the exception has been thrown 
		customerLogin(email,pass, customer, cusService);
		try { 
			int choice = 0;
			do {
				//after login customer menu
				
				System.out.println("Select the option of your choice");
				System.out.println("Press 1 to view all Events");
				System.out.println("Press 2 to Book Ticket for an event");
				System.out.println("Press 3 to add money to a wallet");
				System.out.println("Press 4 view wallet balance");
				System.out.println("Press 5 view my details");
				System.out.println("Press 6 view my bookings");
				System.out.println("Press 7 to logout");
				
				
				///to provide the customer menu////////
				
				choice = sc.nextInt();
				
				switch (choice) {
				case 1:
					//to see all events
					
					customerViewAllEvents(events, eventService);
					break;
				case 2:
					//book event
					
					String result = customerBookEvent(sc, email, events, customer, book, cusService);
					System.out.println(result);
					break;
				case 3:
					//add moeny to wallet 
					
					String moneyAdded = customerAddMoneyToWallet(sc, email, customer, cusService);
					System.out.println(moneyAdded);
					break;
				case 4:
				  /// to check the wallet Balance
					
					double walletBalance = customerViewWalletBalance(email, customer, cusService);
					System.out.println("Wallet balance is: " + walletBalance);
					break;
				case 5:
					// to check customer own detail
					
					customerViewMyDetails(email, customer, cusService);
					break;
				case 6:
					// to check booking detail  
					
					customerViewCustomerBookings(email, book, boo);
					break;
				case 7:
					
					//Logout of the system
					System.out.println("you have successsfully logout");
					choice=10;
					return;
				default:
					
					//invalid choice
					System.out.println("invalid choice");
					break;
				}

			} while (choice <= 6);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	
	///Wallet related classes//////////////
	
	//To add money into the wallet
	
	public static String customerAddMoneyToWallet(Scanner sc, String email, Map<String, Customer> customers,
			CustomerService cusService) throws FileNotFoundException, IOException {
		System.out.println("please enter the amount");
		double money = sc.nextDouble();
		boolean added = cusService.addMoneyToWallet(money, email, customers);

		return "Amount: " + money + " successfully added to your wallet";
	}

	
	//to check wallet balance
	
	public static double customerViewWalletBalance(String email, Map<String, Customer> customers,
			CustomerService cusService) {
		double walletBalance = cusService.viewWalletBalance(email, customers);
		return walletBalance;
	}

	///Bookings ////////////////
	
	// to check or see all the details of the booking made by the particular customer
	
    private static void customerViewCustomerBookings(String email, List<Booking> book, BookingServiceImpl boo2) throws BookingException {
		
		List<Booking> myTransactions = boo.viewCustomerBookings(email, book);

		for (Booking tr : myTransactions) {
			System.out.println(tr);
		}
	}

    
    //to book the an event // to book event for know have to entry to id or the event

	private static String customerBookEvent(Scanner sc, String email, Map<Integer, Event> events,
			Map<String, Customer> customer, List<Booking> boo, CustomerService cusService) throws InvalidDetailsException, EventException, FileNotFoundException, IOException {
		System.out.println("Enter the event id");
		int id = sc.nextInt();
		System.out.println("enter the number of tickets");
		int qty = sc.nextInt();
		cusService.bookEvent(id, qty, email, events, customer, boo);

		return "You have successfully book for this event";
	}

	
	// to view the personal details like name address and wallet balance (can add more fields if required)
	
	public static void customerViewMyDetails(String email, Map<String, Customer> customers,
			CustomerService cusService) {
		Customer cus = cusService.viewCustomerDetails(email, customers);
		System.out.println("name : " + cus.getFirstName()+" "+cus.getLastName());
		System.out.println("address : " + cus.getAddress());
		System.out.println("wallet balance : " + cus.getBalance());
	}

	
	//to check add the events present 
	//basically check the events first then copy the id of the event which you want to book and then use that id to book the event
	
	
	    private static void customerViewAllEvents(Map<Integer, Event> events, EventServicesImpl eventService) throws EventException {
			
		eventService.viewAllEvent(events);
		}


///Authentication part////// related to the login 
	
	private static void customerLogin(String username, String pass, Map<String, Customer> customer,
			CustomerService cusService) throws AuthenticationException, InvalidDetailsException {
		cusService.login(username, pass,customer);
		System.out.println("Customer has successfully login");
		
	}
	
	////delete account //////
	// have to fill the correct user name and password 
	
	private static void deleteAccount(Scanner sc, Map<String, Customer> customer) throws InvalidDetailsException {
		System.out.println("please enter the following details to delete account");
		System.out.println("please enter the username");
		String email = sc.next();
		System.out.println("Enter the password");
		String pass = sc.next();
		System.out.println("Are you sure? y/n");
		String sure = sc.next();
		
		if(sure.equals("y"))
		{
		deleteAccount(email,pass, customer, cusService);
		}
		else
		{
			return;
		}
		
		
	}


	
    // will delete the account and update the customer file	

	private static void deleteAccount(String username, String pass, Map<String, Customer> customer,
			CustomerService cusService2) throws InvalidDetailsException {
		
			cusService.delete(username, pass,customer);
			System.out.println("Account has been deleted.");
	}




	//////Method to handle the functionality related to EventOrganizer
	

	private static void handleEventOrgainzer(Scanner sc , Map<String, Customer> customer, Map<Integer, Event> events , List<Booking> book) throws AuthenticationException, InvalidDetailsException, DuplicateDataException, FileNotFoundException, IOException {
	
		OrganizerImpl orgnizer = new OrganizerImpl();
		Map<String , EventOrganizer> org = FileExists.organizerFile();
		boolean flag = true;
		while(flag)
		{
			//Organizer menu
			System.out.println("1. SignIn");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.println("Enter your choice: ");
			
			
			int choice = scanner.nextInt();
			
			sc.nextLine();
			
			switch(choice)
			{
			case 1:
				 //sign in
				organizerSignUp(sc,org);
				break;
				
			
			case 2:
				 //login
				organizerFunctionality(sc, events ,customer, org,book);
				break;
			case 3:
				//move back to previous menu	 
				flag = false;
				return;
			default:
				
				//invalid choice
				
				System.out.println("invalid Choice. Please try again");
				break;
			}
		}
		
		System.out.println("Hello");
		
			
	
	}
	//Organizer signin need only name unique email and have to wait until approved by the admin

	private static void organizerSignUp(Scanner sc, Map<String, EventOrganizer> org) throws FileNotFoundException, DuplicateDataException, IOException {
		
		OrganizerImpl orgnizer = new OrganizerImpl();
		Map<String , EventOrganizer> bookings = FileExists.organizerFile();
		System.out.println("please enter the following details to Signup");
		System.out.println("please enter the name");
		String Name = sc.nextLine();
		System.out.println("Enter the email");
		String userName = sc.next();
		System.out.println("Enter the password");
		String password = sc.next();
		
		
		EventOrganizer cus = new EventOrganizer(Name, userName, password);
		orgnizer.signUp(cus, org);
		System.out.println("Organizer has Succefully sign up");
	}


//Organizer login after approved can login
	private static void organizerFunctionality(Scanner sc, Map<Integer, Event> events, Map<String, Customer> customer, Map<String, EventOrganizer> org,
			List<Booking> book) throws InvalidDetailsException {
		
		OrganizerImpl orgnizer = new OrganizerImpl();
		Map<String , EventOrganizer> orga= FileExists.organizerFile();
		System.out.println("please enter the following details to login");
		System.out.println("please enter the username");
		String email = sc.next();
		System.out.println("Enter the password");
		String pass = sc.next();
		orgnizer.login(email,pass, org);
		EventOrganizer name = org.get(email);
		String nameOfOrganizer = name.getName();
		try { 
			int choice = 0;
			do {
				
				//after login all the options organizer have
				System.out.println("Select the option of your choice");
				System.out.println("Press 1 to add new Events");
				System.out.println("Press 2 to update an event");
				System.out.println("Press 3 to delete an Event");
				System.out.println("Press 4 to view all Event");
				System.out.println("Press 5 to logout");
				
				
				///to provide the organizer menu////////
				choice = sc.nextInt();
				
				switch (choice) {
				case 1:
					//to add event on the platform
					
					String result =addEvent(sc, events, eventService, nameOfOrganizer);
					 System.out.println(result);
					break;
				case 2:
					//update event
					 result =  updateEvent(sc, events,nameOfOrganizer);
					System.out.println(result);
					break;
				case 3:
					//delete or remove the event
					deleteEvent(sc, events, eventService,customer,nameOfOrganizer);
					System.out.println("Event has been cancelled");
					break;
				case 4:
					//this method is same as customer so I use the same method again here
					ViewAllEventsOrganizer(events, eventService);
					break;
				case 5:
					//logout
					System.out.println("you have successsfully logout");
					choice=10;
					return;
				default:
					System.out.println("invalid choice");
					break;
				}

			} while (choice <= 5);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}





	
	
	//Method related Event ORganizer///////////////////////////
	
	//(Note:to do any modifiation to an event event id is required like for delete or update an event)
	//organizer can see all the event posted by him/her.
	private static void ViewAllEventsOrganizer(Map<Integer, Event> events, EventServicesImpl eventService2) throws EventException {
		eventService.viewAllEventForOrganizer(events);
	}

	//(Note: it's also have to be approved by the admin before appear for the customer)
	//add event 
	public static String addEvent(Scanner sc, Map<Integer, Event> events, EventServices eventService, String nameOfOrganizer) throws FileNotFoundException, IOException {
		OrganizerImpl orgnizer = new OrganizerImpl();
		Map<String , EventOrganizer> orga= FileExists.organizerFile();
		sc.nextLine();
		System.out.println("please enter the Event details");
		System.out.println("Enter the Event name");
		String name = sc.nextLine();
		System.out.println("Enter the Event venue");
		String venue = sc.nextLine();
	    System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
	        String input = sc.nextLine();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
		System.out.println("Enter the Event Type");
		System.out.println(" MOVIE, CONCERT , THEATER , OTHER :");
		String eventType = sc.nextLine().toUpperCase();
		 
		EventType type = EventType.valueOf(eventType);
		System.out.println("Enter the Ticket price");
		double price = sc.nextDouble();
		System.out.println("Enter the Total number of seats");
		int seats = sc.nextInt();
		
		
        int id = IDGeneration.generateId();
		Event evn = new Event(id, name, venue , dateTime , price, seats,type,nameOfOrganizer);
		for (Map.Entry<String, EventOrganizer> me : orga.entrySet()) {
			  EventOrganizer or =me.getValue();
			    
			  
			List<Event> e=  or.getEvents();
			  e.add(evn);
			  
				ObjectOutputStream orgw = new ObjectOutputStream(new FileOutputStream("Organizer.ser"));
				orgw.writeObject(orga);
		}
		
		
		String str = eventService.addEvent(evn, events,nameOfOrganizer);// considering all details are valid

		return str;

	}
	
	//to remove an event
    // (Note: if organizer or admin delete an event that's booked by some customer the money will we refunded. to those customers)
	public static void deleteEvent(Scanner sc, Map<Integer, Event> events, EventServices evnService,Map<String, Customer> customer, String nameOfOrganizer)
			throws EventException, FileNotFoundException, IOException {
		OrganizerImpl orgnizer = new OrganizerImpl();
		Map<String , EventOrganizer> orga= FileExists.organizerFile();
		System.out.println("please enter the id of Event to be deleted");
		int id = sc.nextInt();
		evnService.deleteEvent(id, events,nameOfOrganizer);
		ObjectOutputStream orgw = new ObjectOutputStream(new FileOutputStream("Organizer.ser"));
		orgw.writeObject(orga);
		for (Entry<String, Customer> me : customer.entrySet()) {
			Customer a =me.getValue();

			 List<Booking> b = a.getBookingHistory();
			 Iterator<Booking> iterator = b.iterator();
			 while (iterator.hasNext()) {
			     Booking book = iterator.next();
			     if (book.getEventId() == id) {
			    	double totalAmount= book.getTotal();
			    	 a.setBalance(a.getBalance()+totalAmount);
			         iterator.remove();
			     }
			 }
			 
			  
			
		}
		ObjectOutputStream cus = new ObjectOutputStream(new FileOutputStream("Customer.ser"));
		
		cus.writeObject(cus);
		
		
	}
	
	
	
	//to upgrade 
    //(Note: non of the upgrade will we appear in the customer profile who has already book the event)
	
	public static String updateEvent(Scanner sc, Map<Integer, Event> event,String nameOfOrganizer)
		throws EventException, FileNotFoundException, IOException {
		OrganizerImpl orgnizer = new OrganizerImpl();
		Map<String , EventOrganizer> orga= FileExists.organizerFile();
		String result = null;
		System.out.println("please enter the id of the Event which is to be updated");
		int id = sc.nextInt();
		System.out.println("Enter the updated details ");

		System.out.println("Enter the Event name");
		String name = sc.next();

		System.out.println("Enter the Event venue");
		String venue = sc.next();
		sc.nextLine();
	    System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
	        String input = sc.nextLine();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
		System.out.println("Enter the Event Type");
		System.out.println("MOVIE, "+"CONCERT,"+ " THEATER, "+ "	OTHER");
		 String input1 = sc.nextLine().toLowerCase();

	        EventType type = EventType.valueOf(input1);
		System.out.println("Enter the Ticket price");
		double price = sc.nextDouble();
		System.out.println("Enter the Total number of seats");
		int seats = sc.nextInt();		
		Event update = new Event(id, name, venue, dateTime, price, seats, type, nameOfOrganizer);
				
		
		result  = eventService.updateEvent(id, update, event,nameOfOrganizer);
		ObjectOutputStream orgw = new ObjectOutputStream(new FileOutputStream("Organizer.ser"));
		orgw.writeObject(orga);
		return result;
		
		
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	//////Method to handle the functionality related to Admin
	///there is only one admin for know so the username and the password is hardcoded. (admin/admin)
	
	private static void handleAdminstrator(Scanner sc, Map<String, Customer> customer, Map<Integer, Event> event, List<Booking> bookings) {
		try {
			System.out.println("Enter Administrator username");
			String username = sc.nextLine();
			
			System.out.println("Enter Password");
			String password = sc.nextLine();
			
			
			if(!Administrator.authenticate(username, password))
					{
				throw new AuthenticationException("Invalid Username or Password");
					}
			
			System.out.println("Login successful");
			
////////////// Admin Menu///////////////////
			
			OrganizerImpl orgnizer = new OrganizerImpl();
			Map<String , EventOrganizer> orga= FileExists.organizerFile();
			while(true)
			{
				//after login admin functionality
				System.out.println("\nAdministrator Menu");
				System.out.println("1. Approve or Reject Organzier");
				System.out.println("2. Approve Event");
				System.out.println("3. Reject Event");
				System.out.println("4. View Events");
				System.out.println("5. View Bookings");
				System.out.println("6. View All Customers");
				System.out.println("7. Back ");
				
				
				System.out.println("Enter your choice: ");
				
				////after making the choice////////////////
				
				int choice = sc.nextInt();
				
				sc.nextLine();
				
				switch(choice)
				{
				case 1:
					///approve or reject the organizers
				
					approveOrRejectOrganizer(sc,orgnizer,orga);
					break;
				
				case 2:
					 
					///approve the events
					approveEvent(sc,event);
					break;
					
				
				case 3:
					//reject the events
					rejectEvent( sc ,event);
					break; 
					
				case 4:
					 
					// to see all the events on the platform
					viewEvents(event); 
					break;
					
					
				case 5:
					// to see all the bookings made on the platform
					
					viewBookings(bookings);
					break;
				
				case 6:
					
					// to view all the customers
					viewAllCustomers(customer);
					break;
				case 7:
				{
					//to return to previous menu
					return;
				}
				default:
					 
					System.out.println("invalid choice");
					break;
					}
				
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//approve to reject the organizer 

private static void approveOrRejectOrganizer(Scanner sc, OrganizerImpl orgnizer, Map<String, EventOrganizer> orga) throws BookingException, IOException, EventException {
		
Map<String, EventOrganizer> list = orgnizer.viewAllOrganizer(orga);
	
	for(Map.Entry<String,EventOrganizer > cus : list.entrySet())
	{
		System.out.println(cus);
	}
	
	while(true)
	{
		//menu to pick 
		System.out.println("1. Approve Organzier");
		System.out.println("2. Reject Organizer");
		System.out.println("3. Back ");
		
		
		System.out.println("Enter your choice: ");
		
		////after making the choice////////////////
		
		int choice = sc.nextInt();
		
		sc.nextLine();
		
		switch(choice)
		{
		case 1:
		//approve 
			approveOrganizer(sc,orgnizer,orga);
			break;
		
		case 2:
			 //reject
			rejectOrganizer(sc,orgnizer,orga);
			break;
			
		default:
			
			//to back to main menu of admin
			return;
		
	}	
} 

	
	
	}

//to reject the organizer 
// Organizer will be rejected by entrying it's user name 
// Once the organizer will rejected his/her account will re deleted for the system and have to resign in to again apply to become the organizer

private static void rejectOrganizer(Scanner sc, OrganizerImpl orgnizer, Map<String, EventOrganizer> orga) throws FileNotFoundException, IOException, EventException {
	
	System.out.println("Enter the username of the Organizer to reject");
	String  username = sc.nextLine();
	if (orga != null && orga.size() > 0) {

		if (orga.containsKey(username)) {
			
             EventOrganizer event = orga.get(username);
				orga.remove(username);
				
				
				
				
				
				ObjectOutputStream evn = new ObjectOutputStream(new FileOutputStream("Event.ser"));
				evn.writeObject(orga);
		} else {
			throw new EventException("Event not found");
		}

	} else {
		throw new EventException("Event list is empty");
	}
}

//to approved the organizer 
// same way have to use the organizer username to approve him/her as a organizer 


private static void approveOrganizer(Scanner sc, OrganizerImpl orgnizer, Map<String, EventOrganizer> orga) throws IOException, EventException {
	System.out.println("Enter the username of the Organizer to approve");
	String  username = sc.nextLine();
	
	if (orga != null && orga.size() > 0) {

		if (orga.containsKey(username)) {
			
             EventOrganizer ev = orga.get(username);
		    ev.setStatus("Approved");
		    ObjectOutputStream evn = new ObjectOutputStream(new FileOutputStream("Organizer.ser"));
			evn.writeObject(orga);
		
		    System.out.println("This Organzier has been approved");
		    System.out.println(ev);
			
		} else {
			throw new EventException("Organizer not found");
		}

	} else {
		throw new EventException("Organizer list is empty");
	}
}



private static void viewAllCustomers(Map<String, Customer> customer) throws BookingException {
	  
	Map<String, Customer> list = cusService.viewAllCustomers(customer);
	
	for(Map.Entry<String, Customer> cus : list.entrySet())
	{
		System.out.println(cus);
	}
	}

// to view all the booking made on the platform

private static void viewBookings(List<Booking> book) throws BookingException {
		
		List<Booking> myBookings = boo.viewAllBookings(book);
		for (Booking tr : myBookings) {
			System.out.println(tr);
		}
	}


// to view all the events on the platform
// to approve to reject any event have to use the id of that event so first view all the events and then do the next part

	private static void viewEvents(Map<Integer, Event> event) throws EventException {
		 eventService.viewAllEventForAdmin(event);
	}

	//reject or cancel an event work as same way as cancelled by the organizer//
	// Means money will be refunded to the customer if the event is booked by then.
	
	private static void rejectEvent(Scanner sc, Map<Integer, Event> event) throws EventException, FileNotFoundException, IOException {
				

			System.out.println("please enter the id of product to be Rejected");
			int id = sc.nextInt();	
			eventService.deleteEvent(id, event,"admin");
		
	}

	
	
	//to approve the event 
	//Note once the event has been approved only then it can we visible to the customer on customer side//
	
	private static void approveEvent(Scanner sc ,Map<Integer, Event> event) throws EventException, IOException {
		
		System.out.println("Enter the Id of the event to approve");
		int id = sc.nextInt();
		eventService.approveEvent(id,event);
	}
}
