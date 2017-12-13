package screensframework;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import objects.Listing;

/**
 * FXML Controller class
 *
 */

public class AddFilmListingsController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML
	private ComboBox<String> comboSelectFilm; // Select from a list of films
	private ObservableList<String> filmNames = FXCollections.observableArrayList(); // Container
																					// for
																					// film
																					// titles
	private String selectedFilmForListing; // Holds selected film

	@FXML
	private ComboBox<String> comboChooseTime; // Select appropriate times
	private ObservableList<String> timesAvailable = FXCollections.observableArrayList(); // Container
																							// for
																							// timings
	private String selectedTimeForListing;// Holds selected time

	@FXML
	private DatePicker listingsDatePicker; // Select a date
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formatting
																								// dates
	private String selectedDateForListing;// Holds selected date

	@FXML
	private Button btnAddListings; // Once form complete - press button to add
									// to records

	@FXML
	private Label lblFeedback; // Feedback label - provides users with feedback based on their actions

	// Toolbar variables
	@FXML
	private Button btnSLogout;

	@FXML
	private Button btnSHome;

	@FXML
	private Button btnGoToNewFilm;

	/**
	 * Initialises the controller class.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Adds current list of films from the database to the comboBox - choose film
		List<String[]> filmList = null;
		try {
			filmList = TextFileManager.getFilmTitles(); // Method to retrieve the film titles
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < filmList.size(); i++) {
			filmNames.addAll(filmList.get(i)); // Intermediate storage
		}
		comboSelectFilm.setItems(filmNames); // Sets comboBox the film titles

		// Adds list of timings from the database to the comboBox - choose time
		String[] timeList = null;
		try {
			timeList = TextFileManager.getFilmTimings(); // Method to retrieve the timings
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < timeList.length; i++) {
			timesAvailable.add(timeList[i]); // Intermediate storage
		}
		comboChooseTime.setItems(timesAvailable); // Sets comboBox the timings

	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	/**
	 * Gets the value of the selected film from the list of films
	 * 
	 * @author frazahmad
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void getSelectedFilm(ActionEvent event) throws IOException {
		this.selectedFilmForListing = comboSelectFilm.getValue();
		ScreensFramework.LOGGER.info(selectedFilmForListing + " selected.");
		System.out.println(selectedFilmForListing);
	}

	/**
	 * Gets the value of the selected date from date picker
	 * 
	 * @author frazahmad
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void getSelectedDate(ActionEvent event) throws IOException {
		this.selectedDateForListing = dateTimeFormatter.format(listingsDatePicker.getValue());
		ScreensFramework.LOGGER.info(selectedDateForListing + " selected.");
		System.out.println(selectedDateForListing);
	}

	/**
	 * Gets the value of the selected time from the list of times
	 * 
	 * @author frazahmad
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void getSelectedTime(ActionEvent event) throws IOException {
		this.selectedTimeForListing = comboChooseTime.getValue();
		ScreensFramework.LOGGER.info(selectedTimeForListing + " selected.");
		System.out.println(selectedTimeForListing);
	}

	/**
	 * Creates a new film listing based on user's action. The values are then
	 * transferred to the textfilemanager.
	 * 
	 * @author frazahmad
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void addFilmListing(ActionEvent event) throws IOException {

		// Conditional statements are used so no empty fields can be added.
		if (readyToSelect() && showingTimeIsFree(this.selectedDateForListing, this.selectedTimeForListing)) {

			// New showing ID
			JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
			JSONArray jsonArray = obj.getJSONArray("FilmTimes");
			int filmTimeslength = jsonArray.length(); // Scans the database for the length to get n showingID's
			Integer newShowingIDNumber = filmTimeslength + 1; // New showingID is length of FilmTimes array + 1
			String newShowingID = newShowingIDNumber.toString(); // Integer to string

			Listing newListing = new Listing(newShowingID, this.selectedFilmForListing, this.selectedDateForListing,
					this.selectedTimeForListing, null); // New class made with parameters from the get methods

			TextFileManager.addFilmListings(newListing); // Update film listing
															// in database

			this.lblFeedback.setText("Added new listing!\nTitle: " + this.selectedFilmForListing + "\nDate: "
					+ this.selectedDateForListing + ",  Time: " + this.selectedTimeForListing); // Label to inform user
																								// action
		} else if (!readyToSelect()) {

			this.lblFeedback.setText("No fields should be empty!");
		} else {
			this.lblFeedback.setText("A film is already playing at this time!");
		}
	}

	/**
	 * 
	 * @param date
	 *            Selected date for new showing
	 * @param time
	 *            Selected time for new showing
	 * @return True if there is no showing in the database for the selected date and
	 *         time. False if there is currently a showing at the selected date and
	 *         time.
	 * @throws IOException
	 *             If the database file cannot be found
	 */
	public boolean showingTimeIsFree(String date, String time) throws IOException {
		TextFileManager fileManager = new TextFileManager();
		List<String[]> filmTimes = fileManager.getFilmTimes();

		for (int i = 0; i < filmTimes.size(); i++) {
			if (filmTimes.get(i)[2].equals(date) && filmTimes.get(i)[3].equals(time)) {
				return false; // Checks if the showing time is free
			}
		}
		return true;
	}

	/**
	 * 
	 * @return True if something has been selected in each of the "Select film",
	 *         "Select date" and "Select time" of showing. Will return false if any
	 *         one of these has been left empty.
	 */
	public boolean readyToSelect() {

		try {
			if (!comboSelectFilm.getValue().equals(null) && !listingsDatePicker.getValue().toString().equals(null)
					&& !comboChooseTime.getValue().equals(null)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			ScreensFramework.LOGGER.warning(e.getMessage());
			System.out.println(e);
			return false;
		}
	}

	// Toolbar methods

	@FXML
	public void goToStaffHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffHomeID);
	}

	@FXML
	private void goToAddFilmPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.addFilmPageID);
	}

	@FXML
	public void goToLogin(ActionEvent event) {
		// Unload the User:
		LoginController.USER.clearDetails();
		ScreensFramework.LOGGER.info("User logged out.");
		// Unload screens:
		myController.unloadScreen(ScreensFramework.loginID);
		myController.unloadScreen(ScreensFramework.staffHomeID);
		myController.unloadScreen(ScreensFramework.custHomeID);
		myController.unloadScreen(ScreensFramework.custProfilePageID);
		myController.unloadScreen(ScreensFramework.custBookFilmPageID);
		myController.unloadScreen(ScreensFramework.custConfirmPageID);
		myController.unloadScreen(ScreensFramework.staffExportID);
		myController.unloadScreen(ScreensFramework.bookingSummaryID);
		myController.unloadScreen(ScreensFramework.addFilmPageID);
		myController.unloadScreen(ScreensFramework.addFilmListingsID);

		myController.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);
		myController.setScreen(ScreensFramework.loginID);
	}

}
