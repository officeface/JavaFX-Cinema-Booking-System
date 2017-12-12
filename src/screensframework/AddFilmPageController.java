package screensframework;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.json.JSONException;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import objects.Movie;;

/**
 * FXML Controller class
 *
 */

public class AddFilmPageController implements Initializable, ControlledScreen {

	ScreensController myController;

	@FXML
	private TextField txtNameFilm; // Type in the name of new film

	@FXML
	private Button btnSelectImg; // Upload a new promotional image (png)

	@FXML
	private TextArea txtAreaFilmDescription; // Type in description of the film

	@FXML
	private Button btnAddFilm; // After form complete, when pressed adds to records

	@FXML
	private Label lblAddFilmChecker; // Tells user if the updating is successful!

	@FXML
	private ImageView imgViewFilmImage; // Image of the movie

	private String imagePathway; // Variable that stores the image directory

	// Toolbar variables
	@FXML
	private Button btnSLogout;

	@FXML
	private Button btnSHome;

	@FXML
	private Button btnGoToNewListing;

	// Image destination file:
	File currentDir = new File(".");
	File parentDir = currentDir.getAbsoluteFile().getParentFile();
	File assets = new File(parentDir, "assets");

	/**
	 * Initialises the controller class.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	/**
	 * Opens up a window to choose an image. Specific files ending in jpeg or png
	 * can only be chosen. The pathway of the image is then stored - to then be
	 * added to the database if the user executes the add film method.
	 * 
	 * @author frazahmad
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void uploadImg(ActionEvent event) throws IOException {
		if (btnSelectImg.isArmed()) {

			FileChooser fileChooser = new FileChooser(); // File chooser initialised

			// Set extension filters
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(null);

			if (file != null) { // Cancel the following if User clicks 'cancel'

				try {
					BufferedImage bufferedImage = ImageIO.read(file); // Used to select images
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					imgViewFilmImage.setImage(image); // Sets selected image to the image view

					String imagePathway = file.getAbsolutePath(); // Returns the pathway of an image that has been
																	// selected
					this.imagePathway = imagePathway;

				} catch (IOException ex) {
					System.out.println("Could not upload image");
				}

				this.lblAddFilmChecker.setText("Uploaded Image!");
			}

		} else {
			this.lblAddFilmChecker.setText("Try again!");
		}
	}

	/**
	 * Allows the user to add a film to the database. A new movie class is
	 * initialised and transferred to addFilmDetails in the textfilemanager.
	 * Prevents against empty fields being added to the database.
	 * 
	 * @author frazahmad
	 * @param event
	 * @throws JSONException
	 * @throws IOException
	 */
	@FXML
	private void addFilm(ActionEvent event) throws JSONException, IOException {

		// Checks if the fields are not empty
		if (!this.txtNameFilm.getText().isEmpty() && !this.txtAreaFilmDescription.getText().isEmpty()) {

			// New movie class initialised
			Movie newMovie = new Movie(this.txtNameFilm.getText(), imagePathway, this.txtAreaFilmDescription.getText());
			TextFileManager.addFilmDetails(newMovie); // Update information in database

			this.lblAddFilmChecker.setText("Added Film!");

			// Reset labels and return to Staff Home:
			myController.unloadScreen(ScreensFramework.addFilmPageID);
			myController.unloadScreen(ScreensFramework.addFilmListingsID);
			myController.unloadScreen(ScreensFramework.staffExportID);
			myController.unloadScreen(ScreensFramework.bookingSummaryID);

			myController.loadScreen(ScreensFramework.staffExportID, ScreensFramework.staffExportFile);
			myController.loadScreen(ScreensFramework.bookingSummaryID, ScreensFramework.bookingSummaryFile);
			myController.loadScreen(ScreensFramework.addFilmPageID, ScreensFramework.addFilmPageFile);
			myController.loadScreen(ScreensFramework.addFilmListingsID, ScreensFramework.addFilmListingsFile);

			// Open staff home upon successful staff login
			myController.setScreen(ScreensFramework.staffHomeID);
		} else {
			this.lblAddFilmChecker.setText("Field missing! Try again.");
		}
	}

	// Toolbar methods

	@FXML
	public void goToStaffHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffHomeID);
	}

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

		myController.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);
		myController.setScreen(ScreensFramework.loginID);
	}

	@FXML
	private void goToAddListings(ActionEvent event) {

		// Unloads and reloads to update the page in case a new film was presently added
		myController.unloadScreen(ScreensFramework.addFilmListingsID);
		myController.loadScreen(ScreensFramework.addFilmListingsID, ScreensFramework.addFilmListingsFile);
		myController.setScreen(ScreensFramework.addFilmListingsID);
	}

}
