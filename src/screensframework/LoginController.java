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

//	private static List<String[]> loginDetails() throws IOException {
//		List<String> list = new ArrayList<String>();
//		List<String[]> loginDetails = new ArrayList<String[]>();
//		String line = null;
//
//		File file = new File("\\ScreensFramework\\LoginDetails.txt");
//		FileReader fileReader = new FileReader(file.getName());
//		BufferedReader lineReader = new BufferedReader(fileReader);
//
//		// Extract all lines from .txt file
//		while ((line = lineReader.readLine()) != null) {
//			list.add(line);
//		}
//
//		// Add emails/passwords/SorC to separate columns in arraylist
//		for (String user : list) {
//			loginDetails.add(user.split("\\s+"));
//		}
//		loginDetails.remove(0); // Remove first line of .txt file (not relevant)
//
//		lineReader.close();
//		return loginDetails;
//	}

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
					lblStatus.setText("Login successful");
					successCheck = 1;

					// Open Main scene upon successful customer login
					if (loginDetails.get(i)[2].equals("C")) {
						myController.setScreen(ScreensFramework.custHomeID);
					} else if (loginDetails.get(i)[2].equals("S")) {
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

	@FXML
	private void goToStaffChoicePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffChoiceID);
	}
}