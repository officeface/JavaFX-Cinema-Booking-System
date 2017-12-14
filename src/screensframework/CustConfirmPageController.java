package screensframework;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CustConfirmPageController implements Initializable, ControlledScreen, CustToolbar {

	ScreensController myController;

	// Labels to change

	@FXML
	private Label lblTitle, lblDate, lblTime, lblNoOfSeats, lblOverallPrice, lblStatus;

	// Buttons

	@FXML
	private Button btnBack;

	@FXML
	private Button btnBook;

	// Toolbar butttons
	@FXML
	private Button btnLogout, btnHome, btnMyProfile, btnMyBookings;

	@FXML
	private Label lblCustFilmPage;

	/**
	 * Initialises the controller class.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Labels have been updated in the confirm page from the booking page
		this.lblTitle.setText(CustHomeController.BOOKING.getMovie().getTitle());
		this.lblDate.setText(CustHomeController.BOOKING.getMovie().getDate());
		this.lblTime.setText(CustHomeController.BOOKING.getMovie().getTime());
		Integer numberOfSeats = (Integer) CustHomeController.BOOKING.getSeats().size();
		this.lblNoOfSeats.setText(numberOfSeats.toString());
		this.lblOverallPrice.setText("£" + 5 * numberOfSeats + ".00");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	/**
	 * Tells the TextFileManager to update the database with the booking and sends
	 * the User back to the Homepage.
	 * 
	 * @param event Customer clicks "Confirm"
	 * @throws JSONException if the JSON database object cannot be found.
	 * @throws IOException if the database cannot be found.
	 */
	@FXML
	private void book(ActionEvent event) throws JSONException, IOException {
		TextFileManager.updateListing(CustHomeController.LISTING);
		TextFileManager.updateBookingHistory(CustHomeController.BOOKING);
		lblStatus.setText("Booked!");
		ScreensFramework.LOGGER.info("Film booked!");

		// Unload screens:
		myController.unloadScreen(ScreensFramework.custHomeID);
		myController.unloadScreen(ScreensFramework.custProfilePageID);
		myController.unloadScreen(ScreensFramework.custBookFilmPageID);
		myController.unloadScreen(ScreensFramework.custConfirmPageID);

		// Reload screens:
		myController.loadScreen(ScreensFramework.custHomeID, ScreensFramework.custHomeFile);
		myController.loadScreen(ScreensFramework.custProfilePageID, ScreensFramework.custProfilePageFile);
		myController.loadScreen(ScreensFramework.custBookingHistoryPageID, ScreensFramework.custBookingHistoryPageFile);

		// Return to Customer Home:
		myController.setScreen(ScreensFramework.custHomeID);
	}

	/**
	 * Sends the Customer back to the booking page.
	 * @param event the Customer clicks the "Back" button.
	 */
	@FXML
	public void goToCustBookFilmPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custBookFilmPageID);
	}

	// Toolbar methods

	/**
	 * {@inheritDoc}
	 */
	@FXML
	public void goToCustHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.custHomeID);
	}

	/**
	 * {@inheritDoc}
	 */
	@FXML
	public void goToCustProfilePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custProfilePageID);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@FXML
	public void goToCustBookingHistoryPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custBookingHistoryPageID);

	}
	
	/**
	 * {@inheritDoc}
	 */
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
