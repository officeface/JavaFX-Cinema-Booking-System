package screensframework;

import java.io.File;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {

	public static void main(String[] args) throws JSONException, IOException {
		File file = new File("./assets");
		for(String fileNames : file.list()) System.out.println(fileNames);
		
		File database = new File("./assets/database2.json");
		// TODO Auto-generated method stub
		JSONObject obj = JSONUtils.getJSONObjectFromFile(database);
		JSONArray jsonArray = obj.getJSONArray("LoginDetails");
		System.out.println(jsonArray);
	}

}
