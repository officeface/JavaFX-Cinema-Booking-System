package screensframework;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import objects.Customer;

/**
 * FXML Controller for the Registration page. Allows a User to register as a new
 * Customer, then sends them to the Customer homepage.
 * 
 * @author mark
 *
 */
public class RegistrationPageController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML
	private TextField txtEmail, txtFirstName, txtLastName;

	@FXML
	private PasswordField txtPassword, txtPasswordRepeat;

	@FXML
	private Label lblEmail, lblFirstName, lblLastName, lblPassword, lblPasswordRepeat, lblStatus;

	@FXML
	private Button btnBackToLogin, btnRegister;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	/**
	 * Generates a new Customer ID for the Customer.
	 * 
	 * @param number
	 *            Length of the customer database, as an integer.
	 * @return A 3-digit String representation of the customer's unique ID. e.g. if
	 *         they are the 5th customer, their ID will be "005". If they are the
	 *         99th customer, their ID will be "099". Limits the system to 1000
	 *         customers.
	 */
	private String generateCustID(int number) {

		if (number < 0 || number > 999) {
			ScreensFramework.LOGGER.warning("Customer ID outside of allowed number-range was attempted.");
			return null;
		} else if (number < 10) {
			return "00" + number;
		} else if (number < 100) {
			return "0" + number;
		} else {
			return "" + number;
		}
	}

	/**
	 * Checks the Registration conditions. If all these are met, creates a new
	 * Customer object and sends the Customer to the homepage.
	 * 
	 * @param event
	 *            "Register" button clicked.
	 * @throws IOException
	 *             the database file could not be found.
	 */
	@FXML
	private void register(ActionEvent event) throws IOException {

		int regCheck = 0;

		// Check email
		if (emailCheck(txtEmail.getText())) {
			regCheck++;
		}
		// Check password
		if (passwordCheck(txtPassword.getText())) {
			regCheck++;
		}
		// Check password repeat
		if (passwordRepeatCheck(txtPasswordRepeat.getText())) {
			regCheck++;
		}
		// Check first name
		if (firstNameCheck(txtFirstName.getText())) {
			regCheck++;
		}
		// Check last name
		if (lastNameCheck(txtLastName.getText())) {
			regCheck++;
		}

		if (regCheck == 5) {
			lblStatus.setText("Registration successful!");

			// Set up CustID:
			TextFileManager fileManager = new TextFileManager();
			List<String[]> loginDetails = fileManager.getLoginDetails();
			String custID = generateCustID(loginDetails.size() + 1);

			// Create new user object:
			LoginController.USER = new Customer(custID, txtEmail.getText(), txtPassword.getText(),
					txtFirstName.getText(), txtLastName.getText());

			// Add user to the database:
			TextFileManager.registerNewUser(LoginController.USER);

			// Load Customer home page:
			myController.loadScreen(ScreensFramework.custHomeID, ScreensFramework.custHomeFile);
			myController.loadScreen(ScreensFramework.custProfilePageID, ScreensFramework.custProfilePageFile);
			myController.loadScreen(ScreensFramework.custBookingHistoryPageID,
					ScreensFramework.custBookingHistoryPageFile);

			myController.setScreen(ScreensFramework.custHomeID);

		} else {
			lblStatus.setText("Registration failed.");
		}

	}

	/**
	 * Checks whether the email is currently in use.
	 * 
	 * @param email
	 *            the requested new email address
	 * @return True if the email is valid and not currently in use by another user.
	 * @throws IOException
	 */
	private Boolean emailCheck(String email) throws IOException {
		// Makes sure user types something here
		if (email.length() == 0) {
			lblEmail.setText("Type something!");
			return false;
		}

		TextFileManager fileManager = new TextFileManager();
		List<String[]> loginDetails = fileManager.getLoginDetails();

		for (int i = 0; i < loginDetails.size(); i++) {
			if (loginDetails.get(i)[1].equals(email)) {
				lblEmail.setText("Name already taken.");
				return false; // Username already taken
			}
		}

		lblEmail.setText("Username valid!");
		return true;
	}

	/**
	 * Checks whether the password contains acceptable characters. The acceptable
	 * values must be alphanumeric.
	 * 
	 * @param password
	 *            Password input.
	 * @return True if valid, alphanumeric password has been typed, false otherwise.
	 */
	private Boolean passwordCheck(String password) {

		if (password.length() == 0) {
			lblPassword.setText("Type something!");
			return false;
		}

		if (password.matches("[a-zA-Z0-9]+")) {
			lblPassword.setText("Password valid!");
			return true;
		} else {
			lblPassword.setText("Invalid password.");
			return false;
		}

	}

	/**
	 * Checks whether the second password entered matches the first.
	 * @param passwordRepeat
	 *            Repeated password
	 * @return Checks both passwords match. Returns true if they do and false if
	 *         they don't.
	 */
	private Boolean passwordRepeatCheck(String passwordRepeat) {

		if (passwordRepeat.length() == 0) {
			lblPasswordRepeat.setText("Type something!");
			return false;
		}

		if (passwordRepeat.equals(txtPassword.getText())) {
			lblPasswordRepeat.setText("Passwords match!");
			return true;
		} else {
			lblPasswordRepeat.setText("Passwords don't match.");
			return false;
		}
	}

	/**
	 * Checks whether the requested first name contains only letters. 
	 * @param name the name entered.
	 * @return true if the name just contains letters, false otherwise.
	 */
	private Boolean firstNameCheck(String name) {

		if (name.length() == 0) {
			lblFirstName.setText("Type something!");
			return false;
		}

		if (name.matches("[a-zA-Z]+")) {
			lblFirstName.setText("First name valid!");
			return true;
		} else {
			lblFirstName.setText("Invalid name.");
			return false;
		}
	}

	/**
	 * Checks whether the requested last name contains only letters. 
	 * @param name the name entered.
	 * @return true if the name just contains letters, false otherwise.
	 */
	private Boolean lastNameCheck(String name) {

		if (name.length() == 0) {
			lblLastName.setText("Type something!");
			return false;
		}

		if (name.matches("[a-zA-Z]+")) {
			lblLastName.setText("Last name valid!");
			return true;
		} else {
			lblLastName.setText("Invalid name.");
			return false;
		}
	}

	@FXML
	private void returnToLogin(ActionEvent event) {

		myController.setScreen(ScreensFramework.loginID);

	}

}
