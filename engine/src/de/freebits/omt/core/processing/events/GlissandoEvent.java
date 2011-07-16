package de.freebits.omt.core.processing.events;

import jm.music.data.Note;

import java.util.List;

/**
 * Processing event for glissando.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Glissando">Wikipedia: Glissando</a>
 * @author Marcel Karras
 */
public class GlissandoEvent implements ProcessingEvent {

	private List<Note> glissandoNotes;

	public GlissandoEvent(final List<Note> glissandoNotes) {
		this.glissandoNotes = glissandoNotes;
	}

	public final List<Note> getGlissandoNotes() {
		return glissandoNotes;
	}

}
