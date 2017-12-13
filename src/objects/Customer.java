package objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import screensframework.JSONUtils;
import screensframework.ScreensFramework;
import screensframework.TextFileManager;

/**
 * Customer inherits basic properties from User and can also make/modify movie
 * bookings.
 * 
 * @author Mark Backhouse and Fraz Ahmad
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

	/**
	 * Gets the full booking history of the Customer from the database. The history
	 * is stored inside a HashMap, so that specific bookings can be accessed using
	 * keys.
	 * 
	 * @param user
	 *            the Customer whose booking is to be imported.
	 * @return the booking history of the Customer.
	 * @throws JSONException
	 *             if the JSON object cannot be found.
	 * @throws IOException
	 *             if the file cannot be found.
	 */
	public static Map<String, List<String>> importBookingHistory(User user) throws JSONException, IOException {
		Map<String, List<String>> bookingHistory = new HashMap<String, List<String>>();

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("LoginDetails");

		// Iterate through the Login Details array to find the Customer
		for (int i = 0; i < jsonArray.length(); i++) {
			// Once we have found the customer, try to get their bookings history:
			if (jsonArray.getJSONObject(i).getString("userID").equals(user.getUserID())) {
				try {
					JSONObject bookings = jsonArray.getJSONObject(i).getJSONObject("bookings");
					Iterator<?> keys = bookings.keys();

					while (keys.hasNext()) {
						String key = (String) keys.next();
						JSONArray booking = bookings.getJSONArray(key);
						List<String> value = new ArrayList<String>();

						for (Object seats : booking) {
							value.add(seats.toString());
						}

						bookingHistory.put(key, value);
					}

				} catch (Exception e) {
					ScreensFramework.LOGGER.warning("This customer has not yet made any bookings!");
					bookingHistory.put("No bookings have been made!", null);
				}
			}
		}
		return bookingHistory;
	}

}
