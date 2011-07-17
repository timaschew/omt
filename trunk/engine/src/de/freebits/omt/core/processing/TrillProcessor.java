package de.freebits.omt.core.processing;

import de.freebits.omt.core.harmony.HarmonyHelper;
import de.freebits.omt.core.processing.events.TrillEvent;
import de.freebits.omt.core.rhythm.RhythmDistanceHelper;
import jm.constants.Durations;
import jm.music.data.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Processor for trill recognitions.
 * 
 * @author Marcel Karras
 */
public class TrillProcessor extends DefaultProcessor implements NoteProcessor {

	// 16th note is the maximum duration value for a note inside a trill
	private static final double MAX_DURATION_VALUE = Durations.SIXTEENTH_NOTE;
	// minimum of 4 notes per trill
	private static final int MIN_NOTES_COUNT = 4;
	// 34% speed change from one note to another is allowed (quarter to triplet)
	private static final double MAX_DURATION_CHANGE = 0.34;
	// the scale to be used on processing
	private byte[] scale;
	// the note components of the trill
	private List<Note> trillNotes = new ArrayList<Note>();

	public TrillProcessor(final byte[] scale) {
		this.scale = scale;
	}

	@Override
	public final void processNote(final Note note) {
		boolean meetRequirements = false;
		boolean meetStartNoteReq = false;
		// check duration limit
		if (note.getDuration() <= MAX_DURATION_VALUE && !note.isRest()) {
			// check note scale
			if (HarmonyHelper.isNoteInScale(note, scale)) {
				meetStartNoteReq = true;
				// compare to starting note
				if (trillNotes.size() > 0) {
					if (trillNotes.get(0).getPitch() == note.getPitch()
							|| HarmonyHelper.isNoteNeighbor(trillNotes.get(0), note, scale)) {
						// compare to previous note
						final Note lastNote = trillNotes.get(trillNotes.size() - 1);
						if (lastNote.getPitch() != note.getPitch()) {
							if (RhythmDistanceHelper.isInsideDurationChangeBounds(lastNote, note,
									MAX_DURATION_CHANGE)) {
								meetRequirements = true;
							}
						}
					}
				} else {
					// current note is starting note
					meetRequirements = true;
				}
			}
		}
		// 2. reset/finish the processor if the requirements are broken
		if (!meetRequirements) {
			// abortion without a valid trill occurrence
			if (trillNotes.size() > 0 && trillNotes.size() < MIN_NOTES_COUNT && !note.isRest()) {
				trillNotes.remove(0);
				processNote(note);
				return;
			}
			finish();
			// feed the processor with the last note if it meets the start node
			// requirements
			if (meetStartNoteReq) {
				trillNotes.add(note);
			}
			return;
		} else {
			trillNotes.add(note);
		}
	}

	@Override
	public final void reset() {
		trillNotes.clear();
	}

	@Override
	public void finish() {
		if (trillNotes.size() >= MIN_NOTES_COUNT) {
			// signal trill event to all registered listeners
			signal(new TrillEvent(trillNotes));
		}
		// reset the processor
		reset();
	}

}
