package de.freebits.omt.core.processing.events;

import jm.music.data.Note;

import java.util.List;

/**
 * Processing event for arpeggios.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Chord_(music)">Wikipedia: Chord</a>
 * @author Marcel Karras
 */
public class ChordEvent implements ProcessingEvent {

	private List<Note> chordNotes;

	public ChordEvent(final List<Note> chordNotes) {
		this.chordNotes = chordNotes;
	}

	public final List<Note> getChordNotes() {
		return chordNotes;
	}

}
