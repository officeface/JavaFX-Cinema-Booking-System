package screensframework;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;;


/**
 * FXML Controller class
 *
 */

public class AddFilmPageController implements Initializable, ControlledScreen {

	ScreensController myController; 
    
	@FXML
	private TextField txtnamefilm; //Type in the name of new film 
	
	@FXML
	private Button btnselectimg; //Upload a new promotional image (png)

	@FXML
	private TextArea txtfieldfilmdescription; //Type in description of the film 
	
	@FXML
	private Button btnAddFilm; //After form complete, when pressed adds to records

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
