import javax.inject.Inject;
import javax.inject.Named;

@Named
public class Song {
	private String title;

	@Inject
	public Song(final String title){
		this.title = title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public final String getTitle() {
		return title;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		return title;
	}
}
