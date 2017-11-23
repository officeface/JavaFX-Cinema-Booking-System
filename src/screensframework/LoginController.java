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

	@Override
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
				if ((loginDetails.get(i))[1].equals(txtEmail.getText())
						&& (loginDetails.get(i))[2].equals(txtPassword.getText())) {
					successCheck = 1;

					// Open Main scene upon successful customer login
					if (loginDetails.get(i)[3].equals("C")) {
						String userID = loginDetails.get(i)[0];
						String email = loginDetails.get(i)[1];
						String password = loginDetails.get(i)[2];
						String firstName = loginDetails.get(i)[4];
						String lastName = loginDetails.get(i)[5];

						// Set details:
						USER = new Customer(userID, email, password, firstName, lastName);

						// Set correct screens:
						myController.loadScreen(ScreensFramework.custHomeID, ScreensFramework.custHomeFile);
						myController.loadScreen(ScreensFramework.custProfilePageID,
								ScreensFramework.custProfilePageFile);

						myController.setScreen(ScreensFramework.custHomeID);

					} else if (loginDetails.get(i)[3].equals("S")) {
						String userID = loginDetails.get(i)[0];
						String email = loginDetails.get(i)[1];
						String password = loginDetails.get(i)[2];
						String firstName = loginDetails.get(i)[4];
						String lastName = loginDetails.get(i)[5];

						// Set details:
						USER = new Employee(userID, email, password, firstName, lastName);

						// Open Customer/Staff option screen upon successful staff login
						myController.loadScreen(ScreensFramework.staffChoiceID, ScreensFramework.staffChoiceFile);
						myController.loadScreen(ScreensFramework.staffHomeID, ScreensFramework.staffHomeFile);
						myController.loadScreen(ScreensFramework.staffExportID, ScreensFramework.staffExportFile);
						myController.loadScreen(ScreensFramework.bookingSummaryID, ScreensFramework.bookingSummaryFile);
						myController.loadScreen(ScreensFramework.addFilmPageID, ScreensFramework.addFilmPageFile);
						myController.loadScreen(ScreensFramework.addFilmListingsID,
								ScreensFramework.addFilmListingsFile);

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
					if ((loginDetails.get(i))[1].equals(txtEmail.getText())
							&& (loginDetails.get(i))[2].equals(txtPassword.getText())) {
						successCheck = 1;

						// Open Main scene upon successful customer login
						if (loginDetails.get(i)[3].equals("C")) {
							String userID = loginDetails.get(i)[0];
							String email = loginDetails.get(i)[1];
							String password = loginDetails.get(i)[2];
							String firstName = loginDetails.get(i)[4];
							String lastName = loginDetails.get(i)[5];

							// Set details:
							USER = new Customer(userID, email, password, firstName, lastName);

							myController.loadScreen(ScreensFramework.custHomeID, ScreensFramework.custHomeFile);
							myController.loadScreen(ScreensFramework.custProfilePageID,
									ScreensFramework.custProfilePageFile);

							myController.setScreen(ScreensFramework.custHomeID);
						} else if (loginDetails.get(i)[3].equals("S")) {
							String userID = loginDetails.get(i)[0];
							String email = loginDetails.get(i)[1];
							String password = loginDetails.get(i)[2];
							String firstName = loginDetails.get(i)[4];
							String lastName = loginDetails.get(i)[5];

							// Set details:
							USER = new Employee(userID, email, password, firstName, lastName);

							myController.loadScreen(ScreensFramework.staffChoiceID, ScreensFramework.staffChoiceFile);
							myController.loadScreen(ScreensFramework.staffHomeID, ScreensFramework.staffHomeFile);
							myController.loadScreen(ScreensFramework.staffExportID, ScreensFramework.staffExportFile);
							myController.loadScreen(ScreensFramework.bookingSummaryID,
									ScreensFramework.bookingSummaryFile);
							myController.loadScreen(ScreensFramework.addFilmPageID, ScreensFramework.addFilmPageFile);
							myController.loadScreen(ScreensFramework.addFilmListingsID,
									ScreensFramework.addFilmListingsFile);

							// Open Customer/Staff option screen upon successful staff login
							myController.setScreen(ScreensFramework.staffChoiceID);
						}
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