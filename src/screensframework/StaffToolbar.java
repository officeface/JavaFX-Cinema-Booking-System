package screensframework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Contains the abstract navigation methods for the Staff-side toolbar
 * interaction.
 * 
 * @author Mark Backhouse
 *
 */
public interface StaffToolbar {
	
	/**
	 * Logs the Employee out of the application.
	 * @param event the Employee clicks the "Logout" button.
	 */
	@FXML
	public void goToLogin(ActionEvent event);

	/**
	 * Returns the Employee to the homepage.
	 * @param event the Employee clicks the "Home" button.
	 */
	@FXML
	public void goToStaffHome(ActionEvent event);
	
	/**
	 * Sends the Employee to the Add Film Page.
	 * @param event the Employee clicks "Add Film" button.
	 */
	@FXML
	public void goToAddFilmPage(ActionEvent event);
	
	/**
	 * Sends the Employee to the Add Listings Page.
	 * @param event the Employee clicks "Add Listings" button.
	 */
	@FXML
	public void goToAddListings(ActionEvent event);
	
	/**
	 * Sends the Employee to the Staff Export page.
	 * @param event the Employee clicks the "Staff Export" button.
	 */
	@FXML
	public void goToStaffExport(ActionEvent event);
	
	/**
	 * Sends the Employee to the Booking Summary page.
	 * @param event the Employee clicks the "Booking Summary" button.
	 */
	@FXML
	public void goToBookingSummary(ActionEvent event);
}
