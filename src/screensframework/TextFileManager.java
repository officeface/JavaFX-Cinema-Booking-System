package screensframework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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

	public TextFileManager() throws IOException {
		this.loginDetails = LoginDetailsToArrayList();
		this.filmList = FilmListToArrayList();
		this.filmTimes = FilmTimesToArrayList();
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
	 * @return An array of login details for users of the cinema booking system. The
	 *         array columns signify email-address/username, password and a
	 *         staff/customer choice that tells the system to point the user in the
	 *         correct direction.
	 * @throws IOException
	 *             if an input/output exception occurs
	 */
	public static List<String[]> LoginDetailsToArrayList() throws IOException {
		JSONObject obj = JSONUtils.getJSONObjectFromFile("/database.json");
		JSONArray jsonArray = obj.getJSONArray("LoginDetails");
		List<String[]> loginDetails = new ArrayList<String[]>();

		for (int i = 0; i < jsonArray.length(); i++) {
			String[] tempArray = new String[3];

//			tempArray[0] = jsonArray.getJSONObject(i).getString("email");
//			tempArray[1] = jsonArray.getJSONObject(i).getString("password");
//			tempArray[2] = jsonArray.getJSONObject(i).getString("type");
//			loginDetails.add(tempArray);
			tempArray[0] = "admin";
			tempArray[1] = "admin";
			tempArray[2] = "S";
			loginDetails.add(tempArray);

		}

		return loginDetails;
	}

	/**
	 * 
	 * @return The list of films, their img URLs and descriptions in an array list.
	 * @throws IOException
	 */
	public static List<String[]> FilmListToArrayList() throws IOException {
		JSONObject obj = JSONUtils.getJSONObjectFromFile("/database.json");
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
	public static List<String[]> FilmTimesToArrayList() throws IOException {
		JSONObject obj = JSONUtils.getJSONObjectFromFile("/database.json");
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

}
