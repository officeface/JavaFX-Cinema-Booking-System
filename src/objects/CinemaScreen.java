package objects;

import java.util.List;

/**
 * Describes a cinema screen inside the cinema, containing an associated list of
 * listings (films that are showing on this screen) and seats. This class allows
 * for the cinema application to be easily updated so as to contain multiple
 * cinema screens, so that concurrent listings may be made.
 * 
 * @author Mark Backhouse and Fraz Ahmad
 *
 */
public class CinemaScreen {

	private List<Listing> listOfListings; // All the different shows that are showing
	private List<Seat> seats; // The seats in the cinema

	public List<Listing> getListOfListings() {
		return listOfListings;
	}

	public void setListOfListings(List<Listing> listOfListings) {
		this.listOfListings = listOfListings;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

}
