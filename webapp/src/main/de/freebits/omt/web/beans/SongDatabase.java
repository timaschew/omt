package de.freebits.omt.web.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;

/**
 * This bean contains all the available songs that can be evaluated.
 *
 * @author Marcel Karras
 */
@Named
@ApplicationScoped
public class SongDatabase implements Serializable {

	// list of songs
	private SelectItem[] songList = {
			new SelectItem(Song.createSong("Alle meine Entchen", "Alle Meine Entchen.mid")),
			new SelectItem(Song.createSong("Alle Vögel Sind Schon Da", "Alle Vögel Sind Schon Da.mid"))
	};

	/**
	 * Get a song object by title.
	 *
	 * @param title the song title
	 * @return song object or null if not found
	 */
	public Song getSongByTitle(final String title) {
		for (final SelectItem songItem : songList) {
			if (((Song) songItem.getValue()).getTitle().equals(title)) {
				return (Song) songItem.getValue();
			}
		}
		return null;
	}

	/**
	 * Get the list of evaluable songs.
	 *
	 * @return song list
	 */
	public SelectItem[] getSongList() {
		return songList;
	}
}
