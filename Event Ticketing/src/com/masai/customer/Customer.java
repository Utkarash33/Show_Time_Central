package com.masai.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.masai.booking.Booking;

public class Customer implements Serializable{

	
	private String firstName;
	private String lastName;
	private String address;
	private String mobileNumber;
	private String userName;
	private String password;
	private double balance;
	private List<Booking> bookingHistory;
	public Customer(String firstName, String lastName, String address, String mobileNumber, String userName,
			String password, double balance) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.userName = userName;
		this.password = password;
		this.balance = balance;
		this.bookingHistory = new ArrayList<>();
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Booking> getBookingHistory() {
		return bookingHistory;
	}
	
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public void setBookingHistory(List<Booking> bookingHistory) {
		this.bookingHistory = bookingHistory;
	}
	@Override
	public String toString() {
		return "Customer [Name=" + getFirstName() +" "+ getLastName() + ", address=" + getAddress()
				+ ", mobileNumber=" + getMobileNumber() + ", userName=" + getUserName()
				+ ", bookingHistory=" + getBookingHistory() + "]";
	}
	
	
	
	
	
	
}
