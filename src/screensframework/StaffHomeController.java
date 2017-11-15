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
public class StaffHomeController implements Initializable , ControlledScreen {

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
    private void goToLogin(ActionEvent event){
       myController.setScreen(ScreensFramework.loginID);
    }
    
    @FXML
    private void goToStaffChoicePage(ActionEvent event){
       myController.setScreen(ScreensFramework.staffChoiceID);
    }
}