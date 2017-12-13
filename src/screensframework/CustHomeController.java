package screensframework;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import objects.Booking;
import objects.Customer;
import objects.Listing;

/**
 * FXML Controller class for the Customer Homepage. This page contains selectors
 * that allow the Customer to see all movies playing on a certain date, and then
 * all times that the specified movie is playing on this date. Further, it
 * provides a weekly snapshot of movies, so that the Customer can speedily
 * select a film to watch that is playing over the next few days.
 * 
 * @author Mark Backhouse
 *
 */
public class CustHomeController extends ToolbarController implements Initializable, ControlledScreen {

	@FXML
	private Label lblHomePage, lblStatus;

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

	@FXML
	private Accordion accWeeklyFilms;

	ScreensController myController;

	private ObservableList<String> filmNames = FXCollections.observableArrayList(); // Container for film titles
	private ObservableList<String> timeList = FXCollections.observableArrayList(); // Container for film times
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formatting dates

	public static Booking BOOKING; // Customer's booking
	public static Listing LISTING; // Film listing

	/**
	 * Initializes the controller class. The "films showing this week" accordion is
	 * initialised using database information.
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

		// Set the contents of the Accordion panes:
		int i = 0; // To iterate through Dates
		for (TitledPane day : accWeeklyFilms.getPanes()) {

			String date = dateTimeFormatter.format(LocalDate.now().plusDays(i));

			try { // If the database file can be found

				TextFileManager fileManager = new TextFileManager();
				List<String[]> filmTimes = fileManager.getFilmTimes();
				List<HBox> filmList = new ArrayList<HBox>();

				// Find any listing that is playing on the titlepane's date:
				for (String[] listing : filmTimes) {
					if (listing[2].equals(date)) {
						// Labels, buttons:
						Label info = new Label(listing[1]);
						Label seatsLeft = new Label(listing[0]);
						Button btn = new Button(listing[3]);
						btn.setStyle("-fx-background-color: rgb(85,209,255); -fx-text-fill: white; "); // Sets the style
																										// of the
																										// buttons

						// Two events handlers if the user hovers over the buttons
						btn.setOnMouseExited(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent t) {
								btn.setStyle("-fx-background-color: rgb(85,209,255); -fx-text-fill: white;");
							}
						});

						btn.setOnMouseEntered(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent t) {
								btn.setStyle("-fx-background-color: rgb(0,144,254); -fx-text-fill: white;");
							}
						});

						// For spacing:
						Region region = new Region();
						region.setPrefWidth(50);
						Region region1 = new Region();
						HBox.setHgrow(region1, Priority.ALWAYS);
						Region region2 = new Region();
						region2.setPrefWidth(100);

						// Seat information for listing on display:
						int freeSeatCount = 0;
						String[][] seats = TextFileManager.getSeatInformation(listing[0]);
						for (String[] row : seats) {
							for (String seat : row) {
								if (seat.equals("Free")) {
									freeSeatCount++;
								}
							}
						}
						seatsLeft.setText(freeSeatCount + " seats left.");

						// Take the customer to the book-film page for this listing if they click on the
						// listing:
						btn.setOnAction((ActionEvent e) -> {
							try {
								int PlaceHolderBookingID = 0;
								String title = listing[1];
								String time = listing[3];
								String listingID = Listing.findShowingID(title, date, time);

								LISTING = new Listing(listingID, title, date, time, seats);
								BOOKING = new Booking(PlaceHolderBookingID, LISTING, null,
										(Customer) LoginController.USER);

								myController.loadScreen(ScreensFramework.custBookFilmPageID,
										ScreensFramework.custBookFilmPageFile);

								myController.setScreen(ScreensFramework.custBookFilmPageID);
							} catch (IOException e1) { // the database file could not be found
								ScreensFramework.LOGGER.warning(e1.getMessage());
							}

						});

						HBox container = new HBox(info, region1, seatsLeft, region, btn, region2);
						filmList.add(container);
					}
				}

				ObservableList<HBox> oFilmList = FXCollections.observableArrayList(filmList);

				ListView<HBox> filmsPlayingToday = new ListView<HBox>(oFilmList);

				// Add the content to the TitledPane:
				day.setContent(filmsPlayingToday);

			} catch (IOException e) { // the database file could not be found.
				ScreensFramework.LOGGER.warning(e.getMessage());
			}

			i++; // Go to the next day
		}
	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	/**
	 * Uses the date in the DatePicker box and returns the associated film list.
	 * 
	 * @return the list of films that are playing on a specified date.
	 * @throws IOException
	 *             if the database file cannot be found.
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
	 * Uses the DatePicker and film values to find the associated list of available
	 * times.
	 * 
	 * @return A list of times for a given date and film.
	 * @throws IOException
	 *             if the database file cannot be found.
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
	 *            the date has been selected.
	 * @throws IOException
	 *             if the database file cannot be found.
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

	/**
	 * Sets the times list after a date and film have been selected. Times are those
	 * that will be showing for the specified date and film.
	 * 
	 * @param event
	 *            the film and date have been selected.
	 * @throws IOException
	 *             if the database file cannot be found.
	 */
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

	/**
	 * Sends the Customer, along with User, Listing and Booking objects, to the
	 * Booking page.
	 * 
	 * @param event
	 *            the Customer clicks the "GO!" button after a selection has been
	 *            made.
	 * @throws ParseException
	 *             if the date value cannot be parsed to the database's format.
	 */
	@FXML
	public void goToCustBookFilmPage(ActionEvent event) throws ParseException {
		if (selectDate.getValue() != null && !selectFilm.getSelectionModel().isEmpty()
				&& !selectTime.getSelectionModel().isEmpty()) { // If selections have been made
			if (isValidListingSelection(selectDate.getValue().toString(), selectFilm.getValue(),
					selectTime.getValue())) { // If the selected date is in the future
				try {

					int PlaceHolderBookingID = 0;
					String title = this.selectFilm.getValue();
					String date = dateTimeFormatter.format(selectDate.getValue());
					String time = this.selectTime.getValue();
					String listingID = Listing.findShowingID(title, date, time);
					String[][] seats = TextFileManager.getSeatInformation(listingID);

					LISTING = new Listing(listingID, title, date, time, seats);
					BOOKING = new Booking(PlaceHolderBookingID, LISTING, null, (Customer) LoginController.USER);

					myController.loadScreen(ScreensFramework.custBookFilmPageID, ScreensFramework.custBookFilmPageFile);

					myController.setScreen(ScreensFramework.custBookFilmPageID);

				} catch (Exception e) {
					ScreensFramework.LOGGER.warning(e.getMessage());
				}
			} else {
				ScreensFramework.LOGGER.info("Customer picked an out-of-date screening.");
				lblStatus.setText("You cannot pick a date before today!");
			}
		} else {
			lblStatus.setText("You must select a value for every box!");
		}
	}

	/**
	 * Checks whether a selected listing was valid. Listings are only valid if they
	 * have not yet taken place.
	 * 
	 * @param date
	 *            the date of the listing.
	 * @param movie
	 *            the title of the listing.
	 * @param time
	 *            the time of the listing.
	 * @return true if the listing is taking place in the future, false otherwise.
	 * @throws ParseException
	 *             if the input date cannot be converted into a format recognised by
	 *             the database.
	 */
	private Boolean isValidListingSelection(String date, String movie, String time) throws ParseException {

		String timeNowString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
		date = dateTimeFormatter.format(selectDate.getValue());
		Date timeNow = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(timeNowString);
		Date timeListing = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + time);

		if (timeListing.before(timeNow)) {
			ScreensFramework.LOGGER.warning("Customer tried to book a film listed before today's date.");
			return false;
		}

		return true;
	}

	@Override
	@FXML
	public void goToStaffChoicePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffChoiceID);
	}

	@Override
	@FXML
	public void goToCustHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.custHomeID);
	}

	@Override
	@FXML
	public void goToCustProfilePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custProfilePageID);

	}

	@FXML
	public void goToCustBookingHistoryPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custBookingHistoryPageID);

	}

	@Override
	@FXML
	public void goToLogin(ActionEvent event) {
		// Unload the User:
		LoginController.USER.clearDetails();
		ScreensFramework.LOGGER.info("User logged out.");
		// Unload screens:
		myController.unloadScreen(ScreensFramework.loginID);
		myController.unloadScreen(ScreensFramework.registrationID);
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