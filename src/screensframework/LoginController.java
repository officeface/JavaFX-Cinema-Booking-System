package screensframework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 */
public class LoginController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML
	private Label lblStatus; // Lets user know if their login was successful

	@FXML
	private TextField txtEmail; // Username

	@FXML
	private PasswordField txtPassword; // Password

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}


	@FXML
	private void login(ActionEvent event) throws IOException {
		// myController.setScreen(ScreensFramework.screen2ID);
		try {
			TextFileManager fileManager = new TextFileManager();
			List<String[]> loginDetails = fileManager.getLoginDetails(); // Get login details as arraylist
			int successCheck = 0; // Success check

			for (int i = 0; i < loginDetails.size(); i++) {
				if ((loginDetails.get(i))[0].equals(txtEmail.getText())
						&& (loginDetails.get(i))[1].equals(txtPassword.getText())) {
					//lblStatus.setText("Login successful");
					successCheck = 1;

					// Open Main scene upon successful customer login
					if (loginDetails.get(i)[2].equals("C")) {
						myController.setScreen(ScreensFramework.custHomeID);
					} else if (loginDetails.get(i)[2].equals("S")) {
						// Open Customer/Staff option screen upon successful staff login
						myController.setScreen(ScreensFramework.staffChoiceID);
					}
					txtPassword.setText(""); // Password cleared after successful login and must be retyped after logging out
					continue;
				}
			}

			if (successCheck == 0) {
				lblStatus.setText("Login failed");
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}

	@FXML
	private void goToStaffChoicePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffChoiceID);
	}
}