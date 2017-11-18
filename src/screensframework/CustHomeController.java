package screensframework;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

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

	@FXML
	private DatePicker selectDate;

	ScreensController myController;

	private ObservableList<String> filmNames = FXCollections.observableArrayList();
	private ObservableList<String> timeList = FXCollections.observableArrayList();
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

	}

	/**
	 * 
	 * @return A list of films that are playing on a specified date.
	 * @throws IOException
	 */
	public List<String> getFilmList() throws IOException {
		try {
			String date = dateTimeFormatter.format(selectDate.getValue());
			TextFileManager fileManager = new TextFileManager();
			return fileManager.filmsFilteredByDate(date);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Sets the films list after a date has been selected. Films are those that will
	 * be showing on the specified date.
	 * 
	 * @param event date selection from DatePicker box
	 * @throws IOException
	 */
	@FXML
	private void setFilmList(ActionEvent event) throws IOException {
		selectFilm.getItems().clear();
		// Populate film list
		try {
			List<String> filmList = getFilmList();
			for (int i = 0; i < filmList.size(); i++) {
				filmNames.add(filmList.get(i));
			}
			selectFilm.setItems(filmNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
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