package screensframework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * FXML Controller class for the staff export page.
 * 
 * This page provides two export functionalities.
 * 
 * 1) An employee may select a film and export it's json data to a csv file. The
 * number of free and booked seats are also calculated for each listing as it is
 * not available in the database.
 * 
 * 2) An employee may export all data from json to csv in addition to all film
 * listings and number of free and booked seats.
 * 
 * @author Fraz Ahmad
 *
 */
public class StaffExportController implements Initializable, ControlledScreen, StaffToolbar {

	ScreensController myController;

	@FXML
	private ComboBox<String> filmDropDown; // Lists of films to export
	private ObservableList<String> filmNames = FXCollections.observableArrayList(); // Container for film titles
	private String selectedFilmForExport; // Holds selected Film for export

	@FXML
	private Button btnExportSelectedFilm; // Export to txt the selected film in the combobox

	@FXML
	private Button btnExportAll; // Export to txt all the available films

	// Toolbar variables
	@FXML
	private Button btnSLogout;

	@FXML
	private Button btnSHome;

	@FXML
	private Button btnGoToBookingSummary;

	@FXML
	private Label lblFeedback;

	/**
	 * Initialises the controller class.
	 *
	 * A list of films is added to a film drop-down menu so that a user may select a
	 * specific film to export.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// As page loads film titles are added to the filmdropdown menu
		List<String[]> filmList = null;
		try {
			filmList = TextFileManager.getFilmTitles(); // Getting film titles from the database
		} catch (IOException e) {

			e.printStackTrace();
		}

		for (int i = 0; i < filmList.size(); i++) {
			filmNames.addAll(filmList.get(i)); // Intermediate
		}
		filmDropDown.setItems(filmNames); // Setting film titles to the dropdown
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	/**
	 * Exports film details of a specified film from a json arrays to csv file.
	 * Number of booked and free seats of a specified film are also calculated.
	 * 
	 * @author Fraz Ahmad
	 * @param event
	 *            the Employee has clicked the export button.
	 * @throws IOException
	 *             if the database file could not be found.
	 */
	@FXML
	public void getSelectedFilmDetailsToCSV(ActionEvent event) throws IOException {

		if (!filmDropDown.getSelectionModel().isEmpty()) { // If a film has been chosen!

			// Getting specific film name to export to csv
			this.selectedFilmForExport = filmDropDown.getValue();
			JSONObject objSF = JSONUtils.getJSONObjectFromFile(TextFileManager.database); // New json object is the
																							// database
																							// for scanning

			// New json object and array to hold title,free and bookseats info
			JSONObject objSF2 = new JSONObject();
			JSONArray seatsArraySF = new JSONArray();
			JSONObject itemSF = new JSONObject();
			// Adding fields to array + temporary array created
			itemSF.put("title", "");
			itemSF.put("FreeSeats", "");
			itemSF.put("BookedSeats", "");
			itemSF.put("date", "");
			itemSF.put("time", "");
			seatsArraySF.put(itemSF);
			objSF2.put("SelectedFilmInfo", seatsArraySF); // Naming the array and adding to a new json object

			JSONArray jsonArraySF = objSF.getJSONArray("FilmTimes"); // Getting listings data from main database
			for (int z = 0; z < jsonArraySF.length(); z++) { // To transverse listings data
				if (jsonArraySF.getJSONObject(z).getString("title").equals(this.selectedFilmForExport)) { // Finding
																											// matching
																											// title

					// Get selected title from database
					String[] tempTitleArraySF = new String[1];
					tempTitleArraySF[0] = jsonArraySF.getJSONObject(z).getString("title");

					// Get date from database
					String[] tempDateArraySF = new String[1];
					tempDateArraySF[0] = jsonArraySF.getJSONObject(z).getString("date");

					// Get time from database
					String[] tempTimeArraySF = new String[1];
					tempTimeArraySF[0] = jsonArraySF.getJSONObject(z).getString("time");

					Integer getSeatInfoSF = z + 1; // Position of seat info
					String[][] seats = TextFileManager.getSeatInformation(getSeatInfoSF.toString()); // getting seats
																										// info
					// Initialising book/free seat variable
					Integer bookedSeatsCounterSF = 0;
					Integer freeSeatsCounterSF = 0;
					// Generate the seats according to the listing information:
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 10; j++) {
							// Check if seat is available:
							if (seats[i][j].equals("Free")) {
								freeSeatsCounterSF = freeSeatsCounterSF + 1; // Updates free seats counter
							} else {
								bookedSeatsCounterSF = bookedSeatsCounterSF + 1; // Updates booked seats counter
							}
						}
					}
					;
					// Setting the labels to their respective numbers
					String bscSF = bookedSeatsCounterSF.toString();
					String fscSF = freeSeatsCounterSF.toString();

					// Adding temp selected film data into the existing jsonarray
					JSONObject tempListSF = new JSONObject();
					tempListSF.put("title", tempTitleArraySF[0]);
					tempListSF.put("FreeSeats", fscSF);
					tempListSF.put("BookedSeats", bscSF);
					tempListSF.put("date", tempDateArraySF[0]);
					tempListSF.put("time", tempTimeArraySF[0]);
					seatsArraySF.put(tempListSF);

				}

			}

			String o = (CDL.toString(new JSONArray(objSF2.get("SelectedFilmInfo").toString()))); // Conversion from json
																									// array to csv
																									// format
																									// and then into a
																									// new
																									// string

			// Opens dialogue to ask user to specify a directory to save the exported data
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save file");
			fileChooser.setInitialFileName(this.selectedFilmForExport.replaceAll(" ", "") + "DataExport.text"); // Initial
																												// file
																												// name
			Window savedStage = null;
			File savedFile = fileChooser.showSaveDialog(savedStage);

			if (savedFile != null) { // If save destination has been chosen
				FileUtils.writeStringToFile(savedFile, o, "UTF-8"); // New csv file created - previous string (which was
																	// json) written to new text file

				ScreensFramework.LOGGER.info(this.selectedFilmForExport + " information has been exported!");
				// System.out.println(this.selectedFilmForExport + " information has been
				// exported!");
				lblFeedback.setText((this.selectedFilmForExport + " information has been exported!"));

			}
		}
	}

	/**
	 * Exports entire database into a csv file. Also the number of booked and free
	 * seats for each of the films are also added.
	 * 
	 * @author Fraz Ahmad
	 * @param event
	 *            the user chooses to export the entire database.
	 * @throws IOException
	 *             if the database file could not be found.
	 */
	@FXML
	public void getDatabaseToCSV(ActionEvent event) throws IOException {

		// Opens dialogue to ask user to specify a directory to save the exported data
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");
		fileChooser.setInitialFileName("FilmDataExported.text"); // Initial file name
		Window savedStage = null;
		File savedFile = fileChooser.showSaveDialog(savedStage);

		if (savedFile != null) { // If a save destination has been picked

			// Creating new json object from the database
			JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);

			// Creating a new csv file with FilmList from database at the start
			String x = (CDL.toString(new JSONArray(obj.get("FilmList").toString())));
			FileUtils.writeStringToFile(savedFile, x, "UTF-8");

			// Getting absolute path of file
			String savedfilepath = savedFile.getAbsolutePath();

			// Appending FilmTimes to the file
			String y = (CDL.toString(new JSONArray(obj.get("FilmTimes").toString())));
			Files.write(Paths.get(savedfilepath), y.getBytes(), StandardOpenOption.APPEND);

			// New json array to hold title,free and bookseats info
			JSONObject obj2 = new JSONObject();
			JSONArray seatsArray = new JSONArray();
			JSONObject item = new JSONObject();
			item.put("title", "");
			item.put("FreeSeats", "");
			item.put("BookedSeats", "");
			item.put("date", "");
			item.put("time", "");
			seatsArray.put(item);
			obj2.put("SeatsInfo", seatsArray);

			// For Title values
			JSONArray jsonArray = obj.getJSONArray("FilmTimes");

			for (int z = 0; z < jsonArray.length(); z++) {
				// Get title
				String[] tempTitleArray = new String[1];
				tempTitleArray[0] = jsonArray.getJSONObject(z).getString("title");

				// Get date
				String[] tempDateArray = new String[1];
				tempDateArray[0] = jsonArray.getJSONObject(z).getString("date");

				// Get time
				String[] tempTimeArray = new String[1];
				tempTimeArray[0] = jsonArray.getJSONObject(z).getString("time");

				// Getting seats values for each title
				Integer getSeatInfo = z + 1;
				String[][] seats = TextFileManager.getSeatInformation(getSeatInfo.toString());
				Integer bookedSeatsCounter = 0;
				Integer freeSeatsCounter = 0;
				// Generate the seats according to the listing information:
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 10; j++) {
						// Check if seat is available:
						if (seats[i][j].equals("Free")) {
							freeSeatsCounter = freeSeatsCounter + 1;
						} else {
							bookedSeatsCounter = bookedSeatsCounter + 1;
						}
					}
				}
				;
				// Setting the labels to their respective numbers
				String bsc = bookedSeatsCounter.toString();
				String fsc = freeSeatsCounter.toString();

				JSONObject tempList = new JSONObject();
				tempList.put("title", tempTitleArray[0]);
				tempList.put("FreeSeats", fsc);
				tempList.put("BookedSeats", bsc);
				tempList.put("date", tempDateArray[0]);
				tempList.put("time", tempTimeArray[0]);
				seatsArray.put(tempList);

			} // End of loop

			String z = (CDL.toString(new JSONArray(obj2.get("SeatsInfo").toString())));
			Files.write(Paths.get(savedfilepath), z.getBytes(), StandardOpenOption.APPEND);

			ScreensFramework.LOGGER.info("Database has been exported!");
			// System.out.println("Database has been exported!");
			lblFeedback.setText("Database has been exported!");
		}

	}// End of overall method

	// Toolbar methods
	/**
	 * {@inheritDoc}
	 */
	@FXML
	public void goToStaffHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffHomeID);
	}

	/**
	 * {@inheritDoc}
	 */
	@FXML
	public void goToLogin(ActionEvent event) {
		// Unload the User:
		LoginController.USER.clearDetails();
		ScreensFramework.LOGGER.info("User logged out.");
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

		myController.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);
		myController.setScreen(ScreensFramework.loginID);
	}

	/**
	 * {@inheritDoc}
	 */
	@FXML
	public void goToBookingSummary(ActionEvent event) {
		myController.setScreen(ScreensFramework.bookingSummaryID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void goToAddFilmPage(ActionEvent event) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void goToAddListings(ActionEvent event) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void goToStaffExport(ActionEvent event) {
	}

}
