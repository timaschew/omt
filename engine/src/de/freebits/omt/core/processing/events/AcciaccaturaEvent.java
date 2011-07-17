package de.freebits.omt.core.processing.events;

import jm.music.data.Note;

import java.util.List;

/**
 * Processing event for acciaccatura.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Acciaccatura">Wikipedia: Acciaccatura</a>
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
