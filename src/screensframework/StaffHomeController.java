package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class StaffHomeController implements Initializable, ControlledScreen {
	
    ScreensController myController; 
   
    @FXML
    private Button btnAddFilm; //go to add film PAGE
    
    @FXML
    private Button btnAddListening; //go to add film LISTINGS
    
    @FXML
    private Button btnBookingSummary; //go to BOOKING SUMMARY
    
    @FXML
    private Button btnExportFilms; //go to EXPORT FILMS
    
    /**
	 * Initialises the controller class.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
	
	
}
