package de.freebits.omt.core.processing;

import de.freebits.omt.core.harmony.HarmonyHelper;
import de.freebits.omt.core.processing.events.ArpeggioEvent;
import de.freebits.omt.core.processing.events.ChordEvent;
import de.freebits.omt.core.structures.Chord;
import de.freebits.omt.core.structures.MusicEvent;
import de.freebits.omt.core.structures.MusicEventNote;
import jm.music.data.Note;
import jm.music.data.Score;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Processor for chord recognitions.
 * <h3>Conditions to be met for chord recognition:</h3>
 * <ul>
 * <li>minimum of 2 notes occuring on same time with same duration</li>
 * <li>minimum of 2 different note chars (neutral chords, i.e. c3-g3-c4)</li>
 * <li>maximum of 4 different note chars (up to sept chord variations)</li>
 * <li>have to be in scale of the given piece</li>
 * <li>minimum of 3 semitones between adjacent notes</li>
 * <li>no pause between notes</li>
 * <li>unique absolute pitches</li>
 * </ul>
 *
 * @author Marcel Karras
 * @see de.freebits.omt.core.processing.events.ArpeggioEvent
 */
public class ChordProcessor extends DefaultProcessor implements NoteProcessor {

    /**
     * minimum of 2 notes
     */
    private static final int MIN_NOTES_COUNT = 2;
    /**
     * the note components of the chord
     */
    private List<Note> chordNotes = new ArrayList<Note>();

    /**
     * Initialize chord processor.
     */
    public ChordProcessor() {
        // nothing to do here yet
    }

    @Override
    public final void processNote(final Note note) {
        boolean meetRequirements = false;
        boolean meetStartNoteReq = false;
        final Score s = note.getMyPhrase().getMyPart().getMyScore();

        assert (note instanceof MusicEventNote);
        // check if the chord connection is existent
        final MusicEvent currentME = ((MusicEventNote) note).getMusicEvent();
        if (currentME instanceof Chord) {
            meetStartNoteReq = true;
            if (chordNotes.size() > 0) {
                final MusicEvent startNoteME = ((MusicEventNote) chordNotes.get(0)).getMusicEvent();
                // ensure that this note belongs to the same Chord object
                if (startNoteME.equals(currentME)) {
                    meetRequirements = true;
                }
                // chord objects are different
                else {
                    meetRequirements = false;
                }
            } else {
                // current note is starting note
                meetRequirements = true;
            }
        }

        if (!meetRequirements) {
            // abort: current note doesn't fit into current chord, so try without first note
            if (chordNotes.size() > 0 && chordNotes.size() < MIN_NOTES_COUNT && !note.isRest()) {
                chordNotes.remove(0);
                // reprocess the current note
                processNote(note);
                return;
            }
            // finish the processor and signal chord if conditions are met
            finish();
            // feed the processor with the last note if it meets the start node requirements
            if (meetStartNoteReq) {
                chordNotes.add(note);
            }

        } else {
            // current note meets requirements, so add it as chord note member
            chordNotes.add(note);
        }
    }

    @Override
    public final void reset() {
        chordNotes.clear();
    }

    @Override
    public void finish() {
        // condition: minimum of 2 notes
        if (chordNotes.size() >= MIN_NOTES_COUNT) {
            // condition: maximum of 4 different note chars and minimum of 2 different note chars
            //if (meetNoteCharsCondition(null)) {
                // signal chord event to all registered listeners
            signal(new ChordEvent(chordNotes));
            //}
        }
        // reset the processor
        reset();
    }
}
