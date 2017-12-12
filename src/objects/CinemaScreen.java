package objects;

import java.util.List;

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
