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
import javafx.stage.FileChooser;;


/**
 * FXML Controller class
 *
 */

public class AddFilmPageController implements Initializable, ControlledScreen {

	ScreensController myController; 
    
	@FXML
	private TextField txtNameFilm; //Type in the name of new film 
	
	@FXML
	private Button btnSelectImg; //Upload a new promotional image (png)

	@FXML
	private TextArea txtAreaFilmDescription; //Type in description of the film 
	
	@FXML
	private Button btnAddFilm; //After form complete, when pressed adds to records
	
	@FXML
	private Label lblAddFilmChecker; //Tells user if the updating is successful! 
	
	@FXML
	private ImageView imgViewFilmImage; //Image of the movie
	
	
	private String imagePathway;
	
	
	//Toolbar variables
		@FXML
		private Button btnLogout;
		
		@FXML
		private Button btnHome;
		
		@FXML
		private Button btnGoToNewListing;

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
	
	
	
	@FXML
	private void uploadImg (ActionEvent event) throws IOException {
		if (btnSelectImg.isArmed()) {
			
			
			FileChooser fileChooser = new FileChooser();
			
			//Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
            
            
			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(null);
			
			try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imgViewFilmImage.setImage(image);
                
                String imagePathway = file.getAbsolutePath(); //Retuns the pathway of an image that has been selected
                this.imagePathway = imagePathway;
                
                
            } catch (IOException ex) {
                System.out.println("Could not upload image");
            }
			
			this.lblAddFilmChecker.setText("Uploaded Image!");
		
		} else {
			this.lblAddFilmChecker.setText("Try again!");
		}
	}
	
	
	@FXML
	private void addFilm (ActionEvent event) throws JSONException, IOException {
		if (!this.txtNameFilm.getText().isEmpty() && !this.txtAreaFilmDescription.getText().isEmpty()) {
			
		
			Movie newMovie = new Movie(this.txtNameFilm.getText(), imagePathway , this.txtAreaFilmDescription.getText());
			TextFileManager.addFilmDetails(newMovie); // Update information in database
			
			this.lblAddFilmChecker.setText("Added Film!");
		} else {
			this.lblAddFilmChecker.setText("Field missing! Try again.");
		}
	}
	
	
	
	
	
	
	//Toolbar methods
	
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
			myController.unloadScreen(ScreensFramework.staffChoiceID);

			myController.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);
			myController.setScreen(ScreensFramework.loginID);
		}
	
		
		
		@FXML
	    private void goToAddListings(ActionEvent event){
		   myController.unloadScreen(ScreensFramework.addFilmListingsID);
		   myController.loadScreen(ScreensFramework.addFilmListingsID, ScreensFramework.addFilmListingsFile);
	       myController.setScreen(ScreensFramework.addFilmListingsID);
	    }
		
	
	
	
	
}
