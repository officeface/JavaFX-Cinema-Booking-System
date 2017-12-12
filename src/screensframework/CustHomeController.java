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

/**
 * FXML Controller class
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

		// Set the contents of the Accordion panes:
		int i = 0; // To iterate through Dates
		for (TitledPane day : accWeeklyFilms.getPanes()) {

			String date = dateTimeFormatter.format(LocalDate.now().plusDays(i));

			try {

				TextFileManager fileManager = new TextFileManager();
				List<String[]> filmTimes = fileManager.getFilmTimes();
				List<HBox> filmList = new ArrayList<HBox>();

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

						btn.setOnAction((ActionEvent e) -> {
							try {
								int PlaceHolderBookingID = 0;
								String title = listing[1];
								String time = listing[3];
								String listingID = Listing.findShowingID(title, date, time);
								// String[][] seats = TextFileManager.getSeatInformation(listingID);

								LISTING = new Listing(listingID, title, date, time, seats);
								BOOKING = new Booking(PlaceHolderBookingID, LISTING, null,
										(Customer) LoginController.USER);

								myController.loadScreen(ScreensFramework.custBookFilmPageID,
										ScreensFramework.custBookFilmPageFile);

								myController.setScreen(ScreensFramework.custBookFilmPageID);
							} catch (IOException e1) {
								System.out.println(e1);
							}

						});

						HBox container = new HBox(info, region1, seatsLeft, region, btn, region2);
						filmList.add(container);
					}
				}

				ObservableList<HBox> oFilmList = FXCollections.observableArrayList(filmList);

				ListView<HBox> filmsPlayingToday = new ListView<HBox>(oFilmList);

				day.setContent(filmsPlayingToday);
			} catch (IOException e) {
				e.printStackTrace();
			}

			i++;
		}
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

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

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
					System.out.println(e);
				}
			} else {
				lblStatus.setText("You cannot pick a date before today!");
			}
		} else {
			lblStatus.setText("You must select a value for every box!");
		}
	}

	private Boolean isValidListingSelection(String date, String movie, String time) throws ParseException {

		String timeNowString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
		date = dateTimeFormatter.format(selectDate.getValue());
		Date timeNow = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(timeNowString);
		Date timeListing = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date + " " + time);

		if (timeListing.before(timeNow)) {
			System.out.println("Can't book a film listed before today.");
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