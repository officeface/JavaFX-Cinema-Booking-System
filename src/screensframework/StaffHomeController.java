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
    private Button btnAddListings; //go to add film LISTINGS
    
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
	
	
	@FXML
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
	
	
	//METHODS
	//go to add film 
	@FXML
    private void goToAddFilmPage(ActionEvent event){
       myController.setScreen(ScreensFramework.addFilmPageID);
    }
	
	//go to add listings
	@FXML
    private void goToAddListings(ActionEvent event){
       myController.setScreen(ScreensFramework.addFilmListingsID);
    }
	
	//go to booking summary
	
	@FXML
    private void goToBookingSummary(ActionEvent event){
       myController.setScreen(ScreensFramework.bookingSummaryID);
    }
	
	//go to export films
	@FXML
    private void goToStaffExport(ActionEvent event){
       myController.setScreen(ScreensFramework.staffExportID);
    }

		
	
}
