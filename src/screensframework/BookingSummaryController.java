package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 */

public class BookingSummaryController implements Initializable, ControlledScreen {

    ScreensController myController; 
    
    // 1) Variables to change the summary info below 
    @FXML 
    private ComboBox<String> comboListOfFilms; //Lists of films to find summary booking of
    
    @FXML
    private ComboBox<String> comboTimeSelector; //Selected time of booking
    
    @FXML
    private DatePicker DatePickerSelector; //Selected date of booking 
    
    @FXML
    private Button btnScreenInfo; //Once form fill, pressing button will show:
    //1) graphical image of the screen 2) Updated seat numbers
    
    
    
    // 2) Displaying seat numbers
    @FXML 
    private Label lblBookedSeats; //Label describing the number of booked seats
    
    @FXML
    private Label lblFreeSeats; //Label describing the number of free seats

    
    
    
    // 3) SPACE NEEDED FOR THE VARIABLES INVOLVED IN THE GRAPHICAL REPRESENTATION OF THE SCREEN
    
    
    
    
    
    
    
  //Toolbar variables
  		@FXML
  		private Button btnLogout;
  		
  		@FXML
  		private Button btnHome;
  		
  		@FXML
  		private Button btnGoToExport;
    
    
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
		    private void goToStaffExport(ActionEvent event){
		       myController.setScreen(ScreensFramework.staffExportID);
		    }
	
	
	
	
	
	
	
}
