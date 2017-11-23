package screensframework;

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
	private Seat seat;
	private Customer customer;

	public Booking(int bookingID, Listing movie, Seat seat, Customer customer) {
		super();
		this.bookingID = bookingID;
		this.movie = movie;
		this.seat = seat;
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

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
