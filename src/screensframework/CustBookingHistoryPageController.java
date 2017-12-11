package screensframework;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.json.JSONException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class CustBookingHistoryPageController extends ToolbarController implements Initializable, ControlledScreen {
 
	@FXML
	private Button btnLogout, btnHome, btnMyProfile, btnMyBookings, btnUpdate;

	@FXML
	public TextField txtEmail, txtFirstName, txtLastName;

	@FXML
	private Label lblUpdateStatus, lblFetchDetails;
	
	@FXML
	private Accordion bookingAccordion = new Accordion();

	ScreensController myController;
	
	// Booking History:
	Map<String, List<String>> bookingHistory;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			bookingHistory = Customer.importBookingHistory(LoginController.USER);
			// Iterate through HashMap:
			Set<String> keys = bookingHistory.keySet();
			int i = 0;
			TitledPane[] bookings = new TitledPane[bookingHistory.size()];
			
			for(String key : keys) {
				TitledPane pane = new TitledPane(); // Set title of pane
				pane.setText(Listing.findMovieTitle(key) + "\n" + Listing.findMovieDateAndTime(key)); // Set date/time information
				
				// Add delete button if the listing has not yet completed:
				String timeNowString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
				String timeListingString = Listing.findMovieDateAndTime(key);
				Date timeNow = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(timeNowString);
				Date timeListing = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(timeListingString);
				
				if (timeNow.before(timeListing)) {
				
					Button btnDeleteListing = new Button("Delete Listing");
					// Add ActionEvent for delete button:
					btnDeleteListing.setOnAction((ActionEvent e) -> {
						try {
							TextFileManager.removeBooking(LoginController.USER, key);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						this.bookingAccordion.getPanes().remove(pane);
					});
					
					pane.setGraphic(btnDeleteListing);
					
				}
				
				
				
				
				// Set pane contents:
				List<String> seatsArrayList = bookingHistory.get(key);
				String totalCost = "Total cost: £" + 5*seatsArrayList.size() + ".00"; 	// Set booking total cost
				seatsArrayList.add(totalCost);
				ObservableList<String> seatsObservableList = FXCollections.observableArrayList(seatsArrayList);
				ListView<String> seatsListView = new ListView<String>(seatsObservableList);
				pane.setContent(seatsListView);
					
				bookings[i] = pane; // Add pane to list of bookings
				i++; // Go to next index of bookings array
			}
			
			this.bookingAccordion.getPanes().addAll(bookings);
			
		} catch(ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	
	

	@Override
	@FXML
	public void goToCustHome(ActionEvent event) {
		myController.setScreen(ScreensFramework.custHomeID);
	}

	@Override
	@FXML
	public void goToCustProfilePage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custProfilePageID);
	}

	@Override
	@FXML
	public void goToLogin(ActionEvent event) {

		// Unload screens:
		myController.unloadScreen(ScreensFramework.loginID);
		myController.unloadScreen(ScreensFramework.registrationID);
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

}
