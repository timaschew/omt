package de.freebits.omt.core.processing;

import de.freebits.omt.core.AcousticConstants;
import de.freebits.omt.core.processing.events.AcciaccaturaEvent;
import jm.music.data.Note;
import jm.music.data.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Processor for acciaccatura (short prebeat notes) recognitions.
 * <h3>Conditions to be met for acciaccatura recognition:</h3>
 * <ul>
 * <li>exactly 2 notes</li>
 * <li>maximum prebeat note duration of 100ms</li>
 * <li>note pitch of prebeat note has to be different from main note</li>
 * <li>maximum pause of 20ms between two notes</li>
 * <li>maximum of 2 semitone steps in movement</li>
 * <li>minimum of 25ms onset differences between prebeat and main note</li>
 * </ul>
 *
 * @author Marcel Karras
 * @see AcciaccaturaEvent
 */
public class AcciaccaturaProcessor extends DefaultProcessor implements NoteProcessor {

    /**
     * exactly 2 notes
     */
    private static final int NOTES_COUNT = 2;
    /**
     * maximum of prebeat note duration time in seconds
     */
    private static final double MAX_PREBEAT_NOTE_DURATION = 0.1;
    /**
     * maximum pause of 50ms between glissando notes
     */
    private static final double MAX_OFFSET_TO_MAIN_NOTE = 0.02;
    /**
     * maximum of 3 semitone steps in movement
     */
    private static final double MAX_PITCH_STEP = 2;
    /**
     * minimum of 25ms onset differences between prebeat and main note
     */
    private static final double MIN_ONSET_DIFFERENCE = AcousticConstants.MIN_DURATION_DIFFERENCE;
    /**
     * the note components of the acciaccatura
     */
    private List<Note> acciaccaturaNotes = new ArrayList<Note>();

    @Override
    public final void processNote(final Note note) {
        final Score s = note.getMyPhrase().getMyPart().getMyScore();
        double noteDuration = note.getRhythmValue() * 60.0 / s.getTempo();
        if (acciaccaturaNotes.size() == 0) {
            // condition: note duration
            if (noteDuration <= MAX_PREBEAT_NOTE_DURATION && !note.isRest()) {
                // add prebeat note candidate
                acciaccaturaNotes.add(note);
            } else {
                // no suitable note found
                reset();
            }
        } else {
            // acciaccaturaNotes length is 1
            assert acciaccaturaNotes.size() == 1;
            final Note accNote = acciaccaturaNotes.get(0);
            if (!note.isRest()) {
                double accNoteDuration = accNote.getRhythmValue() * 60.0 / s.getTempo();
                // start time difference
                final double onsetTimeDiff = note.getSampleStartTime() -
                        accNote.getSampleStartTime();
                // pause time between acciaccatura note and main note
                final double pauseTime = note.getSampleStartTime() -
                        (accNote.getSampleStartTime() + accNoteDuration);
                // condition: time diff to main note
                if (onsetTimeDiff > MIN_ONSET_DIFFERENCE &&
                        pauseTime < MAX_OFFSET_TO_MAIN_NOTE) {
                    finish();
                    return;
                }
            }
            // reset if the current candidate is too bad
            reset();
            acciaccaturaNotes.add(note);
        }
    }

    @Override
    public final void reset() {
        acciaccaturaNotes.clear();
    }

    @Override
    public void finish() {
        if (acciaccaturaNotes.size() > 0 && acciaccaturaNotes.size() < NOTES_COUNT) {
            // signal event to all registered listeners
            signal(new AcciaccaturaEvent(acciaccaturaNotes));
        }
        // reset the processor
        reset();
    }
}
