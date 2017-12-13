package objects;

/**
 * Employee inherits the basic properties of the User class. Specific Employee
 * methods could be added at a later date.
 * 
 * @author Mark Backhouse and Fraz Ahmad
 *
 */
public class Employee extends User {

	public Employee(String userID, String email, String password, String firstName, String lastName) {
		super(userID, email, password, firstName, lastName);
	}

}
