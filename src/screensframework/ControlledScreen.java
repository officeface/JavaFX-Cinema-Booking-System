package screensframework;

/**
 * Allows the injection of the Parent ScreenPane.
 */
public interface ControlledScreen {
    
	/**
	 * Allows the injection of the Parent ScreenPane
	 * @param screenPage the ScreensController object.
	 */
    public void setScreenParent(ScreensController screenPage);
}