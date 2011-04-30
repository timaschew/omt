package de.freebits.omt.core.processing;

import jm.music.data.Note;

/**
 * Interface for note processing processors.
 * 
 * @author Marcel Karras
 */
public interface NoteProcessor extends Processor {

	/**
	 * Process a note and evaluate it inside the processor.
	 * 
	 * @param note
	 *            note object to be processed
	 */
	public void processNote(final Note note);
}
