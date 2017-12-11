package screensframework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class manages the various text files of the application by reading their
 * contents and storing them inside ArrayLists for usage from other classes.
 * 
 * @author mark
 *
 */
public class TextFileManager {

	private List<String[]> loginDetails;
	private List<String[]> filmList;
	private List<String[]> filmTimes;
	// static String filePath = new File("").getAbsolutePath();
	// static File database = new File(filePath +
	// "/CinemaBookingSystem/assets/database.json");
//	static URL url = TextFileManager.class.getResource("/database.json");
//	static File database = new File(url.getFile());
//	static File database2 = new File("./assets/database2.json");
	static URL url = TextFileManager.class.getResource("/database.json");
	static String database = "/database.json";
	

	public TextFileManager() throws IOException {
		this.loginDetails = loginDetailsToArrayList();
		this.filmList = filmListToArrayList();
		this.filmTimes = filmTimesToArrayList();
	}

	/**
	 * 
	 * @param path
	 *            address of the file to be read
	 * @return Input from a file as an Input Stream
	 */
	public static InputStream inputStreamFromFile(String path) {

		try {
			InputStream inputStream = TextFileManager.class.getResourceAsStream(path);
			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 *            Listing ID for a particular listing
	 * @return A 2-dimensional array of information about the availability of seats.
	 *         If the seat is free, its value will be "Free". If the seat is booked,
	 *         its value will be the User ID of the user who has booked it.
	 * @throws JSONException
	 * @throws IOException
	 */
	public static String[][] getSeatInformation(String id) throws JSONException, IOException {

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("FilmTimes");
		String title;

		// CHANGE SIZE OF ARRAY AT LATER DATE IF NECESSARY!!!
		String[][] seatInformation = new String[6][10];

		for (int i = 0; i < jsonArray.length(); i++) {
			if (jsonArray.getJSONObject(i).getString("showingID").equals(id)) {
				JSONObject seats = jsonArray.getJSONObject(i).getJSONObject("seats");

				title = jsonArray.getJSONObject(i).getString("title");

				for (int j = 0; j < seatInformation.length; j++) {
					for (int k = 0; k < seatInformation[0].length; k++) {
						Integer J = (Integer) j;
						Integer K = (Integer) k;
						seatInformation[j][k] = seats.getString(J.toString() + K.toString());

					}
				}

				System.out.println("Loaded information for " + title);
				break;
			}
		}

		return seatInformation;
	}

	/**
	 * Rewrites the database with new information for a user whose details will be
	 * updated
	 * 
	 * @param user
	 *            Customer whose details are to be updated
	 * @throws JSONException
	 * @throws IOException
	 */
	public static void updateUserDetails(User user) throws JSONException, IOException {
		String userID = user.getUserID();
		String newEmail = user.getEmail();
		String newFirstName = user.getFirstName();
		String newLastName = user.getLastName();
		

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("LoginDetails");

		for (int i = 0; i < jsonArray.length(); i++) {
			if (jsonArray.getJSONObject(i).getString("userID").equals(userID)) {
				jsonArray.getJSONObject(i).put("email", newEmail);
				jsonArray.getJSONObject(i).put("firstname", newFirstName);
				jsonArray.getJSONObject(i).put("surname", newLastName);
			}
		}

		// try-with-resources statement based on post comment below :)
		try (FileWriter file = new FileWriter("/database.json")) {
			file.write(obj.toString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}
	}

	/**
	 * Updates an existing listing with new seat information (normally after a
	 * customer has made a new booking and seating changes must reflect this)
	 * 
	 * @param listing
	 *            the Listing to be updated
	 * @throws IOException
	 * @throws JSONException
	 */
	public static void updateListing(Listing listing) throws JSONException, IOException {
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("FilmTimes");
		String[][] seats = listing.getSeats();

		JSONObject seatList = new JSONObject();

		// Fill out new seat object
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 10; j++) {
				seatList.put(((Integer) i).toString() + ((Integer) j).toString(), seats[i][j]);
			}
		}

		// Find Showing, then add in seat object
		for (int k = 0; k < jsonArray.length(); k++) {
			if (jsonArray.getJSONObject(k).getString("showingID").equals(listing.getShowingID())) {
				jsonArray.getJSONObject(k).put("seats", seatList);
			}
		}

		// Write object to database file
		try (FileWriter file = new FileWriter("./assets/database.json")) {
			file.write(obj.toString());
			System.out.println("Successfully updated JSON Object in File...");
			System.out.println("\nJSON Object: " + obj);
		}
	}

	/**
	 * Updates a User's booking history with a new booking.
	 * 
	 * @param booking
	 *            The Booking to be added
	 * @throws IOException
	 * @throws JSONException
	 */
	public static void updateBookingHistory(Booking booking) throws JSONException, IOException {
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("LoginDetails");
		JSONArray seatInfo;
		try {
			seatInfo = new JSONArray();

			for (int i = 0; i < jsonArray.length(); i++) {
				if (jsonArray.getJSONObject(i).getString("userID").equals(booking.getCustomer().getUserID())) {
					seatInfo = jsonArray.getJSONObject(i).getJSONObject("bookings")
							.getJSONArray(booking.getMovie().getShowingID());
				}
			}

		} catch (Exception e) {
			seatInfo = new JSONArray();
		}
		for (int i = 0; i < booking.getSeats().size(); i++) {
			System.out.println(booking.getSeats().get(i));
			seatInfo.put(booking.getSeats().get(i));
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			if (jsonArray.getJSONObject(i).getString("userID").equals(booking.getCustomer().getUserID())) {
				jsonArray.getJSONObject(i).getJSONObject("bookings").put(booking.getMovie().getShowingID(), seatInfo);

			}
		}
		// Write object to database file
		try (FileWriter file = new FileWriter("./assets/database.json")) {
			file.write(obj.toString());
			System.out.println("Successfully updated Booking History in File...");
			System.out.println("\nJSON Object: " + obj);
		}
	}

	/**
	 * Removes a specified booking from the User's history
	 * 
	 * @param user
	 *            The User whose booking should be removed
	 * @param listingID
	 *            The ID for the listing to be removes
	 * @throws IOException
	 * @throws JSONException
	 */
	public static void removeBooking(User user, String listingID) throws JSONException, IOException {
		// Steps: remove listing from user's part of database
		// Find steps inside listing part of database and reset them to "Free"

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray loginArray = obj.getJSONArray("LoginDetails");
		JSONArray listingsArray = obj.getJSONArray("FilmTimes");
		JSONObject listingToBeChanged = null;
		JSONObject listingSeats = null; // Initialise
		int indexOfListing = -1;

		// Set listing seats:
		for (int i = 0; i < listingsArray.length(); i++) {
			if (listingsArray.getJSONObject(i).getString("showingID").equals(listingID)) {
				listingToBeChanged = listingsArray.getJSONObject(i);
				listingSeats = listingToBeChanged.getJSONObject("seats");
				indexOfListing = i;
			}
		}

		// Find user's seats for the booking to be deleted:
		for (int i = 0; i < loginArray.length(); i++) {

			JSONObject JSONUser = loginArray.getJSONObject(i);
			if (JSONUser.getString("userID").equals(user.getUserID())) { // Find the user in the database

				// Convert the User's seats for this listing into array format:
				JSONArray seats = JSONUser.getJSONObject("bookings").getJSONArray(listingID); // The seats in question

				// Modify listing's seats by setting each of this user's seats to "Free":
				for (int j = 0; j < seats.length(); j++) {
					System.out.println(seatToCoordinate(seats.get(j).toString()));
					String seatToChange = seatToCoordinate(seats.getString(j).toString());
					listingSeats.put(seatToChange, "Free");

				}

				// Put this seat information back into the listings array:
				listingToBeChanged.put("seats", listingSeats);
				listingsArray.remove(indexOfListing);
				listingsArray.put(listingToBeChanged);

				// Delete user's seats for this listing:
				JSONUser.getJSONObject("bookings").remove(listingID);

			}
		}

		// Write object to database file
		try (FileWriter file = new FileWriter("./assets/database.json")) {
			file.write(obj.toString());
			System.out.println("Successfully updated Booking History in File...");
			System.out.println("\nJSON Object: " + obj);
		}

	}

	/**
	 * 
	 * @param seat
	 *            A String in the form "Seat A7"
	 * @return The coordinates of a booking of the form "Seat A7". In this case the
	 *         returned value would be "06", since Seat A7 is in row 0 and column 6.
	 */
	public static String seatToCoordinate(String seat) {
		char row;
		String col;
		Integer colAsNumber;

		String letterNumberCombo = seat.split(" ")[1];
		row = (char) (letterNumberCombo.charAt(0) - 17);
		
		colAsNumber = Integer.parseInt(letterNumberCombo.substring(1, letterNumberCombo.length())) - 1;
		col = colAsNumber.toString();
		

		return ((Character) (row)).toString() + col;
	}

	/**
	 * 
	 * @return Adds films to the database - IN THIS CASE DATABASE 2
	 * @throws IOException
	 */
	public static void addFilmDetails(Movie newmovie) throws JSONException, IOException {

		// TEST OF ADDING A NEW FILM IN - - DATABASE 2! - moved now to database 1 since
		// it works! (mark)
		String title = newmovie.getTitle();
		String image = newmovie.getImage();
		String description = newmovie.getDescription();

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database); // works! Switched to database 1
																					// (mark)

		JSONArray jsonArray = obj.getJSONArray("FilmList");

		// APPENDS TO THE END OF THE FILELIST! - HOWEVER DB GOES CRAZY
		JSONObject templist = new JSONObject();
		templist.put("title", title);
		templist.put("image", image);
		templist.put("description", description);
		jsonArray.put(templist);

		// // try-with-resources statement based on post comment below :)
		try (FileWriter file = new FileWriter("./assets/database.json")) {
			file.write(obj.toString());
			System.out.println("Successfully Added JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}

	}

	/**
	 * 
	 * @return Adds film listing to the database - IN THIS CASE DATABASE 1
	 * @throws IOException
	 */
	public static void addFilmListings(Listing newlisting) throws JSONException, IOException {

		String showingID = newlisting.getShowingID();
		String title = newlisting.getTitle();
		String date = newlisting.getDate();
		String time = newlisting.getTime();

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database); // testing
																					// in
																					// database
																					// 2
		JSONArray jsonArray = obj.getJSONArray("FilmTimes");

		JSONObject seatList = new JSONObject();
		for (int i = 0; i < 60; i++) {
			Integer I = (Integer) i;
			if (i < 10) {
				seatList.put("0" + I.toString(), "Free");
			} else {
				seatList.put(I.toString(), "Free");
			}
		}

		// APPENDS TO THE END OF THE FILETIMES! - HOWEVER DB GOES CRAZY
		JSONObject templist = new JSONObject();
		templist.put("date", date);
		templist.put("showingID", showingID);
		templist.put("time", time);
		templist.put("title", title);
		templist.put("seats", seatList);

		jsonArray.put(templist);

		// // try-with-resources statement based on post comment below :)
		try (FileWriter file = new FileWriter("./assets/database.json")) {
			file.write(obj.toString());
			System.out.println("Successfully Added JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}

	}

	// public static void ExportAllFilmDetails () throws JSONException,
	// IOException {
	// JSONObject obj =
	// JSONUtils.getJSONObjectFromFile(TextFileManager.database);
	// JSONArray jsonArray = obj.getJSONArray("FilmList");
	//
	// File file=new File("/tmp2/fromJSON.csv");
	// String csv = CDL.toString(jsonArray);
	//
	// }

	/**
	 * 
	 * @return An array of login details for users of the cinema booking system. The
	 *         array columns signify email-address/username, password and a
	 *         staff/customer choice that tells the system to point the user in the
	 *         correct direction.
	 * @throws IOException
	 *             if an input/output exception occurs
	 */
	public static List<String[]> loginDetailsToArrayList() throws IOException {

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("LoginDetails");
		List<String[]> loginDetails = new ArrayList<String[]>();

		for (int i = 0; i < jsonArray.length(); i++) {
			String[] tempArray = new String[6];

			tempArray[0] = jsonArray.getJSONObject(i).getString("userID");
			tempArray[1] = jsonArray.getJSONObject(i).getString("email");
			tempArray[2] = jsonArray.getJSONObject(i).getString("password");
			tempArray[3] = jsonArray.getJSONObject(i).getString("type");
			tempArray[4] = jsonArray.getJSONObject(i).getString("firstname");
			tempArray[5] = jsonArray.getJSONObject(i).getString("surname");

			loginDetails.add(tempArray);

		}

		return loginDetails;
	}

	/**
	 * 
	 * @return The list of films, their img URLs and descriptions in an array list.
	 * @throws IOException
	 */
	public static List<String[]> filmListToArrayList() throws IOException {
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("FilmList");
		List<String[]> filmList = new ArrayList<String[]>();

		for (int i = 0; i < jsonArray.length(); i++) {
			String[] tempArray = new String[3];

			tempArray[0] = jsonArray.getJSONObject(i).getString("title");
			tempArray[1] = jsonArray.getJSONObject(i).getString("image");
			tempArray[2] = jsonArray.getJSONObject(i).getString("description");

			filmList.add(tempArray);

		}

		return filmList;
	}

	/**
	 * 
	 * @return The list of film showings in an array list. Each film has a
	 *         collection of dates and times attributed to it.
	 * @throws IOException
	 */
	public static List<String[]> filmTimesToArrayList() throws IOException {
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("FilmTimes");
		List<String[]> filmTimes = new ArrayList<String[]>();

		for (int i = 0; i < jsonArray.length(); i++) {
			String[] tempArray = new String[4];

			tempArray[0] = jsonArray.getJSONObject(i).getString("showingID");
			tempArray[1] = jsonArray.getJSONObject(i).getString("title");
			tempArray[2] = jsonArray.getJSONObject(i).getString("date");
			tempArray[3] = jsonArray.getJSONObject(i).getString("time");

			filmTimes.add(tempArray);

		}

		return filmTimes;
	}

	/**
	 * 
	 * @param date
	 *            in the format dd/MM/yyyy
	 * @return A list of film titles that are playing on a specified date
	 * @throws IOException
	 */
	public static List<String> filmsFilteredByDate(String date) throws IOException {

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray filmTimesArray = obj.getJSONArray("FilmTimes");
		List<String> films = new ArrayList<String>();

		for (int j = 0; j < filmTimesArray.length(); j++) {
			if (obj.getJSONArray("FilmTimes").getJSONObject(j).getString("date").equals(date)) {
				if (!films.contains(obj.getJSONArray("FilmTimes").getJSONObject(j).getString("title"))) {
					films.add(obj.getJSONArray("FilmTimes").getJSONObject(j).getString("title"));
				}
			}
		}

		return films;
	}

	/**
	 * 
	 * @param date
	 *            Date of the film listing
	 * @param film
	 *            Title of the film listing
	 * @return All times that this film is showing on this date
	 * @throws IOException
	 */
	public static List<String> timesFilteredByDateAndFilm(String date, String film) throws IOException {
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray filmTimesArray = obj.getJSONArray("FilmTimes");
		List<String> times = new ArrayList<String>();

		for (int i = 0; i < filmTimesArray.length(); i++) {
			if (obj.getJSONArray("FilmTimes").getJSONObject(i).getString("date").equals(date)
					&& obj.getJSONArray("FilmTimes").getJSONObject(i).getString("title").equals(film)) {

				times.add(obj.getJSONArray("FilmTimes").getJSONObject(i).getString("time"));

			}
		}

		return times;
	}

	public List<String[]> getLoginDetails() {
		return loginDetails;
	}

	public void setLoginDetails(List<String[]> loginDetails) {
		this.loginDetails = loginDetails;
	}

	public List<String[]> getFilmList() {
		return filmList;
	}

	public void setFilmList(List<String[]> filmList) {
		this.filmList = filmList;
	}

	public List<String[]> getFilmTimes() {
		return filmTimes;
	}

	public void setFilmTimes(List<String[]> filmTimes) {
		this.filmTimes = filmTimes;
	}

	/**
	 * 
	 * @return Lists of all film titles in the database
	 * @throws IOException
	 */
	public static List<String[]> getFilmTitles() throws IOException {
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray jsonArray = obj.getJSONArray("FilmList");
		List<String[]> filmListofTitles = new ArrayList<String[]>();
		for (int i = 0; i < jsonArray.length(); i++) {
			String[] tempArray = new String[1];
			tempArray[0] = jsonArray.getJSONObject(i).getString("title");
			filmListofTitles.add(tempArray);
		}
		return filmListofTitles;
	}

	/**
	 * 
	 * @return Lists of all timings available by the cinema
	 * @throws IOException
	 */
	public static String[] getFilmTimings() throws IOException {
		//
		String[] filmListofTimings = new String[24];

		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				filmListofTimings[i] = "0" + i + ":00";
			} else {
				filmListofTimings[i] = i + ":00";
			}
		}
		// filmListofTimings[0] = "12:00";
		// filmListofTimings[1] = "13:00";
		// filmListofTimings[2] = "14:00";
		// filmListofTimings[3] = "15:00";
		// filmListofTimings[4] = "16:00";
		// filmListofTimings[5] = "17:00";
		// filmListofTimings[6] = "18:00";
		// filmListofTimings[7] = "19:00";
		// filmListofTimings[8] = "20:00";
		// filmListofTimings[9] = "21:00";
		// filmListofTimings[10] = "22:00";
		// filmListofTimings[11] = "23:00";
		return filmListofTimings;
	}

}
