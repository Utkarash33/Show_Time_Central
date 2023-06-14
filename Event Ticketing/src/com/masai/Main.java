package com.masai;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.masai.admin.Administrator;
import com.masai.booking.Booking;
import com.masai.booking.BookingServiceImpl;
import com.masai.customer.Customer;
import com.masai.customer.CustomerService;
import com.masai.customer.CustomerServiceImpl;
import com.masai.event.Event;
import com.masai.event.EventServices;
import com.masai.event.EventServicesImpl;
import com.masai.event.EventType;
import com.masai.exceptions.AuthenticationException;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.EventException;
import com.masai.exceptions.InvalidChoiceException;
import com.masai.exceptions.InvalidDetailsException;


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
//		System.out.println(customer.size());
		try {
			
			
			///To make the first choice
			while(true)
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
					handleAdminstrator();
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
					break;
				
				default:
					 
					
					System.out.println("Invalid Choice. Please try again.");
					break;
				}
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// serialization (finally always executed)
			try {
				ObjectOutputStream evn = new ObjectOutputStream(new FileOutputStream("Event.ser"));
				evn.writeObject(event);
				ObjectOutputStream coos = new ObjectOutputStream(new FileOutputStream("Customer.ser"));
				coos.writeObject(customer);

				ObjectOutputStream toos = new ObjectOutputStream(new FileOutputStream("Transactions.ser"));
				toos.writeObject(bookings);
			//	System.out.println("serialized..........");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}
	}

	
	////Method to handle the functionality related to Customer
	
	
	private static void handleCustomer(Scanner sc, Map<String, Customer> customer , Map<Integer , Event> events , List<Booking> book) throws InvalidChoiceException, DuplicateDataException, AuthenticationException, InvalidDetailsException {

		while(true)
		{
			System.out.println("1. SignIn");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			System.out.println("Enter your choice: ");
			
			
			int choice = scanner.nextInt();
			
			scanner.nextLine();
			
			switch(choice)
			{
			case 1:
				 
				customerSignUp(sc,customer);
				break;
				
			
			case 2:
				 
				customerFunctionality(sc, events , customer,book);
				break;
			
			case 3:
					 
					
				return;
			default:
				 
				
				throw new InvalidChoiceException("invalid Choice. Please try again");
			}
		}
      
		
	}
	//Sign in part.//////////
	
	private static void customerSignUp(Scanner sc, Map<String, Customer> customer) throws DuplicateDataException {
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
		cusService.signUp(cus, customer);
		System.out.println("customer has Succefully sign up");

	}

	private static void customerFunctionality(Scanner sc, Map<Integer, Event> events, Map<String, Customer> customer, List<Booking> book) throws AuthenticationException, InvalidDetailsException {
	
		
//		Login part/////////////////
		
		
		
		
		System.out.println("please enter the following details to login");
		System.out.println("please enter the username");
		String email = sc.next();
		System.out.println("Enter the password");
		String pass = sc.next();
		customerLogin(email,pass, customer, cusService);
		try { 
			int choice = 0;
			do {
				System.out.println("Select the option of your choice");
				System.out.println("Press 1 to view all Events");
				System.out.println("Press 2 to Book Ticket");
				System.out.println("Press 3 to add money to a wallet");
				System.out.println("Press 4 view wallet balance");
				System.out.println("Press 5 view my details");
				System.out.println("Press 6 view my transactions");
				System.out.println("Press 7 to logout");
				
				
				///to provide the customer menu////////
				
				choice = sc.nextInt();
				
				switch (choice) {
				case 1:
					//to see all events
					customerViewAllEvents(events, eventService);
					break;
				case 2:
					String result = customerBookEvent(sc, email, events, customer, book, cusService);
					System.out.println(result);
					break;
				case 3:
					String moneyAdded = customerAddMoneyToWallet(sc, email, customer, cusService);
					System.out.println(moneyAdded);
					break;
				case 4:
					double walletBalance = customerViewWalletBalance(email, customer, cusService);
					System.out.println("Wallet balance is: " + walletBalance);
					break;
				case 5:
					customerViewMyDetails(email, customer, cusService);
					break;
//				case 6:
//					customerViewCustomerTransactions(email, transactions, trnsactionService);
//					break;
				case 7:
					System.out.println("you have successsfully logout");
					break;
				default:
					System.out.println("invalid choice");
					break;
				}

			} while (choice <= 6);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static String customerBookEvent(Scanner sc, String email, Map<Integer, Event> events,
			Map<String, Customer> customer, List<Booking> boo, CustomerService cusService) throws InvalidDetailsException, EventException {
		System.out.println("Enter the event id or name");
		int id = sc.nextInt();
		System.out.println("enter the number of tickets");
		int qty = sc.nextInt();
		cusService.bookEvent(id, qty, email, events, customer, boo);

		return "You have successfully book for this event";
	}


	public static String customerAddMoneyToWallet(Scanner sc, String email, Map<String, Customer> customers,
			CustomerService cusService) {
		System.out.println("please enter the amount");
		double money = sc.nextDouble();
		boolean added = cusService.addMoneyToWallet(money, email, customers);

		return "Amount: " + money + " successfully added to your wallet";
	}

	public static double customerViewWalletBalance(String email, Map<String, Customer> customers,
			CustomerService cusService) {
		double walletBalance = cusService.viewWalletBalance(email, customers);
		return walletBalance;
	}

	public static void customerViewMyDetails(String email, Map<String, Customer> customers,
			CustomerService cusService) {
		Customer cus = cusService.viewCustomerDetails(email, customers);
		System.out.println("name : " + cus.getUserName());
		System.out.println("address : " + cus.getAddress());
		System.out.println("wallet balance : " + cus.getBalance());
	}


private static void customerViewAllEvents(Map<Integer, Event> events, EventServicesImpl eventService) throws EventException {
		
	eventService.viewAllEvent(events);
	}


///Authentication part//////
	
	private static void customerLogin(String username, String pass, Map<String, Customer> customer,
			CustomerService cusService) throws AuthenticationException, InvalidDetailsException {
		cusService.login(username, pass,customer);
		System.out.println("Customer has successfully login");
		
	}


	//////Method to handle the functionality related to EventOrganizer
	

	private static void handleEventOrgainzer(Scanner sc , Map<String, Customer> customer , Map<Integer, Event> events , List<Booking> book) throws AuthenticationException, InvalidDetailsException {
	
	   

		System.out.println("please enter the following details to add Event");
		System.out.println("please enter the Organizer Name");
		String name = sc.next();
		
		System.out.println("Hello ,"+name);
		try { 
			int choice = 0;
			do {
				System.out.println("Select the option of your choice");
				System.out.println("Press 1 to add new Events");
				System.out.println("Press 2 to update an event");
				System.out.println("Press 3 to delete an Event");
				System.out.println("Press 4 to view all Event");
				System.out.println("Press 5 to logout");
				
				
				///to provide the customer menu////////
				choice = sc.nextInt();
				
				switch (choice) {
				case 1:
					String result =addEvent(sc, events, eventService,name);
					 System.out.println(result);
					break;
				case 2:
					 result =  updateEvent(sc, events);
					System.out.println(result);
					break;
				case 3:
					deleteEvent(sc, events, eventService);
					System.out.println("Event has been cancelled");
					break;
				case 4:
					//this method is same as customer so I use the same method again here
					customerViewAllEvents(events, eventService);
					break;
				case 5:
					System.out.println("you have successsfully logout");
					break;
				default:
					System.out.println("invalid choice");
					break;
				}

			} while (choice <= 4);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	}

	//Method related Event ORganizer///////////////////////////
	
	public static String addEvent(Scanner sc, Map<Integer, Event> events, EventServices eventService, String nameOrgainzer) {
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
		Event evn = new Event(id, name, venue , dateTime , price, seats,type , nameOrgainzer);

		String str = eventService.addEvent(evn, events);// considering all details are valid

		return str;

	}
	public static void deleteEvent(Scanner sc, Map<Integer, Event> products, EventServices evnService)
			throws EventException {

		System.out.println("please enter the id of product to be deleted");
		int id = sc.nextInt();
		System.out.println("Name of the orgainzer");
		String org = sc.next();	
		evnService.deleteEvent(id, products,org);
	}
	public static String updateEvent(Scanner sc, Map<Integer, Event> event)
			throws EventException {
		String result = null;
		System.out.println("please enter the id of the Event which is to be updated");
		int id = sc.nextInt();
		System.out.println("Enter the updated details ");

		System.out.println("Enter the Event name");
		String name = sc.next();

		System.out.println("Enter the Event venue");
		String venue = sc.next();
	    System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
	        String input = sc.nextLine();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
		System.out.println("Enter the Event Type");
		System.out.println("MOVIE,\r\n"
				+ "	CONCERT,\r\n"
				+ "	THEATER,\r\n"
				+ "	OTHER");
		 String input1 = sc.nextLine();

	        EventType type = EventType.valueOf(input1);
		System.out.println("Enter the Ticket price");
		double price = sc.nextDouble();
		System.out.println("Enter the Total number of seats");
		int seats = sc.nextInt();
		
		System.out.println("Name of the orgainzer");
		String org = sc.next();		
		Event update = new Event(id, name, venue, dateTime, price, seats, type, org);
				
		
		result  = eventService.updateEvent(id, update, event,org);
		return result;
	}
//
//	public static void adminViewAllCustomers(Map<String, Customer> customers, CustomerService cusService)
//			throws ProductException {
//		List<Customer> list = cusService.viewAllCustomers(customers);
//
//		for (Customer c : list) {
//			System.out.println(c);
//		}
//	}
//
//	public static void adminViewAllTransactions(List<Transaction> transactions, TransactionService trnsactionService)
//			throws TransactionException {
//		List<Transaction> allTransactions = trnsactionService.viewAllTransactions(transactions);
//
//		for (Transaction tr : allTransactions) {
//			System.out.println(tr);
//		}
//
//	}
//	
	//////Method to handle the functionality related to Admin
	
	private static void handleAdminstrator() {
		try {
			System.out.println("Enter Administrator username");
			String username = scanner.nextLine();
			
			System.out.println("Enter Password");
			String password = scanner.nextLine();
			
			
			if(!Administrator.authenticate(username, password))
					{
				throw new AuthenticationException("Invalid Username or Password");
					}
			
			System.out.println("Login successful");
			
////////////// Admin Menu///////////////////
			
			
			while(true)
			{
				System.out.println("\nAdministrator Menu");
				System.out.println("1. Approve Event Organizer");
				System.out.println("2. Reject Event Organizer");
				System.out.println("3. View Events");
				System.out.println("4. View Bookings");
				System.out.println("5. Back ");
				
				
				System.out.println("Enter your choice: ");
				
				////after making the choice////////////////
				
				int choice = scanner.nextInt();
				
				scanner.nextLine();
				
				switch(choice)
				{
				case 1:
					 
					
					approveEventOrganizer();
					break;
				
				case 2:
					 
					
					rejectEventOrganizer();
					break;
				
				case 3:
						 
						
					viewEvents(); 
					break;
				case 4:
					 
					
					viewBookings();
					break;
				
				case 5:
					 
					return;
				
				default:
					 
					
					throw new InvalidChoiceException("invalid Choice. Please try again");
				}
				
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	
	
	
	
	
	private static void viewBookings() {
		// TODO Auto-generated method stub
		
	}

	private static void viewEvents() {
		// TODO Auto-generated method stub
		
	}

	private static void rejectEventOrganizer() {
		// TODO Auto-generated method stub
		
	}

	private static void approveEventOrganizer() {
		// TODO Auto-generated method stub
		
	}
}
