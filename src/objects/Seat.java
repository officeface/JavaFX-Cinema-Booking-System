package objects;

/**
 * Describes key features of a seat inside the Cinema. These include the seat's
 * number, row and column.
 * 
 * @author Mark Backhouse and Fraz Ahmad
 *
 */
public class Seat {

	private int seatNumber;
	private int seatRow;
	private int seatCol;

	public Seat(int seatNumber, int seatRow, int seatCol) {
		this.seatNumber = seatNumber;
		this.seatRow = seatRow;
		this.seatCol = seatCol;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int[] getSeatLocation() {
		int[] location = new int[2];
		location[0] = seatRow;
		location[1] = seatCol;
		return location;
	}

	public void setSeatLocation(int seatRow, int seatCol) {
		this.seatRow = seatRow;
		this.seatCol = seatCol;
	}

}
