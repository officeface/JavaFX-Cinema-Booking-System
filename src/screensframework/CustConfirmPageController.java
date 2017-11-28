package screensframework;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CustConfirmPageController implements Initializable, ControlledScreen {

    ScreensController myController; 
    

    //Labels to change
    
    @FXML
    private Label lblTitle;
    
    @FXML
    private Label lblDate;
    
    @FXML
    private Label lblTime;
    
    @FXML
    private Label lblNoOfSeats;
    
    @FXML
    private Label lblOverallPrice;
    
    //Buttons
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnBook;
    
    
    
    
    
    
    
    
    
    
    /**
	 * Initialises the controller class.
	 */
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Labels have been updated in the confirm page from the booking page
		this.lblTitle.setText(CustHomeController.BOOKING.getMovie().getTitle());
		this.lblDate.setText(CustHomeController.BOOKING.getMovie().getDate());
		this.lblTime.setText(CustHomeController.BOOKING.getMovie().getTime());
		Integer numberOfSeats = (Integer)CustHomeController.BOOKING.getSeats().size();
		this.lblNoOfSeats.setText(numberOfSeats.toString());
		this.lblOverallPrice.setText("£" + 5*numberOfSeats + ".00");
	}
	
	
	@FXML
	private void book(ActionEvent event) throws JSONException, IOException {
		TextFileManager.updateListing(CustHomeController.LISTING);
		TextFileManager.updateBookingHistory(CustHomeController.BOOKING);
		System.out.println("Booked!");
	}
	
	
	
	
	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
	
	@FXML
	public void goToCustBookFilmPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custBookFilmPageID);
	}
	
}
