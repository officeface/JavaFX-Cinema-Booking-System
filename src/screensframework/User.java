package screensframework;

/**
 * Generic user with email, password, first name and last name
 * @author mark
 *
 */
public class User {
	
	private String userID;
	private String email;
	private String password;
	private String type;
	private String firstName;
	private String lastName;
	
	public User(String userID, String email, String password, String firstName, String lastName) {
		this.userID = userID;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getUserID() {
		return userID;
	}

	public String getType() {
		return type;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void clearDetails() {
		setEmail(null);
		setPassword(null);
		setFirstName(null);
		setLastName(null);
		
	}

}
