package de.freebits.omt.core.processing.events;

import jm.music.data.Note;

import java.util.List;

/**
 * Processing event for tremoli.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Tremolo">Wikipedia: Tremolo</a>
 * @author Marcel Karras
 */
public class TremoloEvent implements ProcessingEvent {

	private boolean singlePitched;
	private List<Note> tremoloNotes;

	public TremoloEvent(final boolean singlePitched, final List<Note> tremoloNotes) {
		this.singlePitched = singlePitched;
		this.tremoloNotes = tremoloNotes;
	}

	/**
	 * Check if the tremolo is single pitched.
	 * 
	 * @return true if single pitched, false else
	 */
	public final boolean isSinglePitched() {
		return singlePitched;
	}

	/**
	 * Get the tremolo note components
	 * 
	 * @return tremolo notes
	 */
	public final List<Note> getTremoloNotes() {
		return tremoloNotes;
	}

}
