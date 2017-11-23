package screensframework;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
	private TextArea textareaDescription;

	@FXML
	private ImageView ImgShowFilmImage;

	// Seating

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

	}

	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}

	@FXML
	public void goToCustConfirmPage(ActionEvent event) {
		myController.setScreen(ScreensFramework.custConfirmPageID);
	}

	public void setLblTimeSelected(String time) {
		this.lblTimeSelected.setText(time);
	}

	public void setLblDateSelected(String date) {
		this.lblDateSelected.setText(date);
	}

	public void setLblFilmSelected(String film) {
		this.lblFilmSelected.setText(film);
	}

}
