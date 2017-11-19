package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CustProfilePageController extends ToolbarController implements Initializable, ControlledScreen {

	@FXML
	private Button btnLogout, btnHome, btnMyProfile, btnMyBookings, btnUpdate;

	@FXML
	public TextField txtEmail, txtFirstName, txtLastName;

	@FXML
	private Label lblUpdateStatus, lblFetchDetails;

	ScreensController myController;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
	
	@FXML
	private void getDetails(ActionEvent event) {
		try {
			this.txtEmail.setText(LoginController.USER.getEmail());
			this.txtFirstName.setText(LoginController.USER.getFirstName());
			this.txtLastName.setText(LoginController.USER.getLastName());
		} catch (Exception e) {
			System.out.println(e);
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
		myController.unloadScreen(ScreensFramework.staffExportID);
		myController.unloadScreen(ScreensFramework.bookingSummaryID);
		myController.unloadScreen(ScreensFramework.addFilmPageID);
		myController.unloadScreen(ScreensFramework.addFilmListingsID);
		myController.unloadScreen(ScreensFramework.staffChoiceID);

		myController.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);
		myController.loadScreen(ScreensFramework.staffHomeID, ScreensFramework.staffHomeFile);
		myController.loadScreen(ScreensFramework.staffChoiceID, ScreensFramework.staffChoiceFile);
		myController.loadScreen(ScreensFramework.custHomeID, ScreensFramework.custHomeFile);
		myController.loadScreen(ScreensFramework.custProfilePageID, ScreensFramework.custProfilePageFile);

		// for staff screens Excluding: staff home
		myController.loadScreen(ScreensFramework.staffExportID, ScreensFramework.staffExportFile);
		myController.loadScreen(ScreensFramework.bookingSummaryID, ScreensFramework.bookingSummaryFile);
		myController.loadScreen(ScreensFramework.addFilmPageID, ScreensFramework.addFilmPageFile);
		myController.loadScreen(ScreensFramework.addFilmListingsID, ScreensFramework.addFilmListingsFile);
		myController.setScreen(ScreensFramework.loginID);
	}

}
