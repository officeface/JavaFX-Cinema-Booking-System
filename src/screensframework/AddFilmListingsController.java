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


/**
 * FXML Controller class
 *
 */

public class AddFilmListingsController implements Initializable, ControlledScreen {

    ScreensController myController; 
    
    @FXML
    private ComboBox<String> comboSelectFilm; //Select from a list of films
	private ObservableList<String> filmNames = FXCollections.observableArrayList(); // Container for film titles
	private String selectedfilmforlisting; //Holds selected Film

    
    @FXML
    private ComboBox<String> comboChooseTime; //Select appropriate times
    private ObservableList<String> TimesAvailable = FXCollections.observableArrayList(); // Container for timings
    private String selectedTimeforlisting;

    @FXML
    private DatePicker listingsDatepicker; //Select a date
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formatting dates
	private String selecteddateforlisting;//Holds selected date

    
    @FXML 
    private Button btnAddListings; //Once form complete - press button to add to records
    
    @FXML
    private Label lblFeedback;
    
    
    
    
    /**
	 * Initialises the controller class.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources){

		List<String[]> filmList=null;
		try {
			filmList = TextFileManager.getFilmTitles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < filmList.size(); i++) {
			filmNames.addAll(filmList.get(i));
		}
		comboSelectFilm.setItems(filmNames);
		
		
		String[] timeList=null;
		try {
			timeList = TextFileManager.getFilmTimings();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < timeList.length; i++) {
			TimesAvailable.add(timeList[i]);
		}
		comboChooseTime.setItems(TimesAvailable);
		
		
		
		
	}
	
	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
	
	
	
	
	
	
	
	@FXML
	private void getSelectedFilm (ActionEvent event) throws IOException {
				this.selectedfilmforlisting = comboSelectFilm.getValue();
				System.out.println(selectedfilmforlisting);
	}
	
	
	@FXML
	private void getSelectedDate (ActionEvent event) throws IOException {
		this.selecteddateforlisting = dateTimeFormatter.format(listingsDatepicker.getValue());
		System.out.println(selecteddateforlisting);
	}
	
	
	@FXML
	private void getSelectedTime (ActionEvent event) throws IOException {
		this.selectedTimeforlisting = comboChooseTime.getValue();
		System.out.println(selectedTimeforlisting);
	}
	
	
	

	
	@FXML
	private void addfilmListing (ActionEvent event) throws IOException {
		
		if (btnAddListings.isArmed()) {
			
			String[][] testSeats = new String[2][2]; //Seats test
		
//			for (int i=0; i<60; i++){
//				System.out.println(""+i+"" +":" +"Free");
//			}
			
Listing newlisting = new Listing("99", this.selectedfilmforlisting , this.selecteddateforlisting, this.selectedTimeforlisting, testSeats);
		   
			TextFileManager.addFilmListings(newlisting); // Update film listing in database
			
			this.lblFeedback.setText("Added Film Listing!");
		} else {
			this.lblFeedback.setText("Try again.");
		}
	}
	
	
	
}
