package com.masai;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.masai.booking.Booking;
import com.masai.customer.Customer;
import com.masai.event.Event;

public class FileExists {

////Customer ////////////////////////////////
	public static Map<String, Customer> customerFile() {

		Map<String, Customer> cFile = null;
		
		File f = new File("Customer.ser");
		boolean flag = false;
		try {
			if (!f.exists()) {
				f.createNewFile();
				flag = true;
			}

			if (flag) {
				cFile = new LinkedHashMap<>();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(cFile);
				return cFile;

			} else {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				cFile = (Map<String, Customer>)ois.readObject();

				return cFile;

			}

		} catch (Exception e) {
			// TODO: handle exception
            e.getStackTrace();
			System.out.println(e.getMessage());
		}
		return cFile;

	}


///Events ////////////////

	public static Map<Integer, Event> eventFile() {

		Map<Integer, Event> pFile = null;

		File f = new File("Event.ser");
		boolean flag = false;
		try {
			if (!f.exists()) {
				f.createNewFile();
				flag = true;
			}

			if (flag) {

				pFile = new LinkedHashMap<>();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(pFile);
				return pFile;

			} else {


				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				pFile = (LinkedHashMap<Integer, Event>) ois.readObject();

				return pFile;

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return pFile;
	}
	
	////Bookings//////////////////
	
	
	public static List<Booking> bookingFile() {

		List<Booking> tFile = new ArrayList<>();

		File f = new File("Bookings.ser");
		boolean flag = false;
		try {
			if (!f.exists()) {
				f.createNewFile();
				flag = true;
			}

			if (flag) {
				tFile =  new ArrayList<>();
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(tFile);

				return tFile;

			} else {

				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				tFile = (List<Booking>) ois.readObject();
				return tFile;

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return tFile;

	}


	
	
}
