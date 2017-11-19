package screensframework;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 */
public class StaffChoicePageController implements Initializable, ControlledScreen {

    ScreensController myController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToStaffHome(ActionEvent event){
       myController.setScreen(ScreensFramework.staffHomeID);
    }
    
    @FXML
    private void goToCustHome(ActionEvent event){
		
		myController.loadScreen(ScreensFramework.custHomeID, ScreensFramework.custHomeFile);
        myController.loadScreen(ScreensFramework.custProfilePageID, ScreensFramework.custProfilePageFile);
        myController.loadScreen(ScreensFramework.custBookFilmPageID, ScreensFramework.custBookFilmPageFile);
        myController.loadScreen(ScreensFramework.custConfirmPageID, ScreensFramework.custConfirmPageFile);
       myController.setScreen(ScreensFramework.custHomeID);
    }
    
    
}