/**
 * 
 */
package de.freebits.omt.core.processing;

import java.util.ArrayList;
import java.util.List;

import jm.constants.Durations;
import jm.music.data.Note;
import de.freebits.omt.core.AcousticConstants;
import de.freebits.omt.core.processing.events.ArpeggioEvent;

/**
 * Processor for arpeggio recognitions.
 * 
 * @author Marcel Karras
 */
public class ArpeggioProcessor extends DefaultProcessor implements NoteProcessor {

	// half note is the maximum duration value for a note inside an arpeggio
	private static final double MAX_DURATION_VALUE = Durations.HALF_NOTE;
	// maximum of time offset in seconds between arpeggio notes (here 100ms)
	private static final double MAX_DIFF_BETWEEN_NOTES = 0.1;
	// minimum of 3 notes per arpeggio
	private static final int MIN_NOTES_COUNT = 3;
	// the note components of the arpeggio
	private List<Note> arpeggioNotes = new ArrayList<Note>();

	@Override
	public final void processNote(final Note note) {
		boolean meetRequirements = false;
		boolean meetStartNoteReq = false;
		boolean movementUp;
		if (arpeggioNotes.size() > 1)
			movementUp = arpeggioNotes.get(0).getPitch() < arpeggioNotes.get(1).getPitch();
		else
			movementUp = true;
		// check duration limit
		if (note.getDuration() <= MAX_DURATION_VALUE && !note.isRest()) {
			meetStartNoteReq = true;
			// compare to starting note
			if (arpeggioNotes.size() > 0) {
				final Note lastNote = arpeggioNotes.get(arpeggioNotes.size() - 1);
				if (arpeggioNotes.size() == 1) {
					if (arpeggioNotes.get(0).getPitch() > note.getPitch()) {
						movementUp = false;
					}
					meetRequirements = true;
				} else {
					// check the distance between the notes
					final double timeDiff = note.getSampleStartTime()
							- lastNote.getSampleStartTime();
					if (timeDiff > AcousticConstants.MIN_DURATION_DIFFERENCE
							&& timeDiff < MAX_DIFF_BETWEEN_NOTES) {
						if (movementUp && lastNote.getPitch() < note.getPitch()) {
							meetRequirements = true;
						} else if (!movementUp && lastNote.getPitch() > note.getPitch()) {
							meetRequirements = true;
						}
					}
				}
			} else {
				// current note is starting note
				meetRequirements = true;
			}
		}
		// 2. reset/finish the processor if the requirements are broken
		if (!meetRequirements) {

			// abortion without a valid arpeggio occurrence
			if (arpeggioNotes.size() > 0 && arpeggioNotes.size() < MIN_NOTES_COUNT
					&& !note.isRest()) {
				arpeggioNotes.remove(0);
				processNote(note);
				return;
			}
			finish();
			// feed the processor with the last note if it meets the start node
			// requirements
			if (meetStartNoteReq) {
				arpeggioNotes.add(note);
			}
			return;
		} else {
			arpeggioNotes.add(note);
		}
	}

	@Override
	public final void reset() {
		arpeggioNotes.clear();
	}

	@Override
	public void finish() {
		if (arpeggioNotes.size() >= MIN_NOTES_COUNT) {
			// signal trill event to all registered listeners
			signal(new ArpeggioEvent(arpeggioNotes));
		}
		// reset the processor
		reset();
	}
}
