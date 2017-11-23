package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class StaffExportController implements Initializable, ControlledScreen {

    ScreensController myController; 
    
    @FXML
    private ComboBox<String> filmdropdown; //Lists of films to export
    
    @FXML
    private Button btnexportselectedfilm; //Export to txt the selected film in the combobox 
    
    @FXML
    private Button btnexportall; //Export to txt all the available films
    
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
