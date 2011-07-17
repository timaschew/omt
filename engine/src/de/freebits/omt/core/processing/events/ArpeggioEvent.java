package de.freebits.omt.core.processing.events;

import jm.music.data.Note;

import java.util.List;

/**
 * Processing event for arpeggios.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Arpeggio">Wikipedia: Arpeggio</a>
 * @author Marcel Karras
 */
public class ArpeggioEvent implements ProcessingEvent {

	private List<Note> arpeggioNotes;

	public ArpeggioEvent(final List<Note> arpeggioNotes) {
		this.arpeggioNotes = arpeggioNotes;
	}

	public final List<Note> getArpeggioNotes() {
		return arpeggioNotes;
	}

}
