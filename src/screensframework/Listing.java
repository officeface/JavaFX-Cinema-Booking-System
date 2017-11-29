package screensframework;

import java.io.IOException;
import java.util.List;

/**
 * Class contains a specific listing of a film, the date that the film will be
 * shown, as well as its time of showing.
 * 
 * @author mark
 *
 */
public class Listing {

	private String showingID;
	private String title;
	private String date;
	private String time;
	private String[][] seats;

	public Listing(String showingID, String title, String date, String time, String[][] seats) {
		this.showingID = showingID;
		this.title = title;
		this.date = date;
		this.time = time;
		this.seats = seats;
	}

	public String getShowingID() {
		return showingID;
	}

	/**
	 * 
	 * @param title
	 *            Film title
	 * @param date
	 *            Date of listing
	 * @param time
	 *            Time of listing
	 * @return The Listing's ID as a String
	 * @throws IOException
	 */
	public static String findShowingID(String title, String date, String time) throws IOException {

		TextFileManager fileManager = new TextFileManager();
		List<String[]> filmTimes = fileManager.getFilmTimes();

		for (int i = 0; i < filmTimes.size(); i++) {
			if (filmTimes.get(i)[1].equals(title) && filmTimes.get(i)[2].equals(date)
					&& filmTimes.get(i)[3].equals(time)) {
				System.out.println("Successfully found ID");
				return filmTimes.get(i)[0];
			}
		}
		return null;

	}

	/**
	 * 
	 * @param showingID
	 *            The Listing's ID as a String
	 * @return the title of the movie
	 * @throws IOException
	 */
	public static String findMovieTitle(String showingID) throws IOException {

		TextFileManager fileManager = new TextFileManager();
		List<String[]> filmTimes = fileManager.getFilmTimes();

		for (int i = 0; i < filmTimes.size(); i++) {
			if (filmTimes.get(i)[0].equals(showingID)) {
				return filmTimes.get(i)[1];
			}
		}
		return null;

	}

	/**
	 * 
	 * @param showingID The Listing's ID as a String
	 * @return the date and time of the listing, formatted. e.g. "12/11/2017, 19:00"
	 * @throws IOException
	 */
	public static String findMovieDateAndTime(String showingID) throws IOException {

		TextFileManager fileManager = new TextFileManager();
		List<String[]> filmTimes = fileManager.getFilmTimes();

		for (int i = 0; i < filmTimes.size(); i++) {
			if (filmTimes.get(i)[0].equals(showingID)) {
				return filmTimes.get(i)[2] + " " + filmTimes.get(i)[3];
			}
		}
		return null;

	}

	public String[][] getSeats() {
		return seats;
	}

	public void setSeats(String[][] seats) {
		this.seats = seats;
	}

	public void setShowingID(String showingID) {
		this.showingID = showingID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
