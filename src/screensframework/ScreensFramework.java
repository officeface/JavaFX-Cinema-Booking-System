package screensframework;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreensFramework extends Application {

	// Declaring the LOGIN
	public static String loginID = "main";
	public static String loginFile = "Login.fxml";
	
	// Registration
	public static String registrationID = "registrationPage";
	public static String registrationFile = "RegistrationPage.fxml";

	// Customer only files
	public static String custHomeID = "custHome";
	public static String custHomeFile = "CustHome.fxml";
	public static String custProfilePageID = "custProfilePage";
	public static String custProfilePageFile = "CustProfilePage.fxml";
	public static String custBookingHistoryPageID = "custBookingHistoryPage";
	public static String custBookingHistoryPageFile = "CustBookingHistoryPage.fxml";
	public static String custBookFilmPageID = "custBookFilmPage";
	public static String custBookFilmPageFile = "CustBookFilmPage.fxml";
	public static String custConfirmPageID = "custConfirmPage";
	public static String custConfirmPageFile = "CustConfirmPage.fxml";

	// Staff only files
	public static String staffChoiceID = "staffChoice";
	public static String staffChoiceFile = "StaffChoicePage.fxml";
	public static String staffHomeID = "staffHome";
	public static String staffHomeFile = "StaffHome.fxml";
	public static String staffExportID = "staffExport";
	public static String staffExportFile = "StaffExport.fxml";
	public static String bookingSummaryID = "bookingSummary";
	public static String bookingSummaryFile = "BookingSummary.fxml";
	public static String addFilmPageID = "addFilmPage";
	public static String addFilmPageFile = "AddFilmPage.fxml";
	public static String addFilmListingsID = "addFilmListings";
	public static String addFilmListingsFile = "AddFilmListings.fxml";

	@Override
	public void start(Stage primaryStage) {

		ScreensController mainContainer = new ScreensController();
		mainContainer.loadScreen(ScreensFramework.loginID, ScreensFramework.loginFile);

		// for customer screens
		// mainContainer.loadScreen(ScreensFramework.custHomeID,
		// ScreensFramework.custHomeFile);
		// mainContainer.loadScreen(ScreensFramework.custProfilePageID,
		// ScreensFramework.custProfilePageFile);
		// mainContainer.loadScreen(ScreensFramework.custBookFilmPageID,
		// ScreensFramework.custBookFilmPageFile);
		// mainContainer.loadScreen(ScreensFramework.custConfirmPageID,
		// ScreensFramework.custConfirmPageFile);

		// for staff screens
		// mainContainer.loadScreen(ScreensFramework.staffChoiceID,
		// ScreensFramework.staffChoiceFile);
		// mainContainer.loadScreen(ScreensFramework.staffHomeID,
		// ScreensFramework.staffHomeFile);
		// mainContainer.loadScreen(ScreensFramework.staffExportID,
		// ScreensFramework.staffExportFile);
		// mainContainer.loadScreen(ScreensFramework.bookingSummaryID,
		// ScreensFramework.bookingSummaryFile);
		// mainContainer.loadScreen(ScreensFramework.addFilmPageID,
		// ScreensFramework.addFilmPageFile);
		// mainContainer.loadScreen(ScreensFramework.addFilmListingsID,
		// ScreensFramework.addFilmListingsFile);

		// First loading screen
		mainContainer.setScreen(ScreensFramework.loginID);
		
		

		Group root = new Group();
		root.getChildren().addAll(mainContainer);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
		// Defining the stylesheet 
		// load the stylesheet
		String style = getClass().getResource("ScreensFramework.css").toExternalForm();
		// apply stylesheet to the scene graph
		scene.getStylesheets().addAll(style);

	}

	public static void main(String[] args) {
		launch(args) ;
	}
}