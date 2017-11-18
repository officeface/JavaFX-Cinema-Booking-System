package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;


/**
 * FXML Controller class
 *
 */

public class AddFilmListingsController implements Initializable, ControlledScreen {

    ScreensController myController; 
    
    @FXML
    private ComboBox<String> comboSelectFilm; //Select from a list of films 
    
    @FXML
    private ComboBox<String> comboChooseTime; //Select appropriate times

    @FXML
    private DatePicker listingsDatepicker; //Select a date
    
    @FXML 
    private Button btnAddListings; //Once form complete - press button to add to records
    
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
