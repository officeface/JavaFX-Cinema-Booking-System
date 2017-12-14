package screensframework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import objects.Customer;
import objects.Employee;
import objects.User;

/**
 * FXML Controller class for the Login page. Allows a user to enter their login
 * details, determines whether or not they exist in the database and then sends
 * them the correct way based on their type (Customer or Employee).
 *
 */
public class LoginController implements Initializable, ControlledScreen {

	ScreensController myController;
	/**
	 * The USER object for the session. Can be instantiated as either an Employee or
	 * a Customer.
	 */
	public static User USER;

	@FXML
	private Label lblStatus; // Lets user know if their login was successful

	@FXML
	private TextField txtEmail; // Username

	@FXML
	private PasswordField txtPassword; // Password

	/**
	 * The database file, stored in the assets folder at the User's home directory.
	 */
	File database = new File(System.getProperty("user.home") + "/assets/", "database.json");
	/**
	 * The assets folder, stored in the User's home directory.
	 */
	File assets = new File(System.getProperty("user.home"), "assets");

	/**
	 * Initializes the controller class. Sets up the database file if it does not
	 * already exist.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		try {

			// Folder for images and database:
			if (assets.exists() && assets.isDirectory()) {
				ScreensFramework.LOGGER.info("Assets folder exists!");
			} else {
				new File(System.getProperty("user.home"), "assets").mkdir();
				ScreensFramework.LOGGER.info("Creating Assets folder in User home directory");
			}

			// Initialise the database JSON file:
			if (!database.exists()) {
				ScreensFramework.LOGGER.info("Database does not exist in assets folder, creating now.");

				InputStream in = getClass().getResourceAsStream("/database.json"); // database shell
				JSONObject obj = JSONUtils.getJSONObjectFromFile(in);
				FileWriter write = new FileWriter(database); // Add the assets-folder-database-contents into the newly
																// created database
				write.write(obj.toString());
				write.close();
			}

		} catch (IOException e) {
			ScreensFramework.LOGGER.warning(e.getMessage());
			System.out.println(e);
		}

	}
	
	/**
	 * Sets the screen parent.
	 */
	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	/**
	 * Checks user's login details against the database. If the user is of type
	 * "Customer", creates a new Customer object and initialises it with the
	 * database data. If the user is of type "Employee", creates a new Employee
	 * object and initialises it. The user is then sent to the correct follow-on
	 * screen depending on their permission level. Triggered by clicking Login.
	 * 
	 * @param event
	 *            the User clicks the "Login" button
	 * @throws IOException
	 *             if the database file cannot be found.
	 */
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
						myController.loadScreen(ScreensFramework.custBookingHistoryPageID,
								ScreensFramework.custBookingHistoryPageFile);

						myController.setScreen(ScreensFramework.custHomeID);

					} else if (loginDetails.get(i)[3].equals("S")) {
						String userID = loginDetails.get(i)[0];
						String email = loginDetails.get(i)[1];
						String password = loginDetails.get(i)[2];
						String firstName = loginDetails.get(i)[4];
						String lastName = loginDetails.get(i)[5];

						// Set details:
						USER = new Employee(userID, email, password, firstName, lastName);

						// Open staff home screen upon successful staff login
						myController.loadScreen(ScreensFramework.staffHomeID, ScreensFramework.staffHomeFile);
						myController.loadScreen(ScreensFramework.staffExportID, ScreensFramework.staffExportFile);
						myController.loadScreen(ScreensFramework.bookingSummaryID, ScreensFramework.bookingSummaryFile);
						myController.loadScreen(ScreensFramework.addFilmPageID, ScreensFramework.addFilmPageFile);
						myController.loadScreen(ScreensFramework.addFilmListingsID,
								ScreensFramework.addFilmListingsFile);

						// Open staff home upon successful staff login
						myController.setScreen(ScreensFramework.staffHomeID);
					}
					continue;
				}
			}

			if (successCheck == 0) {
				lblStatus.setText("Login failed"); // If email/password pair not found
			}
		} catch (FileNotFoundException e) {
			ScreensFramework.LOGGER.warning(e.getMessage());
			System.out.println(e);
		}
	}
	
	/**
	 * Sends the user to the staff home page.
	 * @param event user requests to go to the Staff home page
	 */
	@FXML
	private void goToStaffHomePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffHomeID);
	}

	/**
	 * Checks user's login details against the database. If the user is of type
	 * "Customer", creates a new Customer object and initialises it with the
	 * database data. If the user is of type "Employee", creates a new Employee
	 * object and initialises it. The user is then sent to the correct follow-on
	 * screen depending on their permission level. Triggered by pressing Enter.
	 * 
	 * @param ke
	 *            any time a key is pressed. Specifically listens for the "Enter"
	 *            key.
	 * @throws IOException
	 *             if the database file could not be found.
	 */
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
							myController.loadScreen(ScreensFramework.custBookingHistoryPageID,
									ScreensFramework.custBookingHistoryPageFile);

							myController.setScreen(ScreensFramework.custHomeID);
						} else if (loginDetails.get(i)[3].equals("S")) {
							String userID = loginDetails.get(i)[0];
							String email = loginDetails.get(i)[1];
							String password = loginDetails.get(i)[2];
							String firstName = loginDetails.get(i)[4];
							String lastName = loginDetails.get(i)[5];

							// Set details:
							USER = new Employee(userID, email, password, firstName, lastName);

							myController.loadScreen(ScreensFramework.staffHomeID, ScreensFramework.staffHomeFile);
							myController.loadScreen(ScreensFramework.staffExportID, ScreensFramework.staffExportFile);
							myController.loadScreen(ScreensFramework.bookingSummaryID,
									ScreensFramework.bookingSummaryFile);
							myController.loadScreen(ScreensFramework.addFilmPageID, ScreensFramework.addFilmPageFile);
							myController.loadScreen(ScreensFramework.addFilmListingsID,
									ScreensFramework.addFilmListingsFile);

							// Open staff home screen upon successful staff login
							myController.setScreen(ScreensFramework.staffHomeID);
						}
						continue;
					}
				}

				if (successCheck == 0) {
					lblStatus.setText("Login failed");
				}
			} catch (FileNotFoundException e) {
				ScreensFramework.LOGGER.warning(e.getMessage());
				System.out.println(e);
			}
		}
	}
	
	/**
	 * Sends the User to the registration page.
	 * @param event the User clicks the "Register" button.
	 */
	@FXML
	private void goToRegistrationPage(ActionEvent event) {
		myController.loadScreen(ScreensFramework.registrationID, ScreensFramework.registrationFile);
		myController.setScreen(ScreensFramework.registrationID);
	}
}