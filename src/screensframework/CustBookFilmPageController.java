package screensframework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

	@FXML
	private ListView<String> seatSummary;
	private ObservableList<String> seatList = FXCollections.observableArrayList(); // Container for selected seats
	public HashSet<String> set = new HashSet<String>();

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
		this.lblFilmSelected.setText(CustHomeController.BOOKING.getMovie().getTitle());
		this.lblDateSelected.setText(CustHomeController.BOOKING.getMovie().getDate());
		this.lblTimeSelected.setText(CustHomeController.BOOKING.getMovie().getTime());
		this.lblSubtotal.setText("£0.00");

		try {
			this.lblDescription.setText(getDescription(CustHomeController.BOOKING.getMovie().getTitle()));

			File file = new File(getImage(CustHomeController.BOOKING.getMovie().getTitle()));
			Image image = new Image(file.toURI().toString());
			this.imgShowFilmImage.setImage(image);

		} catch (IOException e) {
			e.printStackTrace();
		}

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
				} else {
					btn.setStyle("-fx-base: lightpink;");
				}

				btn.setOnAction(e -> {
					if (seats[I][J].equals("Free")) {
						btn.setStyle("-fx-base: deepskyblue;");
						seats[I][J] = LoginController.USER.getUserID();

						// Set seat summary using HashSet:
						seatSummary.getItems().clear();
						set.add(btn.getId());
						for (String values : set) {
							seatList.add(values);
						}
						seatSummary.setItems(seatList);
						lblSubtotal.setText("£" + 5 * seatList.size() + ".00");

					} else if (seats[I][J].equals(LoginController.USER.getUserID()) && btn.getStyle().equals("-fx-base: deepskyblue;")) {
						btn.setStyle("-fx-base: lightgreen;");
						seats[I][J] = "Free";

						// Set seat summary using HashSet:
						seatSummary.getItems().clear();
						set.remove(btn.getId());
						for (String values : set) {
							seatList.add(values);
						}
						seatSummary.setItems(seatList);
						lblSubtotal.setText("£" + 5 * seatList.size() + ".00");

					}
				});
			}
		}

	}

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
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

	@FXML
	public void seatClicked(ActionEvent event) {

	}

	@FXML
	public void goToCustConfirmPage(ActionEvent event) {

		if (seatList.size() > 0) { // Can only progress if seats have been selected
			
			List<String> custSeats = new ArrayList<String>();
			for (String i : seatList) {
				custSeats.add(i);
			}
			
			CustHomeController.BOOKING.setSeats(custSeats);
			CustHomeController.LISTING.setSeats(seats);
			
			myController.loadScreen(ScreensFramework.custConfirmPageID, ScreensFramework.custConfirmPageFile);
			myController.setScreen(ScreensFramework.custConfirmPageID);
		}
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
	 * @param gridPane
	 *            GridPane to be iterated through
	 * @param col
	 *            Column index
	 * @param row
	 *            Row index
	 * @return The node at the specified GridPane index
	 */
	@SuppressWarnings("unused")
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return node;
			}
		}
		return null;
	}

}
