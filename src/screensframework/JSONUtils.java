package screensframework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

	public static String getJSONStringFromFile(String path) throws IOException {
		Scanner scanner;
		InputStream in = TextFileManager.inputStreamFromFile(path);
		scanner = new Scanner(in);
		String json = scanner.useDelimiter("\\Z").next();
		scanner.close();
		in.close();
		return json;
	}
	
	public static JSONObject getJSONObjectFromFile(String path) throws JSONException, IOException {
		return new JSONObject(getJSONStringFromFile(path));
	}
	
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
