package screensframework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 */
public class CustHomeController implements Initializable, ControlledScreen {

	@FXML
	private Button btnLogout, btnHome, btnMyProfile, btnMyBookings;

	@FXML
	private ComboBox<String> selectFilm;
	
	@FXML
	private ComboBox<String> selectTime;
	
	
	ScreensController myController;
	
	
	private ObservableList<String> filmNames = FXCollections.observableArrayList();
	private ObservableList<String> timeList = FXCollections.observableArrayList();
	

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		// Populate times list
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				timeList.add("0" + i + ":00");
			} else {
				timeList.add(i + ":00");
			}
		}
		selectTime.setItems(timeList);
		
		// Populate film list
		try {
			List<String[]> filmList = getFilmList();
			for (int i = 0; i < filmList.size(); i++) {
				filmNames.add(filmList.get(i)[0]);
			}
			selectFilm.setItems(filmNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
		
	public List<String[]> getFilmList() throws IOException{
		TextFileManager fileManager = new TextFileManager();
		return fileManager.getFilmList();
	}

	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	@FXML
	private void goToLogin(ActionEvent event) {
		myController.setScreen(ScreensFramework.loginID);
	}

	@FXML
	private void goToStaffChoicePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffChoiceID);
	}

	@FXML
	private void goToCustHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.custHomeID);
	}

	@FXML
	private void goToCustProfilePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custProfilePageID);
	}

}