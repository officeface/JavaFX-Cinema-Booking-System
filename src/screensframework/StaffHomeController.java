package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class for the Staff Homepage.
 * 
 * This page provides an overview and portal to the cinema's employee functionalities.
 * Through this page, the employee may enter: add film, add listing, booking summary
 * and export film details pages. The staff toolbar methods are also functional. 
 * 
 * @author Fraz Ahmad
 *
 */
public class StaffHomeController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML
	private Button btnAddFilm; // go to add film PAGE

	@FXML
	private Button btnAddListings; // go to add film LISTINGS

	@FXML
	private Button btnBookingSummary; // go to BOOKING SUMMARY

	@FXML
	private Button btnExportFilms; // go to EXPORT FILMS

	// INITILISE TOOLBAR BUTTONS
	@FXML
	private Button btnSLogout;

	@FXML
	private Button btnSHome; // not in use as in the home page already

	/**
	 * Initialises the controller class.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	// METHODS
	
	/**
	 * Redirects employee to add film page once button is pressed
	 * @author Fraz Ahmad
	 * @param event
	 */
	@FXML
	private void goToAddFilmPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.addFilmPageID);
	}

	
	/**
	 * Redirects employee to add listing page once button is pressed
	 * @author Fraz Ahmad
	 * @param event
	 */
	@FXML
	private void goToAddListings(ActionEvent event) {
		myController.setScreen(ScreensFramework.addFilmListingsID);
	}


	/**
	 * Redirects employee to booking summary page once button is pressed
	 * @author Fraz Ahmad
	 * @param event
	 */
	@FXML
	private void goToBookingSummary(ActionEvent event) {
		myController.setScreen(ScreensFramework.bookingSummaryID);
	}


	/**
	 * Redirects employee to staff export once button is pressed
	 * @author Fraz Ahmad
	 * @param event
	 */
	@FXML
	private void goToStaffExport(ActionEvent event) {
		myController.unloadScreen(ScreensFramework.staffExportID);
		myController.loadScreen(ScreensFramework.staffExportID, ScreensFramework.staffExportFile);
		myController.setScreen(ScreensFramework.staffExportID);
	}

	// TOOLBAR ACTIONS

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
		myController.unloadScreen(ScreensFramework.staffChoiceID);

		myController.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);
		myController.setScreen(ScreensFramework.loginID);
	}

}
