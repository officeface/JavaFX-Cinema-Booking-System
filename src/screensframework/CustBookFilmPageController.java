package screensframework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CustBookFilmPageController implements Initializable, ControlledScreen {

	ScreensController myController;

	// Customer selected summary

	@FXML
	private Label lblTimeSelected;

	@FXML
	private Label lblDateSelected;

	@FXML
	private Label lblFilmSelected;

	@FXML
	private Label lblDescription;

	@FXML
	private ImageView imgShowFilmImage;

	// Seating

	@FXML
	private Button btnResetSeats;

	// Subtotal and continue
	@FXML
	private Label lblSubtotal;

	@FXML
	private Button btnContinue;
	
	Image image;

	/**
	 * Initialises the controller class.
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.lblFilmSelected.setText(CustHomeController.BOOKING.getMovie().getTitle());
		this.lblDateSelected.setText(CustHomeController.BOOKING.getMovie().getDate());
		this.lblTimeSelected.setText(CustHomeController.BOOKING.getMovie().getTime());
		
		try {
			this.lblDescription.setText(getDescription(CustHomeController.BOOKING.getMovie().getTitle()));
			
			File file = new File(getImage(CustHomeController.BOOKING.getMovie().getTitle()));
	        Image image = new Image(file.toURI().toString());
	        this.imgShowFilmImage.setImage(image);
//			image = new Image(getImage(CustHomeController.BOOKING.getMovie().getTitle()));
//			this.imgShowFilmImage.setImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	@FXML
	public void goToCustConfirmPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custConfirmPageID);
	}

	public static String getDescription(String title) throws IOException {
		TextFileManager fileManager = new TextFileManager();
		List<String[]> filmList = fileManager.getFilmList();
		
		for (int i = 0; i < filmList.size(); i++) {
			if (filmList.get(i)[0].equals(title)) {
				return filmList.get(i)[2];
			}
		}
		return null;
	}
	
	public static String getImage(String title) throws IOException {
		TextFileManager fileManager = new TextFileManager();
		List<String[]> filmList = fileManager.getFilmList();
		
		for (int i = 0; i < filmList.size(); i++) {
			if (filmList.get(i)[0].equals(title)) {
				return filmList.get(i)[1];
			}
		}
		return null;
	}

}
