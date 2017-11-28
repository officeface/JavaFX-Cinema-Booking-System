package screensframework;

import java.util.List;

/**
 * Contains information about a booking, including a booking ID, film title,
 * seat number and customer details.
 * 
 * @author mark
 *
 */
public class Booking {

	private int bookingID;
	private Listing movie;
	private List<Seat> seats;
	private Customer customer;

	public Booking(int bookingID, Listing movie, List<Seat> seats, Customer customer) {
		super();
		this.bookingID = bookingID;
		this.movie = movie;
		this.seats = seats;
		this.customer = customer;
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	

	public Listing getMovie() {
		return movie;
	}

	public void setMovie(Listing movie) {
		this.movie = movie;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
