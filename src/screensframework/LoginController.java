package screensframework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 */
public class LoginController implements Initializable, ControlledScreen {

	ScreensController myController;
	public static User USER;

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
		try {
			TextFileManager fileManager = new TextFileManager();
			List<String[]> loginDetails = fileManager.getLoginDetails(); // Get login details as arraylist
			int successCheck = 0; // Success check

			for (int i = 0; i < loginDetails.size(); i++) {
				if ((loginDetails.get(i))[0].equals(txtEmail.getText())
						&& (loginDetails.get(i))[1].equals(txtPassword.getText())) {
					successCheck = 1;

					// Open Main scene upon successful customer login
					if (loginDetails.get(i)[2].equals("C")) {
						
						// Set details:
						USER = new Customer(txtEmail.getText(), txtPassword.getText(), "BOB", "SONOFBOB");
						
						// Set correct screens:
						myController.setScreen(ScreensFramework.custHomeID);
						
						
					} else if (loginDetails.get(i)[2].equals("S")) {
						
						// Set details:
//						user = new Employee();
//						user.setEmail(email);
//						user.setPassword(password);
//						user.setFirstName(firstName);
//						user.setLastName(lastName);	
						
						// Open Customer/Staff option screen upon successful staff login
						myController.setScreen(ScreensFramework.staffChoiceID);
					}
					continue;
				}
			}

			if (successCheck == 0) {
				lblStatus.setText("Login failed"); // If email/password pair not found
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}

	@FXML
	private void goToStaffChoicePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffChoiceID);
	}

	@FXML
	public void buttonPressed(KeyEvent ke) throws IOException {
		if (ke.getCode() == KeyCode.ENTER) {
			try {
				TextFileManager fileManager = new TextFileManager();
				List<String[]> loginDetails = fileManager.getLoginDetails(); // Get login details as arraylist
				int successCheck = 0; // Success check

				for (int i = 0; i < loginDetails.size(); i++) {
					if ((loginDetails.get(i))[0].equals(txtEmail.getText())
							&& (loginDetails.get(i))[1].equals(txtPassword.getText())) {
						successCheck = 1;

						// Open Main scene upon successful customer login
						if (loginDetails.get(i)[2].equals("C")) {
							// Set details:
							USER = new Customer(txtEmail.getText(), txtPassword.getText(), "BOB", "SONOFBOB");
							
							myController.loadScreen(ScreensFramework.custHomeID, ScreensFramework.custHomeFile);
					        myController.loadScreen(ScreensFramework.custProfilePageID, ScreensFramework.custProfilePageFile);
					        myController.loadScreen(ScreensFramework.custBookFilmPageID, ScreensFramework.custBookFilmPageFile);
					        myController.loadScreen(ScreensFramework.custConfirmPageID, ScreensFramework.custConfirmPageFile);
							
							myController.setScreen(ScreensFramework.custHomeID);
						} else if (loginDetails.get(i)[2].equals("S")) {
							// Set details:
							USER = new Employee(txtEmail.getText(), txtPassword.getText(), "BOB", "SONOFBOB");
							
							myController.loadScreen(ScreensFramework.staffChoiceID, ScreensFramework.staffChoiceFile);
					        myController.loadScreen(ScreensFramework.staffHomeID, ScreensFramework.staffHomeFile);
					        myController.loadScreen(ScreensFramework.staffExportID, ScreensFramework.staffExportFile);
					        myController.loadScreen(ScreensFramework.bookingSummaryID, ScreensFramework.bookingSummaryFile);
					        myController.loadScreen(ScreensFramework.addFilmPageID, ScreensFramework.addFilmPageFile);
					        myController.loadScreen(ScreensFramework.addFilmListingsID, ScreensFramework.addFilmListingsFile);
					        
							// Open Customer/Staff option screen upon successful staff login
							myController.setScreen(ScreensFramework.staffChoiceID);
						}
//						txtPassword.setText(""); // Password cleared after successful login and must be retyped after
//													// logging out
//						lblStatus.setText("");
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
	}
}