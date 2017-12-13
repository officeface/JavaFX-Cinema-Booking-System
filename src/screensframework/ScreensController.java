package screensframework;

import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * 
 * <h1>Screens Controller Class</h1>
 * <p>
 * This class controls the loading and unloading of screens. This makes it much
 * easier to add/remove/change screens within the application. The code is taken
 * from Oracle's YouTube channel and was written by Angela Caicedo. Javadoc
 * comments written by Mark Backhouse.
 * </p>
 *
 */
public class ScreensController extends StackPane {

	private HashMap<String, Node> screens = new HashMap<>(); // Holds the screens

	public ScreensController() {
		super();
	}

	/**
	 * Adds the selected screen to the collection of screens.
	 * 
	 * @param name
	 *            the name of the selected screen.
	 * @param screen
	 *            the screen to be added.
	 */
	public void addScreen(String name, Node screen) {
		screens.put(name, screen);
	}

	/**
	 * 
	 * @param name
	 *            the name of the node to be returned.
	 * @return the node with the appropriate name.
	 */
	public Node getScreen(String name) {
		return screens.get(name);
	}

	/**
	 * Loads the fxml file, adds the screen to the screens collection and finally
	 * injects the screenPane to the controller.
	 * 
	 * @param name
	 *            the name of the screen to be loaded.
	 * @param resource
	 *            the fxml file to be loaded.
	 * @return true if the screen has successfully been loaded to the collection,
	 *         false otherwise.
	 */
	public boolean loadScreen(String name, String resource) {
		try {
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) myLoader.load();
			ControlledScreen myScreenController = ((ControlledScreen) myLoader.getController());
			myScreenController.setScreenParent(this);
			addScreen(name, loadScreen);
			ScreensFramework.LOGGER.info(name + " was loaded.");
			return true;
		} catch (Exception e) {
			ScreensFramework.LOGGER.warning("Failed to load: " + e.getMessage());
			return false;
		}
	}

	/**
	 * This method tries to display the screen with a predefined name. First, it
	 * makes sure that the screen has already been loaded. Then, if there is more
	 * than one screen, the new screen is added and then the current screen is
	 * removed. If no screen is being displayed, the new screen is just added to the
	 * root.
	 * 
	 * @param name
	 *            the name of the screen to be loaded.
	 * @return true if the screen is successfully loaded, false otherwise.
	 */
	public boolean setScreen(final String name) {
		if (screens.get(name) != null) { // screen loaded
			final DoubleProperty opacity = opacityProperty();

			if (!getChildren().isEmpty()) { // if there is more than one screen
				Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
						new KeyFrame(new Duration(600), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent t) {
								getChildren().remove(0); // remove the displayed screen
								getChildren().add(0, screens.get(name)); // add the screen
								Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
										new KeyFrame(new Duration(600), new KeyValue(opacity, 1.0)));
								fadeIn.play();
							}
						}, new KeyValue(opacity, 0.0)));
				fade.play();

			} else {
				setOpacity(0.0);
				getChildren().add(screens.get(name)); // no one else been displayed, then just show
				Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
						new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0)));
				fadeIn.play();
			}
			return true;
		} else { // Screen has not been loaded.
			ScreensFramework.LOGGER.warning(name + " hasn't been loaded.");
			return false;
		}

	}

	/**
	 * Removes the screen with the given name from the screens collection.
	 * 
	 * @param name the name of the screen to be unloaded.
	 * @return true if the screen was successfully unloaded, false otherwise.
	 */
	public boolean unloadScreen(String name) {
		if (screens.remove(name) == null) { // Screen wasn't open in the first place
			ScreensFramework.LOGGER.warning(name + " wasn't open.");
			return false;
		} else { // Screen found and successfully unloaded
			ScreensFramework.LOGGER.info(name + " was closed.");
			return true;
		}
	}
}