/**
 * 
 */
package de.freebits.omt.core.processing;

import java.util.ArrayList;
import java.util.List;

import jm.music.data.Note;
import jm.music.data.Score;
import de.freebits.omt.core.AcousticConstants;
import de.freebits.omt.core.processing.events.AcciaccaturaEvent;

/**
 * Processor for acciaccatura (short prebeat notes) recognitions.
 * 
 * @author Marcel Karras
 */
public class AcciaccaturaProcessor extends DefaultProcessor implements NoteProcessor {

	// maximum allowed rhythm value is lower than a 32nd
	private static final double MAX_RHYTHM_VALUE = 0.1;
	// maximum of time offset in seconds to the main note
	private static final double MAX_OFFSET_TO_MAIN_NOTE = 0.1;
	// maximum of note duration time in seconds
	private static final double MAX_NOTE_DURATION = 0.1;
	// minimum of 1 note per acciaccatura
	private static final int MIN_NOTES_COUNT = 1;
	// the note components of the acciaccatura
	private List<Note> acciaccaturaNotes = new ArrayList<Note>();

	@Override
	public final void processNote(final Note note) {
		final Score s = note.getMyPhrase().getMyPart().getMyScore();
		double rhythmDuration = note.getRhythmValue() * 60.0 / s.getTempo();
		if (acciaccaturaNotes.size() == 0) {
			// restriction: note duration
			if (note.getRhythmValue() <= MAX_RHYTHM_VALUE && rhythmDuration <= MAX_NOTE_DURATION
					&& !note.isRest()) {
				acciaccaturaNotes.add(note);
			} else {
				// no suitable note found
				reset();
			}
		} else {
			// acciaccaturaNotes length is 1
			assert acciaccaturaNotes.size() == 1;
			// restriction: note duration
			final Note accNote = acciaccaturaNotes.get(0);
			if (!note.isRest()) {
				// restriction: time diff to main note
				final double timeDiff = note.getSampleStartTime() - accNote.getSampleStartTime();
				if (timeDiff > AcousticConstants.MIN_DURATION_DIFFERENCE
						&& timeDiff < MAX_OFFSET_TO_MAIN_NOTE) {
					finish();
					return;
				}
			}
			// reset if the current candidate is too bad
			reset();
		}
	}

	@Override
	public final void reset() {
		acciaccaturaNotes.clear();
	}

	@Override
	public void finish() {
		if (acciaccaturaNotes.size() >= MIN_NOTES_COUNT) {
			// signal event to all registered listeners
			signal(new AcciaccaturaEvent(acciaccaturaNotes));
		}
		// reset the processor
		reset();
	}
}
