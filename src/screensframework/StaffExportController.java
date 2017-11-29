package screensframework;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	

	
	
	
	
	
	
	
	
	
}
