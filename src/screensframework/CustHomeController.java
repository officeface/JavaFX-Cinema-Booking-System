package screensframework;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 */
public class CustHomeController implements Initializable, ControlledScreen {

	@FXML
	private Button btnLogout, btnHome, btnMyProfile, btnMyBookings;

	ScreensController myController;
	
	private List<String> filmList;
	private List<String> timeList;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
//		// Populate timeList
//		for (int i = 0; i < 24; i++) {
//			if (i < 10) {
//				timeList.add("0" + i + ":00");
//			} else {
//				timeList.add(i + ":00");
//			}
//		}
//		
//		// Populate filmList
		
	}

	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	@FXML
	private void goToLogin(ActionEvent event) {
		myController.setScreen(ScreensFramework.loginID);
	}

	@FXML
	private void goToStaffChoicePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffChoiceID);
	}

	@FXML
	private void goToCustHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.custHomeID);
	}

	@FXML
	private void goToCustProfilePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custProfilePageID);
	}

}