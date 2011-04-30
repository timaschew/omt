package de.freebits.omt.core.processing.events;

import java.util.List;

import jm.music.data.Note;

/**
 * Processing event for arpeggios.
 * 
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
