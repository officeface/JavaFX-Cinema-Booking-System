package objects;

/**
 * Defines a movie, with an associated title, image url and short description.
 * More fields could be added at a later date, such as movie rating, release
 * date, director/actor names. Movie tags could also be added, so that a
 * recommended films feature could be added. This would work by looking at a
 * Customer's booking history, finding commonly listed movie tags and matching
 * them to other movies in the database.
 * 
 * @author Mark Backhouse and Fraz Ahmad
 *
 */
public class Movie {

	private String title;
	private String image;
	private String description;

	public Movie(String title, String image, String description) {
		this.title = title;
		this.image = image;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
