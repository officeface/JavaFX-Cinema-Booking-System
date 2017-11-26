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
		this.txtEmail.setText(LoginController.USER.getEmail());
		this.txtFirstName.setText(LoginController.USER.getFirstName());
		this.txtLastName.setText(LoginController.USER.getLastName());
	}

	@Override
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
	private void update(ActionEvent event) throws JSONException, IOException {
		if (!this.txtEmail.getText().isEmpty() && !this.txtFirstName.getText().isEmpty()
				&& !this.txtLastName.getText().isEmpty()) {
			LoginController.USER.setEmail(this.txtEmail.getText());
			LoginController.USER.setFirstName(this.txtFirstName.getText());
			LoginController.USER.setLastName(this.txtLastName.getText());
			
			TextFileManager.updateUserDetails(LoginController.USER); // Update information in database
			
			this.lblUpdateStatus.setText("Updated details!");
		} else {
			this.lblUpdateStatus.setText("Field missing! Try again.");
		}
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

	@Override
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
