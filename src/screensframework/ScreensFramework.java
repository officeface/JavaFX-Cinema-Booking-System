package screensframework;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
	


public class ScreensFramework extends Application {
    
    //Declaring the files
    public static String loginID = "main";
    public static String loginFile = "Login.fxml";
    public static String staffHomeID = "staffHome";
    public static String staffHomeFile = "StaffHome.fxml";
    public static String staffChoiceID = "staffChoice";
    public static String staffChoiceFile = "StaffChoicePage.fxml";
    public static String custHomeID = "custHome";
    public static String custHomeFile = "CustHome.fxml";
    public static String custProfilePageID = "custProfilePage";
    public static String custProfilePageFile = "CustProfilePage.fxml";
    
    //Staff only files Excluding: staff home
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
        mainContainer.loadScreen(ScreensFramework.staffHomeID, ScreensFramework.staffHomeFile);
        mainContainer.loadScreen(ScreensFramework.staffChoiceID, ScreensFramework.staffChoiceFile);
        mainContainer.loadScreen(ScreensFramework.custHomeID, ScreensFramework.custHomeFile);
        mainContainer.loadScreen(ScreensFramework.custProfilePageID, ScreensFramework.custProfilePageFile);
        
        //for staff screens Excluding: staff home
        mainContainer.loadScreen(ScreensFramework.staffExportID, ScreensFramework.staffExportFile);
        mainContainer.loadScreen(ScreensFramework.bookingSummaryID, ScreensFramework.bookingSummaryFile);
        mainContainer.loadScreen(ScreensFramework.addFilmPageID, ScreensFramework.addFilmPageFile);
        mainContainer.loadScreen(ScreensFramework.addFilmListingsID, ScreensFramework.addFilmListingsFile);
        
        
        //First loading screen
        mainContainer.setScreen(ScreensFramework.loginID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        //File file = new File(".");
        //for(String fileNames : file.list()) System.out.println(fileNames);
    }
}