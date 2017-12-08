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
	private String selectedfilmforlisting; // Holds selected Film

	@FXML
	private ComboBox<String> comboChooseTime; // Select appropriate times
	private ObservableList<String> TimesAvailable = FXCollections.observableArrayList(); // Container
																							// for
																							// timings
	private String selectedTimeforlisting;

	@FXML
	private DatePicker listingsDatepicker; // Select a date
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formatting
																								// dates
	private String selecteddateforlisting;// Holds selected date

	@FXML
	private Button btnAddListings; // Once form complete - press button to add
									// to records

	@FXML
	private Label lblFeedback;

	// Toolbar variables
	@FXML
	private Button btnLogout;

	@FXML
	private Button btnHome;

	@FXML
	private Button btnGoToNewFilm;

	/**
	 * Initialises the controller class.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		List<String[]> filmList = null;
		try {
			filmList = TextFileManager.getFilmTitles();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < filmList.size(); i++) {
			filmNames.addAll(filmList.get(i));
		}
		comboSelectFilm.setItems(filmNames);

		String[] timeList = null;
		try {
			timeList = TextFileManager.getFilmTimings();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < timeList.length; i++) {
			TimesAvailable.add(timeList[i]);
		}
		comboChooseTime.setItems(TimesAvailable);

	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	@FXML
	private void getSelectedFilm(ActionEvent event) throws IOException {
		this.selectedfilmforlisting = comboSelectFilm.getValue();
		System.out.println(selectedfilmforlisting);
	}

	@FXML
	private void getSelectedDate(ActionEvent event) throws IOException {
		this.selecteddateforlisting = dateTimeFormatter.format(listingsDatepicker.getValue());
		System.out.println(selecteddateforlisting);
	}

	@FXML
	private void getSelectedTime(ActionEvent event) throws IOException {
		this.selectedTimeforlisting = comboChooseTime.getValue();
		System.out.println(selectedTimeforlisting);
	}

	@FXML
	private void addfilmListing(ActionEvent event) throws IOException {

		if (readyToSelect() && showingTimeIsFree(this.selecteddateforlisting, this.selectedTimeforlisting)) {

			// New seating ID
			JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
			JSONArray jsonArray = obj.getJSONArray("FilmTimes");
			int filmtimeslength = jsonArray.length();
			Integer newshowingIDnumber = filmtimeslength + 1;
			String newshowingID = newshowingIDnumber.toString();

			Listing newlisting = new Listing(newshowingID, this.selectedfilmforlisting, this.selecteddateforlisting,
					this.selectedTimeforlisting, null);

			TextFileManager.addFilmListings(newlisting); // Update film listing
															// in database

			this.lblFeedback.setText("Added Film Listing!");
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
				return false;
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
			if (!comboSelectFilm.getValue().equals(null) && !listingsDatepicker.getValue().toString().equals(null)
					&& !comboChooseTime.getValue().equals(null)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
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
		myController.unloadScreen(ScreensFramework.staffChoiceID);

		myController.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);
		myController.setScreen(ScreensFramework.loginID);
	}

}
