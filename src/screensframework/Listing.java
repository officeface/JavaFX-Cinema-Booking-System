package screensframework;

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

	public Listing(String showingID, String title, String date, String time) {
		this.showingID = showingID;
		this.title = title;
		this.date = date;
		this.time = time;
	}
	
	public String getShowingID() {
		return showingID;
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
