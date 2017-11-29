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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 */

public class BookingSummaryController implements Initializable, ControlledScreen {

    ScreensController myController; 
    
    // 1) Variables to change the summary info below 
    @FXML 
    private ComboBox<String> comboListOfFilms; //Lists of films to find summary booking of
    
    @FXML
    private ComboBox<String> comboTimeSelector; //Selected time of booking
    
    @FXML
    private DatePicker DatePickerSelector; //Selected date of booking 
    
    @FXML
    private Button btnScreenInfo; //Once form fill, pressing button will show the summary data

    // To store data from TextFileManager
    private ObservableList<String> filmNames = FXCollections.observableArrayList(); // Container for film titles
	private ObservableList<String> timeList = FXCollections.observableArrayList(); // Container for film times
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formatting dates
    
    
    // 2) Displaying seat numbers
    @FXML 
    private Label lblBookedSeats; //Label describing the number of booked seats
    
    @FXML
    private Label lblFreeSeats; //Label describing the number of free seats
    
    
    // 3) VARIABLES INVOLVED IN THE GRAPHICAL REPRESENTATION OF THE SCREEN
    
    // Seating
 	String[][] seats; 
    
 	@FXML
	private GridPane seatLayout;
    
    
    
  //Toolbar variables
  		@FXML
  		private Button btnLogout;
  		
  		@FXML
  		private Button btnHome;
  		
  		@FXML
  		private Button btnGoToExport;
    
    
    /**
	 * Initialises the controller class.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
	

	
	
	// Find current listings based on date -> METHODS @author: Mark
	/**
	 * 
	 * @return A list of films that are playing on a specified date.
	 * @throws IOException
	 */
	public List<String> getFilmList() throws IOException {
		try {
			String date = dateTimeFormatter.format(DatePickerSelector.getValue());
			return TextFileManager.filmsFilteredByDate(date);
		} catch (IOException e) {
			return null;
		}
	}

	
	/**
	 * 
	 * @return A list of times for a given date and film.
	 * @throws IOException
	 */
	public List<String> getTimesList() throws IOException {
		try {
			String date = dateTimeFormatter.format(DatePickerSelector.getValue());
			String film = comboListOfFilms.getValue();
			return TextFileManager.timesFilteredByDateAndFilm(date, film);
		} catch (IOException e) {
			return null;
		}
	}

	
	/**
	 * Sets the films list after a date has been selected. Films are those that will

	 * be showing on the specified date.
	 * 
	 * @param event
	 *            date selection from DatePicker box
	 * @throws IOException
	 */
	@FXML
	private void setFilmList(ActionEvent event) throws IOException {
		comboListOfFilms.getItems().clear();
		// Populate film list
		try {
			List<String> filmList = getFilmList();
			for (int i = 0; i < filmList.size(); i++) {
				filmNames.add(filmList.get(i));
			}
			comboListOfFilms.setItems(filmNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@FXML
	private void setTimesList(ActionEvent event) throws IOException {
		comboTimeSelector.getItems().clear();
		// Populate times list
		try {
			List<String> times = getTimesList();
			for (int i = 0; i < times.size(); i++) {
				timeList.add(times.get(i));
			}
			comboTimeSelector.setItems(timeList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//WHEN SCREEN INFO BUTTON PRESSED - BOOKING SUMMARY UPDATED BELOW 
	@FXML
	public void getScreenInfo(ActionEvent event) throws IOException {
		
			String title = this.comboListOfFilms.getValue();
			String date = dateTimeFormatter.format(DatePickerSelector.getValue());
			String time = this.comboTimeSelector.getValue();
			String listingID = Listing.findShowingID(title, date, time);
			String[][] seats = TextFileManager.getSeatInformation(listingID);
			
			Integer bookedseatscounter = 0;
			Integer freeseatscounter = 0;
		
			
			// Generate the seats according to the listing information:
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 10; j++) {
					Button btn = new Button();
					Integer I = (Integer) i;
					Integer J = (Integer) j;
					seatLayout.add(btn, j, i);
					btn.setPrefSize(38, 28);
					btn.setId(getSeatName(I, J));
					

					// Check if seat is available:
					if (seats[i][j].equals("Free")) {
						btn.setStyle("-fx-base: lightgreen;");
						freeseatscounter = freeseatscounter + 1;
						
					} else {
						btn.setStyle("-fx-base: lightpink;");
						bookedseatscounter = bookedseatscounter + 1;
						
					}

					
					
				}
					
			};
			
			//Setting the labels to their respective numbers  
			lblBookedSeats.setText(bookedseatscounter.toString());
			lblFreeSeats.setText(freeseatscounter.toString());
			
		}
			
	
	
	/**
	 * 
	 * @param row
	 *            Seat's row number
	 * @param col
	 *            Seat's col number
	 * @return The seat's name as a nice string. e.g. if the seat is in row 0, col
	 *         5, the seat name will be "Seat A6" (rows/cols are zero-indexed)
	 */
	public String getSeatName(Integer row, Integer col) {

		char rowAnswer = (char) ('A' + row);

		Integer colShift = col + 1;
		String colAnswer = colShift.toString();

		return "Seat " + rowAnswer + colAnswer;
	}
	

	//Toolbar methods
	
	@FXML
	public void goToStaffHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.staffHomeID);
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
    private void goToStaffExport(ActionEvent event){
       myController.setScreen(ScreensFramework.staffExportID);
    }

	
}
