package screensframework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class StaffExportController implements Initializable, ControlledScreen {

    ScreensController myController; 
    
    @FXML
    private ComboBox<String> filmdropdown; //Lists of films to export
	private ObservableList<String> filmNames = FXCollections.observableArrayList(); // Container for film titles


    
    @FXML
    private Button btnexportselectedfilm; //Export to txt the selected film in the combobox 
    
    @FXML
    private Button btnexportall; //Export to txt all the available films
    
    
  //Toolbar variables
		@FXML
		private Button btnLogout;
		
		@FXML
		private Button btnHome;
		
		@FXML
		private Button btnGoToBookingSummary;
    
    
    
    
    /**
	 * Initialises the controller class.
	 */
 
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    		
		List<String[]> filmList=null;
		try {
			filmList = TextFileManager.getFilmTitles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
		for (int i = 0; i < filmList.size(); i++) {
			filmNames.addAll(filmList.get(i));
		}
		filmdropdown.setItems(filmNames);
    }
    		
    

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
	

	
	//Export specific film
	
	
	
	
	//Export all films -> save to a csv file
	
	@FXML
	public void getDatabaseToCSV(ActionEvent event) throws IOException {
		
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		String x = (CDL.toString(new JSONArray(obj.get("FilmList").toString())));
		FileUtils.writeStringToFile(new File("testofexport1.txt"), x, "UTF-8");	
				
		System.out.println("Database has been exported!");
		
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
    private void goToBookingSummary(ActionEvent event){
       myController.setScreen(ScreensFramework.bookingSummaryID);
    }
	

	
	
	
	
	
	
	
	
	
}
