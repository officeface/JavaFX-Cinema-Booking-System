package screensframework;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

/**
 * FXML Controller class
 *
 */
public class CustHomeController extends ToolbarController implements Initializable, ControlledScreen {

	@FXML
	private Label lblHomePage;

	@FXML
	private Button btnLogout, btnHome, btnMyProfile, btnMyBookings;

	@FXML
	private TitledPane day1Label, day2Label, day3Label, day4Label, day5Label, day6Label, day7Label;

	@FXML
	private ComboBox<String> selectFilm;

	@FXML
	private ComboBox<String> selectTime;

	@FXML
	private DatePicker selectDate;

	ScreensController myController;

	private ObservableList<String> filmNames = FXCollections.observableArrayList(); // Container for film titles
	private ObservableList<String> timeList = FXCollections.observableArrayList(); // Container for film times
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formatting dates

	public static Booking BOOKING; // Customer's booking

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// Set the values on the labels
		day1Label.setText("Today");
		day2Label.setText("Tomorrow");
		day3Label.setText(dateTimeFormatter.format(LocalDate.now().plusDays(2)));
		day4Label.setText(dateTimeFormatter.format(LocalDate.now().plusDays(3)));
		day5Label.setText(dateTimeFormatter.format(LocalDate.now().plusDays(4)));
		day6Label.setText(dateTimeFormatter.format(LocalDate.now().plusDays(5)));
		day7Label.setText(dateTimeFormatter.format(LocalDate.now().plusDays(6)));
	}

	/**
	 * 
	 * @return A list of films that are playing on a specified date.
	 * @throws IOException
	 */
	public List<String> getFilmList() throws IOException {
		try {
			String date = dateTimeFormatter.format(selectDate.getValue());
			return TextFileManager.filmsFilteredByDate(date);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 
	 * @return A list of times for a given date and film.
	 * @throws IOException
	 */
	public List<String> getTimesList() throws IOException {
		try {
			String date = dateTimeFormatter.format(selectDate.getValue());
			String film = selectFilm.getValue();
			return TextFileManager.timesFilteredByDateAndFilm(date, film);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Sets the films list after a date has been selected. Films are those that will
	 * be showing on the specified date.
	 * 
	 * @param event
	 *            date selection from DatePicker box
	 * @throws IOException
	 */
	@FXML
	private void setFilmList(ActionEvent event) throws IOException {
		selectFilm.getItems().clear();
		// Populate film list
		try {
			List<String> filmList = getFilmList();
			for (int i = 0; i < filmList.size(); i++) {
				filmNames.add(filmList.get(i));
			}
			selectFilm.setItems(filmNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void setTimesList(ActionEvent event) throws IOException {
		selectTime.getItems().clear();
		// Populate times list
		try {
			List<String> times = getTimesList();
			for (int i = 0; i < times.size(); i++) {
				timeList.add(times.get(i));
			}
			selectTime.setItems(timeList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	@FXML
	public void goToCustBookFilmPage(ActionEvent event) {
		try {
			int PlaceHolderBookingID = 0;
			Seat SeatNotPickedYet = new Seat(1000, 1000, 1000);

			Listing listing = new Listing("Placeholder", this.selectFilm.getValue(),
					dateTimeFormatter.format(selectDate.getValue()), this.selectTime.getValue());
			BOOKING = new Booking(PlaceHolderBookingID, listing, SeatNotPickedYet, (Customer) LoginController.USER);
			
			myController.loadScreen(ScreensFramework.custBookFilmPageID, ScreensFramework.custBookFilmPageFile);
	        myController.loadScreen(ScreensFramework.custConfirmPageID, ScreensFramework.custConfirmPageFile);

			myController.setScreen(ScreensFramework.custBookFilmPageID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void goToStaffChoicePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffChoiceID);
	}

	@FXML
	public void goToCustHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.custHomeID);
	}

	@FXML
	public void goToCustProfilePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custProfilePageID);

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