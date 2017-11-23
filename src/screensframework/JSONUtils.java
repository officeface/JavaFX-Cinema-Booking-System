package screensframework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

//	public static String getJSONStringFromFile(String path) throws IOException {
//		try {
//			//Scanner scanner;
//			InputStream in = TextFileManager.inputStreamFromFile(path);
//			Scanner scanner = new Scanner(in);
//			String json = scanner.useDelimiter("\\Z").next();
//			scanner.close();
//			in.close();
//			return json;
//		} catch (IOException e) {
//			System.out.println(e);
//		}
//		return null;
//	}
	
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

//	public static JSONObject getJSONObjectFromFile(String path) throws JSONException, IOException {
//		return new JSONObject(getJSONStringFromFile(path));
//	}
	
	public static JSONObject getJSONObjectFromFile(File jsonFile) throws JSONException, IOException {
		return new JSONObject(getJSONStringFromFile(jsonFile));
	}


//	public static JSONArray getJSONArrayFromFile(String path) throws JSONException, IOException {
//		return new JSONArray(getJSONStringFromFile(path));
//	}

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
