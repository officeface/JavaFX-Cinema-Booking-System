package screensframework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test {

	public static void main(String[] args) throws JSONException, IOException {
		// TODO Auto-generated method stub
		String date = "01/12/2017";
		
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		JSONArray filmListArray = obj.getJSONArray("FilmList");
		JSONArray filmTimesArray = obj.getJSONArray("FilmTimes");
		List<String> films = new ArrayList<String>();
		
			for (int j = 0; j < filmTimesArray.length(); j++) {
				if(obj.getJSONArray("FilmTimes").getJSONObject(j).getString("date").equals(date)) {
					if(!films.contains(obj.getJSONArray("FilmTimes").getJSONObject(j).getString("title"))) {
						films.add(obj.getJSONArray("FilmTimes").getJSONObject(j).getString("title"));
						System.out.println(obj.getJSONArray("FilmTimes").getJSONObject(j).getString("title"));
					}
					
				}
				
			}
		
		
	}

}
