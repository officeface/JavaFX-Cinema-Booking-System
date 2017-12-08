package screensframework;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class StaffExportController implements Initializable, ControlledScreen {

    ScreensController myController; 
    
    @FXML
    private ComboBox<String> filmdropdown; //Lists of films to export
	private ObservableList<String> filmNames = FXCollections.observableArrayList(); // Container for film titles
	private String selectedfilmforexport; // Holds selected Film for export



    
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
	@FXML
	public void getSelectedFilmDetailsToCSV(ActionEvent event) throws IOException {
		
		//Getting specific film name to export to csv
		this.selectedfilmforexport = filmdropdown.getValue();
		JSONObject objSF = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		
		//New json array to hold title,free and bookseats info
				JSONObject objSF2 = new JSONObject();
				JSONArray seatsarrySF = new JSONArray();
				JSONObject itemSF = new JSONObject();
				itemSF.put("title", "");
				itemSF.put("FreeSeats", "");
				itemSF.put("BookedSeats", "");
				itemSF.put("date", "");
				itemSF.put("time", "");
				seatsarrySF.put(itemSF);
				objSF2.put("SelectedFilmInfo", seatsarrySF);
		
		
		
		
		JSONArray jsonArraySF = objSF.getJSONArray("FilmTimes");
		for (int z = 0; z < jsonArraySF.length(); z++) {
			if (jsonArraySF.getJSONObject(z).getString("title").equals(this.selectedfilmforexport)){
				
				//Get selected title
				String[] temptitleArraySF = new String[1];
				temptitleArraySF[0] = jsonArraySF.getJSONObject(z).getString("title");
				
				//Get date
				String[] tempdateArraySF = new String[1];
				tempdateArraySF[0] = jsonArraySF.getJSONObject(z).getString("date");
				
				
				//Get time
				String[] temptimeArraySF = new String[1];
				temptimeArraySF[0] = jsonArraySF.getJSONObject(z).getString("time");
				
				Integer getseatinfoSF = z + 1;
				String[][] seats = TextFileManager.getSeatInformation(getseatinfoSF.toString());
				Integer bookedseatscounterSF = 0;
				Integer freeseatscounterSF = 0;
					// Generate the seats according to the listing information:
					for (int i = 0; i < 6; i++) {
						for (int j = 0; j < 10; j++) {
							// Check if seat is available:
							if (seats[i][j].equals("Free")) {
								freeseatscounterSF = freeseatscounterSF + 1;
							} else {
								bookedseatscounterSF = bookedseatscounterSF + 1;
							}
						}
					};
				//Setting the labels to their respective numbers  
				String bscSF = bookedseatscounterSF.toString();
				String fscSF = freeseatscounterSF.toString();
				
				JSONObject templistSF = new JSONObject();
				templistSF.put("title", temptitleArraySF[0]);
				templistSF.put("FreeSeats", fscSF);
				templistSF.put("BookedSeats", bscSF);
				templistSF.put("date", tempdateArraySF[0]);
				templistSF.put("time", temptimeArraySF[0]);
				seatsarrySF.put(templistSF);
			
			}//End of conditional statement
		
		}//End of loop 
		
		String o = (CDL.toString(new JSONArray(objSF2.get("SelectedFilmInfo").toString())));
		FileUtils.writeStringToFile(new File(this.selectedfilmforexport + " DataExport.text"), o, "UTF-8");
		System.out.println(this.selectedfilmforexport + " information has been exported!");
		
	}//End of method
	
	
	
	
	
	
	
	//Export all films -> save to a csv file
	
	@FXML
	public void getDatabaseToCSV(ActionEvent event) throws IOException {
		
		JSONObject obj = JSONUtils.getJSONObjectFromFile(TextFileManager.database);
		//Creating a new csv file with FilmList from database at the start
		String x = (CDL.toString(new JSONArray(obj.get("FilmList").toString())));	
		FileUtils.writeStringToFile(new File("FilmDataExported.text"), x, "UTF-8");	
		//Appending FilmTimes to the file
		String y = (CDL.toString(new JSONArray(obj.get("FilmTimes").toString())));
		Files.write(Paths.get("FilmDataExported.text"), y.getBytes(), StandardOpenOption.APPEND);

	
		//New json array to hold title,free and bookseats info
		JSONObject obj2 = new JSONObject();
		JSONArray seatsarry = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("title", "");
		item.put("FreeSeats", "");
		item.put("BookedSeats", "");
		item.put("date", "");
		item.put("time", "");
		seatsarry.put(item);
		obj2.put("SeatsInfo", seatsarry);

		
	
		//For Title values
		JSONArray jsonArray = obj.getJSONArray("FilmTimes");
		
		for (int z = 0; z < jsonArray.length(); z++) {
			//Get title 
			String[] temptitleArray = new String[1];
			temptitleArray[0] = jsonArray.getJSONObject(z).getString("title");
			
			//Get date
			String[] tempdateArray = new String[1];
			tempdateArray[0] = jsonArray.getJSONObject(z).getString("date");
			
			
			//Get time
			String[] temptimeArray = new String[1];
			temptimeArray[0] = jsonArray.getJSONObject(z).getString("time");
		
			//Getting seats values for each title
			Integer getseatinfo = z + 1;
			String[][] seats = TextFileManager.getSeatInformation(getseatinfo.toString());
			Integer bookedseatscounter = 0;
			Integer freeseatscounter = 0;
				// Generate the seats according to the listing information:
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 10; j++) {
						// Check if seat is available:
						if (seats[i][j].equals("Free")) {
							freeseatscounter = freeseatscounter + 1;
						} else {
							bookedseatscounter = bookedseatscounter + 1;
						}
					}
				};
			//Setting the labels to their respective numbers  
			String bsc = bookedseatscounter.toString();
			String fsc = freeseatscounter.toString();
			
			JSONObject templist = new JSONObject();
			templist.put("title", temptitleArray[0]);
			templist.put("FreeSeats", fsc);
			templist.put("BookedSeats", bsc);
			templist.put("date", tempdateArray[0]);
			templist.put("time", temptimeArray[0]);
			seatsarry.put(templist);
			
		}//End of loop
	
		String z = (CDL.toString(new JSONArray(obj2.get("SeatsInfo").toString())));
		Files.write(Paths.get("FilmDataExported.text"), z.getBytes(), StandardOpenOption.APPEND);
		
		System.out.println("Database has been exported!");
		
	}//End of overall method
	
	
	
	     
	
	
	
	
	
	
	
	
	
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
