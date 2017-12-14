package screensframework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Contains the abstract navigation methods for the Customer-side toolbar
 * interaction.
 * 
 * @author Mark Backhouse
 *
 */
public interface CustToolbar {

	/**
	 * Logs the Customer out of the application.
	 * @param event the Customer clicks the "Logout" button.
	 */
	@FXML
	public void goToLogin(ActionEvent event);
	
	/**
	 * Returns the Customer to the Homepage
 	 * @param event the Customer clicks the "Home" button.
	 */
	@FXML
	public void goToCustHome(ActionEvent event);

	/**
	 * Sends the Customer to their profile page.
	 * @param event the Customer clicks the "Profile" button.
	 */
	@FXML
	public void goToCustProfilePage(ActionEvent event);

	/**
	 * Sends the Customer to their booking history page.
	 * @param event the Customer clicks the "Booking History" button.
	 */
	@FXML
	public void goToCustBookingHistoryPage(ActionEvent event);
	
}
