package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ToolbarController implements Initializable, ControlledScreen {

	ScreensController myController = new ScreensController();

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void goToLogin(ActionEvent event) {

		// Unload screens:
		myController.unloadScreen(ScreensFramework.loginID);
		myController.unloadScreen(ScreensFramework.staffHomeID);
		myController.unloadScreen(ScreensFramework.custHomeID);
		myController.unloadScreen(ScreensFramework.custProfilePageID);
		myController.unloadScreen(ScreensFramework.custBookFilmPageID);
		myController.unloadScreen(ScreensFramework.custConfirmPageID);

		myController.unloadScreen(ScreensFramework.staffExportID);
		myController.unloadScreen(ScreensFramework.bookingSummaryID);
		myController.unloadScreen(ScreensFramework.addFilmPageID);
		myController.unloadScreen(ScreensFramework.addFilmListingsID);
		myController.unloadScreen(ScreensFramework.staffChoiceID);

		myController.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);
		myController.setScreen(ScreensFramework.loginID);
	}

	@FXML
	public void goToStaffChoicePage(ActionEvent event) {
		// myController.unloadScreen(ScreensFramework.staffChoiceID);
		// myController.loadScreen(ScreensFramework.staffChoiceID,
		// ScreensFramework.staffChoiceFile);

		myController.setScreen(ScreensFramework.staffChoiceID);
	}

	@FXML
	public void goToCustHome(ActionEvent event) {
		// myController.unloadScreen(ScreensFramework.custHomeID);
		// myController.loadScreen(ScreensFramework.custHomeID,
		// ScreensFramework.custHomeFile);

		myController.setScreen(ScreensFramework.custHomeID);
	}

	@FXML
	public void goToCustProfilePage(ActionEvent event) {
		// myController.unloadScreen(ScreensFramework.custProfilePageID);
		// myController.loadScreen(ScreensFramework.custProfilePageID,
		// ScreensFramework.custProfilePageFile);

		myController.setScreen(ScreensFramework.custProfilePageID);
	}

}
