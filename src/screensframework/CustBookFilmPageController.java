package screensframework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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
	Image image;
	
	// Seating
	String[][] seats = CustHomeController.LISTING.getSeats();
	
	@FXML
	private GridPane seatLayout;
	

	@FXML
	private Button btnResetSeats;

	// Subtotal and continue
	@FXML
	private Label lblSubtotal;

	@FXML
	private Button btnContinue;
	
	

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
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Generate the seats according to the listing information:
		for (int i = 0; i < seatLayout.getRowCount(); i++) {
			for (int j = 0; j < seatLayout.getColumnCount(); j++) {
				Button btn = new Button();
				Integer I = (Integer)i;
				Integer J = (Integer)j;
				seatLayout.add(btn, j, i);
				btn.setPrefSize(40, 30);
				btn.setId("btn" + I.toString() + J.toString());				
				
				// Check if seat is available:
				if (seats[i][j].equals("Free")) {
				} else {
					btn.setStyle("-fx-base: #ff0000;");
				}
				
				btn.setOnAction(e -> {
					if (seats[I][J].equals("Free")) {
						btn.setStyle("-fx-base: #b6e7c9;");
					}
				});
			}
		}
		
		
		
		

	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
	
	@FXML
	public void seatClicked(ActionEvent event) {
		
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
	
	/**
	 * 
	 * @param gridPane GridPane to be iterated through
	 * @param col Column index
	 * @param row Row index
	 * @return The node at the specified GridPane index
	 */
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            return node;
	        }
	    }
	    return null;
	}

}
