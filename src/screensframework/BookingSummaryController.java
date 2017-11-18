package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

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
    private ComboBox comboListOfFilms; //Lists of films to find summary booking of
    
    @FXML
    private ComboBox comboTimeSelector; //Selected time of booking
    
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
