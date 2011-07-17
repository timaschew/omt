package de.freebits.omt.core.processing;

import de.freebits.omt.core.harmony.HarmonyHelper;
import de.freebits.omt.core.harmony.Scales;
import de.freebits.omt.core.processing.events.GlissandoEvent;
import jm.music.data.Note;
import jm.music.data.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Processor for glissando (slides between two notes) recognitions.
 * <h3>Conditions to be met for glissando recognition:</h3>
 * <ul>
 * <li>minimum of 4 notes</li>
 * <li>maximum note duration of 150ms</li>
 * <li>must be in C major or F# major scale</li>
 * <li>movement direction must be kept over all notes (otherwise more than one glissando)</li>
 * <li>maximum pause of 50ms between glissando notes</li>
 * <li>maximum of 3 semitone steps in movement</li>
 * <li>minimum of 25ms onset differences from one note to the next note</li>
 * </ul>
 *
 * @author Marcel Karras
 * @see GlissandoEvent
 */
public class GlissandoProcessor extends DefaultProcessor implements NoteProcessor {

    /**
     * maximum of note duration time in seconds
     */
    private static final double MAX_NOTE_DURATION = 0.15;
    /**
     * maximum pause of 50ms between glissando notes
     */
    private static final double MAX_NOTE_PAUSE = 0.05;
    /**
     * maximum of 3 semitone steps in movement
     */
    private static final double MAX_PITCH_STEP = 3;
    /**
     * minimum of 4 notes
     */
    private static final int MIN_NOTES_COUNT = 4;

    /**
     * Cmaj scale
     */
    private static final byte[] C_MAJOR_SCALE = Scales.MAJOR_SCALE;
    /**
     * F#maj scale
     */
    private static final byte[] F_MAJOR_SCALE = HarmonyHelper.shiftScale(Scales.MAJOR_SCALE, 6);
    /**
     * the candidate notes
     */
    private List<Note> glissandoNotes = new ArrayList<Note>();

    @Override
    public final void processNote(final Note note) {
        // variable indicating a condition that qualifies a note for being part of note list
        boolean meetRequirements = false;
        // variable indicating a condition that qualifies a note for being first note
        boolean meetStartNoteReq = false;
        boolean movementUp;
        final Score s = note.getMyPhrase().getMyPart().getMyScore();

        double noteDuration = note.getRhythmValue() * 60.0 / s.getTempo();
        if (glissandoNotes.size() > 1)
            movementUp = glissandoNotes.get(0).getPitch() < glissandoNotes.get(1).getPitch();
        else
            movementUp = true;
        // basic conditions: scale, duration
        if (HarmonyHelper.isNoteInScales(note, Arrays.asList(C_MAJOR_SCALE, F_MAJOR_SCALE)) &&
                noteDuration <= MAX_NOTE_DURATION && !note.isRest()) {
            meetStartNoteReq = true;
            // compare to starting note
            if (glissandoNotes.size() > 0) {
                final Note lastNote = glissandoNotes.get(glissandoNotes.size() - 1);
                double lastNoteDuration = lastNote.getRhythmValue() * 60.0 / s.getTempo();
                if (glissandoNotes.size() == 1) {
                    // solo note always meets requirements - TODO: if entfernen
                    meetRequirements = true;
                } else {
                    // condition: pause between notes, pitch difference
                    if (note.getSampleStartTime() - (lastNote.getSampleStartTime() +
                            lastNoteDuration) <= MAX_NOTE_PAUSE &&
                            Math.abs(lastNote.getPitch() - note.getPitch()) <= MAX_PITCH_STEP) {
                        // check if the movement is kept
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

            // abortion without a valid glissando occurrence
            if (glissandoNotes.size() > 0 && glissandoNotes.size() < MIN_NOTES_COUNT
                    && !note.isRest()) {
                // here the glissando detection failed too soon to build a full one,
                // so remove first note and retry using the remaining ones
                glissandoNotes.remove(0);
                // recursive reprocessing of the current note without first candidate note
                processNote(note);
                return;
            }
            finish();
            // feed the processor with the last note if it meets the start node requirements
            if (meetStartNoteReq) {
                glissandoNotes.add(note);
            }
        } else {
            glissandoNotes.add(note);
        }
    }

    @Override
    public final void reset() {
        glissandoNotes.clear();
    }

    @Override
    public void finish() {
        if (glissandoNotes.size() >= MIN_NOTES_COUNT) {
            // signal event to all registered listeners
            signal(new GlissandoEvent(glissandoNotes));
        }
        // reset the processor
        reset();
    }
}
