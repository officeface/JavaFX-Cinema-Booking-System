package screensframework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Controller class for the Customer's film-booking page. This screen contains
 * the summary information for the film and gives the customer an interactive
 * seating display for the listing, allowing them to choose their seats.
 * 
 * @author Mark Backhouse
 *
 */
public class CustBookFilmPageController implements Initializable, ControlledScreen {

	ScreensController myController;

	// Customer selected summary

	@FXML
	private Label lblTimeSelected;

	@FXML
	private Label lblDateSelected;

	@FXML
	private Label lblFilmSelected;

	@FXML
	private Label lblDescription;

	@FXML
	private ImageView imgShowFilmImage;
	Image image;

	// Seating
	String[][] seats = CustHomeController.LISTING.getSeats();

	@FXML
	private GridPane seatLayout;

	@FXML
	private Button btnResetSeats;

	@FXML
	private ListView<String> seatSummary;
	private ObservableList<String> seatList = FXCollections.observableArrayList(); // Container for selected seats
	public HashSet<String> set = new HashSet<String>(); // Customer's selected seats

	// Subtotal and continue
	@FXML
	private Label lblSubtotal;

	@FXML
	private Button btnContinue;

	// Toolbar butttons
	@FXML
	private Button btnLogout, btnHome, btnMyProfile, btnMyBookings;

	@FXML
	private Label lblBookFilmPage;

	/**
	 * Initialises the controller class. Sets up the seating layout for the listing.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.lblFilmSelected.setText(CustHomeController.BOOKING.getMovie().getTitle());
		this.lblDateSelected.setText(CustHomeController.BOOKING.getMovie().getDate());
		this.lblTimeSelected.setText(CustHomeController.BOOKING.getMovie().getTime());
		this.lblSubtotal.setText("£0.00");

		// Bind the "Continue" button to the size of the seatList. This way, if a
		// customer has not selected a seat, they cannot click this button.
		btnContinue.disableProperty().bind(Bindings.size(seatList).isEqualTo(0));

		// Load image:
		try {
			this.lblDescription.setText(getDescription(CustHomeController.BOOKING.getMovie().getTitle()));

			String imagePath = getImage(CustHomeController.BOOKING.getMovie().getTitle());
			File file = new File(imagePath);
			if (imagePath.startsWith("/")) { // One of the built-in images, does not have an absolute filepath listed
				Image image = new Image(getClass().getResourceAsStream(imagePath));
				this.imgShowFilmImage.setImage(image);
			} else { // image has been added by Employee
				Image image = new Image(file.toURI().toString());
				this.imgShowFilmImage.setImage(image);
			}

		} catch (IOException e) {
			ScreensFramework.LOGGER.warning("Image URL could not be found.  Please update in Employee's side.");
		}

		// Generate the seats according to the listing information:
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 10; j++) {
				Button btn = new Button();
				Integer I = (Integer) i;
				Integer J = (Integer) j;
				seatLayout.add(btn, j, i);
				btn.setPrefSize(50, 28);
				btn.setId("Seat " + getSeatName(I, J));

				// Seats labels
				btn.setText(getSeatName(I, J));

				// Check if seat is available:
				if (seats[i][j].equals("Free")) {
					btn.setStyle("-fx-base: lightgreen;");
				} else {
					btn.setStyle("-fx-base: lightpink;");
				}

				// Set actions for when the Customer selects seats:
				btn.setOnAction(e -> {
					if (seats[I][J].equals("Free")) {
						btn.setStyle("-fx-base: deepskyblue;");
						seats[I][J] = LoginController.USER.getUserID();

						// Set seat summary using HashSet:
						seatSummary.getItems().clear();
						set.add(btn.getId());
						for (String values : set) {
							seatList.add(values);
						}
						seatSummary.setItems(seatList);
						lblSubtotal.setText("£" + 5 * seatList.size() + ".00");

					} else if (seats[I][J].equals(LoginController.USER.getUserID())
							&& btn.getStyle().equals("-fx-base: deepskyblue;")) {
						btn.setStyle("-fx-base: lightgreen;");
						seats[I][J] = "Free";

						// Set seat summary using HashSet:
						seatSummary.getItems().clear();
						set.remove(btn.getId());
						for (String values : set) {
							seatList.add(values);
						}
						seatSummary.setItems(seatList);
						lblSubtotal.setText("£" + 5 * seatList.size() + ".00");
					}
				});
			}
		}

	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	/**
	 * Reformats the simple seatnames provided by the database as 'nice' Strings
	 * that are more user-friendly for the Customer.
	 * 
	 * @param row
	 *            Seat's row number
	 * @param col
	 *            Seat's col number
	 * @return The seat's name as a nice string. e.g. if the seat is in row 0, col
	 *         5, the seat name will be "Seat A6" (rows/cols are zero-indexed)
	 */
	public String getSeatName(Integer row, Integer col) {

		char rowAnswer = (char) ('A' + row);

		Integer colShift = col + 1;
		String colAnswer = colShift.toString();

		return rowAnswer + colAnswer;
	}

	/**
	 * Progresses to the Confirmation page if the Customer has selected some seats.
	 * 
	 * @param event
	 *            Customer selects "Continue"
	 */
	@FXML
	public void goToCustConfirmPage(ActionEvent event) {

		if (seatList.size() > 0) { // Can only progress if seats have been selected

			List<String> custSeats = new ArrayList<String>();
			for (String i : seatList) {
				custSeats.add(i);
			}

			CustHomeController.BOOKING.setSeats(custSeats);
			CustHomeController.LISTING.setSeats(seats);

			myController.loadScreen(ScreensFramework.custConfirmPageID, ScreensFramework.custConfirmPageFile);
			myController.setScreen(ScreensFramework.custConfirmPageID);
		}
	}

	/**
	 * Finds the description for a specified movie.
	 * 
	 * @param title
	 *            the title of the movie.
	 * @return the description of the movie as a String.
	 * @throws IOException
	 *             if the database file cannot be found.
	 */
	public static String getDescription(String title) throws IOException {
		TextFileManager fileManager = new TextFileManager();
		List<String[]> filmList = fileManager.getFilmList();

		for (int i = 0; i < filmList.size(); i++) {
			if (filmList.get(i)[0].equals(title)) {
				return filmList.get(i)[2];
			}
		}
		return null;
	}

	/**
	 * Finds the image url for a specified movie.
	 * 
	 * @param title
	 *            the title of the movie.
	 * @return the image url of the movie as a String.
	 * @throws IOException
	 *             if the database file cannot be found.
	 */
	public static String getImage(String title) throws IOException {
		TextFileManager fileManager = new TextFileManager();
		List<String[]> filmList = fileManager.getFilmList();

		for (int i = 0; i < filmList.size(); i++) {
			if (filmList.get(i)[0].equals(title)) {
				return filmList.get(i)[1];
			}
		}
		return null;
	}

	// Toolbar methods

	@FXML
	public void goToCustHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.custHomeID);
	}

	@FXML
	public void goToCustProfilePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custProfilePageID);

	}

	@FXML
	public void goToCustBookingHistoryPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custBookingHistoryPageID);

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
