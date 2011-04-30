package de.freebits.omt.core.processing.events;

import java.util.List;

import jm.music.data.Note;

/**
 * Processing event for acciaccatura.
 * 
 * @author Marcel Karras
 */
public class AcciaccaturaEvent implements ProcessingEvent {

	private List<Note> acciaccaturaNotes;

	public AcciaccaturaEvent(final List<Note> acciaccaturaNotes) {
		this.acciaccaturaNotes = acciaccaturaNotes;
	}

	public final List<Note> getAcciaccaturaNotes() {
		return acciaccaturaNotes;
	}

}
