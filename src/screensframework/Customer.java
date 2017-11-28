package screensframework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Customer inherits basic properties from User and can also make/modify movie
 * bookings
 * 
 * @author mark
 *
 */
public class Customer extends User {

	public Customer(String userID, String email, String password, String firstName, String lastName) {
		super(userID, email, password, firstName, lastName);
	}

	private List<Booking> bookingHistory = new ArrayList<Booking>();

	

	public List<Booking> getBookingHistory() {
		return bookingHistory;
	}

	public void setBookingHistory(List<Booking> bookingHistory) {
		this.bookingHistory = bookingHistory;
	}

	@Override
	public void clearDetails() {
		super.clearDetails();
		bookingHistory = null;
	}
	
	public static Map<String, List<String>> importBookingHistory(User user) throws JSONException, IOException{
		Map<String, List<String>> bookingHistory = new HashMap<String, List<String>>();
		
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("LoginDetails");
		
		for (int i = 0; i < jsonArray.length(); i++) {
			if(jsonArray.getJSONObject(i).getString("userID").equals(user.getUserID())) {
				try {
					JSONObject bookings = jsonArray.getJSONObject(i).getJSONObject("bookings");
					Iterator<?> keys = bookings.keys();

					while( keys.hasNext() ) {
					    String key = (String)keys.next();
					    JSONArray booking = bookings.getJSONArray(key);
					    List<String> value = new ArrayList<String>();
					    
					    for (Object seats : booking) {
					    	value.add(seats.toString());
					    }
					    
					    bookingHistory.put(key, value);
					}
					
				} catch (Exception e) {
					System.out.println(e);
					bookingHistory.put("No bookings have been made!", null);
				}
			}
		}
		return bookingHistory;
	}
	
//	public void newBooking(int bookingID, Listing movie, Seat seat, Customer customer) {
//		Booking booking = new Booking(bookingID, movie, seat, customer);
//		bookingHistory.add(booking);
//	}

}
