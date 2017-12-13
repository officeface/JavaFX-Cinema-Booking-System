package objects;

import java.util.List;

/**
 * Contains information about a booking, including a booking ID, film title,
 * seat number and customer details.
 * 
 * @author Mark Backhouse and Fraz Ahmad
 *
 */
public class Booking {

	private int bookingID;
	private Listing movie;
	private List<String> seats;
	private Customer customer;

	public Booking(int bookingID, Listing movie, List<String> seats, Customer customer) {
		super();
		this.bookingID = bookingID;
		this.movie = movie;
		this.seats = seats;
		this.customer = customer;
	}

	/**
	 * Get the Booking ID.
	 * @return the Booking ID as an integer.
	 */
	public int getBookingID() {
		return bookingID;
	}
	
	/**
	 * Set the Booking ID.
	 * @param bookingID the ID to be set.
	 */
	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	/**
	 * Get the listing associated with the booking.
	 * @return the listing associated with the booking.
	 */
	public Listing getMovie() {
		return movie;
	}

	/**
	 * Set the listing for a booking.
	 * @param movie the listing to be set.
	 */
	public void setMovie(Listing movie) {
		this.movie = movie;
	}

	/**
	 * Get the list of seats associated with the booking.
	 * @return the seats in the booking.
	 */
	public List<String> getSeats() {
		return seats;
	}

	/**
	 * Set the list of seats for the booking.
	 * @param seats the seats to be set.
	 */
	public void setSeats(List<String> seats) {
		this.seats = seats;
	}

	/**
	 * Get the customer associated with the booking.
	 * @return the customer associated with the booking.
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Set the customer for the booking.
	 * @param customer the customer to be set.
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
