package de.freebits.omt.web.beans;

import javax.faces.model.SelectItem;

/**
 * This beans provides information for an evaluable song.
 *
 * @author Marcel Karras
 */
public class Song extends SelectItem {

	// title of the song
	private String title;

	// filename of the song
	private String filename;

	/**
	 * Create a new song object by title.
	 *
	 * @param title	song title
	 * @param filename song filename with extension
	 */
	public Song(final String title, final String filename) {
		this.title = title;
		this.filename = filename;
	}

	/**
	 * Set the song's title.
	 *
	 * @param title title string
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Get the song's title.
	 *
	 * @return song's title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * Get the song's filename.
	 *
	 * @return song's filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Set the song's filename.
	 *
	 * @param filename song's filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return title;
	}
}
