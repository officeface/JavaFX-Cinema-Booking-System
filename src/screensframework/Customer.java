package screensframework;

import java.util.ArrayList;
import java.util.List;

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
	
	public void newBooking(int bookingID, String title, Seat seat, Customer customer) {
		Booking booking = new Booking(bookingID, title, seat, customer);
		bookingHistory.add(booking);
	}

}
