package screensframework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Adapted from a YouTube video by @TheAnimeNerd2009, this class controls the
 * input streams from the JSON database.
 * 
 * @author Mark Backhouse and Fraz Ahmad
 *
 */
public class JSONUtils {

	/**
	 * This method takes a JSON file and returns it as a String.
	 * 
	 * @param path
	 *            the path of the file.
	 * @return the contents of the JSON file as a String.
	 * @throws IOException
	 *             if the file cannot be found.
	 */
	public static String getJSONStringFromFile(String path) throws IOException {
		try {
			// Scanner scanner;
			InputStream in = TextFileManager.inputStreamFromFile(path);
			Scanner scanner = new Scanner(in);
			String json = scanner.useDelimiter("\\Z").next();
			scanner.close();
			in.close();
			return json;
		} catch (IOException e) {
			ScreensFramework.LOGGER.warning(e.getMessage());
		}
		return null;
	}

	/**
	 * This method takes a JSON file and returns it as a String.
	 * 
	 * @param jsonFile
	 *            the input stream to be read.
	 * @return the contents of the JSON file as a String.
	 * @throws IOException
	 *             if the file cannot be found.
	 */
	public static String getJSONStringFromFile(InputStream jsonFile) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(jsonFile));
		try {
			String line;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} finally {
			in.close();
		}
		return stringBuilder.toString();
	}

	/**
	 * This method takes a JSON file and returns it as a String.
	 * @param jsonFile the input file to be read.
	 * @return the contents of the JSON file as a String.
	 * @throws IOException if the file cannot be found.
	 */
	public static String getJSONStringFromFile(File jsonFile) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile)));
		try {
			String line;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} finally {
			in.close();
		}
		return stringBuilder.toString();
	}

	/**
	 * Returns the JSON Object inside a JSON file.
	 * @param path the path of the file.
	 * @return the JSON Object inside the file.
	 * @throws JSONException if the JSON Object cannot be returned.
	 * @throws IOException if the file cannot be found.
	 */
	public static JSONObject getJSONObjectFromFile(String path) throws JSONException, IOException {
		return new JSONObject(getJSONStringFromFile(path));
	}

	/**
	 * Returns the JSON Object inside a JSON file.
	 * @param path the path of the file.
	 * @return the JSON Object inside the file.
	 * @throws JSONException if the JSON Object cannot be returned.
	 * @throws IOException if the file cannot be found.
	 */
	public static JSONObject getJSONObjectFromFile(InputStream in) throws JSONException, IOException {
		return new JSONObject(getJSONStringFromFile(in));
	}

	/**
	 * Returns the JSON Object inside a JSON file.
	 * @param path the path of the file.
	 * @return the JSON Object inside the file.
	 * @throws JSONException if the JSON Object cannot be returned.
	 * @throws IOException if the file cannot be found.
	 */
	public static JSONObject getJSONObjectFromFile(File jsonFile) throws JSONException, IOException {
		return new JSONObject(getJSONStringFromFile(jsonFile));
	}

	
	/**
	 * Checks whether or not a JSON Object with a predefined key exists.
	 * @param jsonObject the JSON Object.
	 * @param key the key of the object.
	 * @return true if the object exists, false otherwise.
	 */
	public static boolean objectExists(JSONObject jsonObject, String key) {
		Object o;
		try {
			o = jsonObject.get(key);

		} catch (Exception e) {
			return false;
		}
		return o != null;
	}
}
