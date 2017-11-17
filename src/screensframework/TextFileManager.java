package screensframework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
		this.loginDetails = FileToArray("LoginDetails");
		this.filmList = FileToArray("FilmList");
		this.filmTimes = FileToArray("FilmTimes");
	}
	
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
	 * @param fileName the file to be read.
	 * @return each row of a text file as elements in an ArrayList. The first line
	 *         in each .txt file is omitted and reserved as a descriptor of the
	 *         file.
	 * @throws IOException 
	 */
	public static List<String[]> FileToArray(String fileName) throws IOException {
		
		List<String> list = new ArrayList<String>();
		List<String[]> arr = new ArrayList<String[]>();
		String line = null;

		File file = new File("\\ScreensFramework\\" + fileName + ".txt");
		FileReader fileReader = new FileReader(file.getName());
		BufferedReader lineReader = new BufferedReader(fileReader);

		// Extract all lines from .txt file
		while ((line = lineReader.readLine()) != null) {
			list.add(line);
		}

		// Add emails/passwords/SorC to separate columns in arraylist
		for (String row : list) {
			arr.add(row.split("\\s+"));
		}
		arr.remove(0); // Remove first line of .txt file (not relevant)

		lineReader.close();
		return arr;
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
