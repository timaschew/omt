package de.freebits.omt.core.processing.events;

import java.util.List;

import jm.music.data.Note;

/**
 * Processing event for trills.
 * 
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
