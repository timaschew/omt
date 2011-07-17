package de.freebits.omt.core.processing.events;

import jm.music.data.Note;

import java.util.List;

/**
 * Processing event for trills.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Trill_(music)">Wikipedia: Trill</a>
 * @author Marcel Karras
 */
public class TrillEvent implements ProcessingEvent {

	private List<Note> trillNotes;

	public TrillEvent(final List<Note> trillNotes) {
		this.trillNotes = trillNotes;
	}

	public final List<Note> getTrillNotes() {
		return trillNotes;
	}

}
