package screensframework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sun.rmi.runtime.Log;

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
	static File database = new File("./assets/database.json");	
	static File database2 = new File("./assets/database2.json");


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
		try (FileWriter file = new FileWriter("./assets/database.json")) {
			file.write(obj.toString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}

	}
	
	
	
	/**
	 * 
	 * @return Adds films to the database - IN THIS CASE DATABASE 2	
	 * @throws IOException
	 */
	public static void addFilmDetails(Movie newmovie) throws JSONException, IOException {
		
		//TEST OF ADDING A NEW FILM IN  - - DATABASE 2!
        String title = newmovie.getTitle();
		String image = newmovie.getImage();
		String description = newmovie.getDescription();

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database2); //testing in database 2 
		JSONArray jsonArray = obj.getJSONArray("FilmList");
		
		//APPENDS TO THE END OF THE FILELIST! - HOWEVER DB GOES CRAZY
	    		JSONObject templist = new JSONObject();
	    		templist.put("title",title);
	    		templist.put("image",image);
	    		templist.put("description",description);
	            jsonArray.put(templist);
		
//		// try-with-resources statement based on post comment below :)
		try (FileWriter file = new FileWriter("./assets/database2.json")) {
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
		String[][] seats = newlisting.getSeats();

		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database); //testing in database 2 
		JSONArray jsonArray = obj.getJSONArray("FilmTimes");
		
		//APPENDS TO THE END OF THE FILETIMES! - HOWEVER DB GOES CRAZY
	    		JSONObject templist = new JSONObject();
	    		templist.put("date",date);
	    		templist.put("showingID",showingID);
	    		templist.put("time",time);
	    		templist.put("title",title);
	    		templist.put("seats",seats);
	        jsonArray.put(templist);
		
	            
	            
	            
//		// try-with-resources statement based on post comment below :)
		try (FileWriter file = new FileWriter("./assets/database.json")) {
			file.write(obj.toString());
			System.out.println("Successfully Added JSON Object to File...");
			System.out.println("\nJSON Object: " + obj);
		}

	}
	
	
//	public static void ExportAllFilmDetails () throws JSONException, IOException {
//		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
//		JSONArray jsonArray = obj.getJSONArray("FilmList");
//		
//        File file=new File("/tmp2/fromJSON.csv");
//        String csv = CDL.toString(jsonArray);
//        
//	}
	
	
	
	

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
		String[] filmListofTimings = new String[11];
		filmListofTimings[0] = "12:00";
		filmListofTimings[1] = "13:00";
		filmListofTimings[2] = "14:00";
		filmListofTimings[3] = "15:00";
		filmListofTimings[4] = "16:00";
		filmListofTimings[5] = "17:00";
		filmListofTimings[6] = "18:00";
		filmListofTimings[7] = "19:00";
		filmListofTimings[8] = "20:00";
		filmListofTimings[9] = "21:00";
		filmListofTimings[10] = "22:00";
		filmListofTimings[10] = "23:00";
		return filmListofTimings;
	}
	
	
	
	
	
	
	
	
	
	

}
