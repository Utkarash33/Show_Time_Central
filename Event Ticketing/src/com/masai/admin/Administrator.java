package com.masai.admin;

public class Administrator {

	
	private static final String ADMIN_USERNAME = "admin";
	private static final String ADMIN_PASSWORD = "admin";
	
	
	
	public static boolean authenticate(String username, String password)
	{
		return username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD);
	}
}
