package de.freebits.omt.core.processing;

import de.freebits.omt.core.processing.events.TremoloEvent;
import de.freebits.omt.core.rhythm.RhythmDistanceHelper;
import jm.constants.Durations;
import jm.music.data.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * Processor for tremolo recognitions.
 * 
 * @author Marcel Karras
 */
public class TremoloProcessor extends DefaultProcessor implements NoteProcessor {

	// 16th note is the maximum duration value for a note inside a trill
	private static final double MAX_DURATION_VALUE = Durations.QUARTER_NOTE;
	// minimum of 4 notes per tremolo
	private static final int MIN_NOTES_COUNT = 4;
	// 34% speed change from one note to another is allowed (quarter to triplet)
	private static final double MAX_DURATION_CHANGE = 0.34;
	// the note components of the tremolo
	private List<Note> tremoloNotes = new ArrayList<Note>();
	// property for single pitched or double pitched tremoli
	private boolean singlePitched = false;

	public TremoloProcessor() {
	}

	@Override
	public final void processNote(final Note note) {
		boolean meetRequirements = false;
		boolean meetStartNoteReq = false;
		if (tremoloNotes.size() > 1)
			singlePitched = tremoloNotes.get(0).getPitch() == tremoloNotes.get(1).getPitch();
		else
			singlePitched = false;
		// check duration limit
		if (note.getDuration() <= MAX_DURATION_VALUE && !note.isRest()) {
			meetStartNoteReq = true;
			// compare to starting note
			if (tremoloNotes.size() > 0) {
				final Note lastNote = tremoloNotes.get(tremoloNotes.size() - 1);
				// check if note duration changes are inside the allowed
				// boundaries
				if (RhythmDistanceHelper.isInsideDurationChangeBounds(lastNote, note,
						MAX_DURATION_CHANGE)) {
					// only the starting note is already inserted
					if (tremoloNotes.size() == 1) {
						// single pitched tremolo?
						if (tremoloNotes.get(0).getPitch() == note.getPitch()) {
							singlePitched = true;
						}
						meetRequirements = true;
					} else {
						final Note preLastNode = tremoloNotes.get(tremoloNotes.size() - 2);
						if (singlePitched) {
							// single pitched: no pitch change allowed
							if (lastNote.getPitch() == note.getPitch()) {
								meetRequirements = true;
							}
						} else {
							// double pitched: toggled pitch change
							if (lastNote.getPitch() != note.getPitch()
									&& preLastNode.getPitch() == note.getPitch()) {
								meetRequirements = true;
							}
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
			// abortion without a valid tremolo occurrence
			if (tremoloNotes.size() > 0 && tremoloNotes.size() < MIN_NOTES_COUNT && !note.isRest()) {
				tremoloNotes.remove(0);
				processNote(note);
				return;
			}
			finish();
			// feed the processor with the last note if it meets the start node
			// requirements
			if (meetStartNoteReq) {
				tremoloNotes.add(note);
			}
			return;
		} else {
			tremoloNotes.add(note);
		}
	}

	@Override
	public final void reset() {
		tremoloNotes.clear();
		singlePitched = false;
	}

	@Override
	public void finish() {
		if (tremoloNotes.size() >= MIN_NOTES_COUNT) {
			// signal trill event to all registered listeners
			signal(new TremoloEvent(singlePitched, tremoloNotes));
		}
		// reset the processor
		reset();
	}

}
