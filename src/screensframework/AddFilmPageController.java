package screensframework;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.json.JSONException;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;;


/**
 * FXML Controller class
 *
 */

public class AddFilmPageController implements Initializable, ControlledScreen {

	ScreensController myController; 
    
	@FXML
	private TextField txtnamefilm; //Type in the name of new film 
	
	@FXML
	private Button btnselectimg; //Upload a new promotional image (png)

	@FXML
	private TextArea txtareafilmdescription; //Type in description of the film 
	
	@FXML
	private Button btnAddFilm; //After form complete, when pressed adds to records
	
	@FXML
	private Label lbladdfilmchecker; //Tells user if the updating is successful! 
	
	@FXML
	private ImageView ImgvfilmImage; //Image of the movie
	
	
	private String imagepathway;

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
	
	
	
	@FXML
	private void uploadimg (ActionEvent event) throws IOException {
		if (btnselectimg.isArmed()) {
			
			
			FileChooser fileChooser = new FileChooser();
			
			//Set extension filter
            FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
            
            
			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(null);
			
			try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ImgvfilmImage.setImage(image);
                
                String imagepathway = file.getAbsolutePath(); //Retuns the pathway of an image that has been selected
                this.imagepathway = imagepathway;
                
                
            } catch (IOException ex) {
                System.out.println("Could not upload image");
            }
			
			this.lbladdfilmchecker.setText("Uploaded Image!");
		
		} else {
			this.lbladdfilmchecker.setText("Try again!");
		}
	}
	
	
	@FXML
	private void addfilm (ActionEvent event) throws JSONException, IOException {
		if (!this.txtnamefilm.getText().isEmpty() && !this.txtareafilmdescription.getText().isEmpty()) {
			
		
			Movie newmovie = new Movie(this.txtnamefilm.getText(), imagepathway , this.txtareafilmdescription.getText());
			TextFileManager.addFilmDetails(newmovie); // Update information in database
			
			this.lbladdfilmchecker.setText("Added Film!");
		} else {
			this.lbladdfilmchecker.setText("Field missing! Try again.");
		}
	}
	
	
	
	
	
	
	
	
}
