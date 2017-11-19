package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class CustBookFilmPageController implements Initializable, ControlledScreen {

    ScreensController myController; 
    
    
    //Customer selected summary
   
    @FXML
    private Label lblTimeSelected;
    
    @FXML
    private Label lblDateSelected;
    
    @FXML
    private Label lblFilmSelected;
    
    @FXML
    private TextArea textareaDescription;
    
    @FXML
    private ImageView ImgShowFilmImage;
    
    //Seating
  
    @FXML
    private Button btnResetSeats; 
    
    //Subtotal and continue
    @FXML
    private Label lblSubtotal;
    
    @FXML
    private Button btnContinue;
    
    
    
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
	
	@FXML
	public void goToCustConfirmPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custConfirmPageID);
	}
	
	
	
}
