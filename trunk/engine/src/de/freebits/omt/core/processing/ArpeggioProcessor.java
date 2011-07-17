package de.freebits.omt.core.processing;

import de.freebits.omt.core.harmony.HarmonyHelper;
import de.freebits.omt.core.processing.events.ArpeggioEvent;
import jm.music.data.Note;
import jm.music.data.Score;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Processor for arpeggio (tied only atm) recognitions.
 * <h3>Conditions to be met for glissando recognition:</h3>
 * <ul>
 * <li>minimum of 3 notes</li>
 * <li>minimum of 2 different note chars (neutral chords, i.e. c3-g3-c4)</li>
 * <li>maximum of 4 different note chars (up to sept chord variations)</li>
 * <li>have to be in scale of the given piece</li>
 * <li>movement direction must be kept over all notes (otherwise more than one arpeggio)</li>
 * <li>minimum of 3 semitones between adjacent notes</li>
 * <li>no pause between notes</li>
 * <li>unique absolute pitches</li>
 * <li>minimum of 10ms for inter-onset-intervals</li>
 * <li>TODO: restrictions on end times (tied arpeggios should end up nearly the same)</li>
 * </ul>
 *
 * @author Marcel Karras
 * @see ArpeggioEvent
 */
public class ArpeggioProcessor extends DefaultProcessor implements NoteProcessor {

    /**
     * minimum of 3 notes
     */
    private static final int MIN_NOTES_COUNT = 3;
    /**
     * minimum of 2 different note chars (neutral chords, i.e. c3-g3-c4)
     */
    private static final int MIN_NOTE_CHARS = 2;
    /**
     * maximum of 4 different note chars (up to sept chord variations)
     */
    private static final int MAX_NOTE_CHARS = 4;
    /**
     * minimum of 3 semitones between adjacent notes
     */
    private static final int MIN_PITCH_DISTANCE = 3;
    /**
     * minimum of 10ms for inter-onset-intervals
     */
    private static final double MIN_IOI_SECONDS = 0.010;
    /**
     * the note components of the arpeggio
     */
    private List<Note> arpeggioNotes = new ArrayList<Note>();
    /**
     * the scale to be used on processing
     */
    private byte[] scale;

    /**
     * Initialize arpeggio processor with the given scale.
     *
     * @param scale the scale arpeggios will be recognized in
     */
    public ArpeggioProcessor(final byte[] scale) {
        this.scale = scale;
    }

    @Override
    public final void processNote(final Note note) {
        boolean meetRequirements = false;
        boolean meetStartNoteReq = false;
        boolean movementUp;
        final Score s = note.getMyPhrase().getMyPart().getMyScore();
        // calculate the arpeggio movement
        if (arpeggioNotes.size() == 1) {
            movementUp = arpeggioNotes.get(0).getPitch() < note.getPitch();
        } else if (arpeggioNotes.size() > 1)
            movementUp = arpeggioNotes.get(0).getPitch() < arpeggioNotes.get(1).getPitch();
        else
            movementUp = true;
        // condition for each note: note in scale and no rest
        if (HarmonyHelper.isNoteInScale(note, scale) && !note.isRest()) {
            // this note could be taken as start note
            meetStartNoteReq = true;
            if (arpeggioNotes.size() > 0) {
                // assume requirements are met and false them if not
                meetRequirements = true;
                // current note is arpeggio note candidate
                final Note lastNote = arpeggioNotes.get(arpeggioNotes.size() - 1);
                double lastNoteDuration = lastNote.getRhythmValue() * 60.0 / s.getTempo();
                // condition: movement has to be kept
                if (movementUp && lastNote.getPitch() >= note.getPitch()) {
                    meetRequirements = false;
                } else if (!movementUp && lastNote.getPitch() <= note.getPitch()) {
                    meetRequirements = false;
                }
                // condition: no pause between notes
                if (meetRequirements && (lastNote.getSampleStartTime() + lastNoteDuration) <=
                        note.getSampleStartTime()) {
                    meetRequirements = false;
                }
                // condition: minimum of 10ms for inter-onset-intervals
                if (meetRequirements && note.getSampleStartTime() - lastNote.getSampleStartTime()
                        < MIN_IOI_SECONDS) {
                    meetRequirements = false;
                }
                // condition: unique pitches
                if (meetRequirements && isPitchPresent(note.getPitch())) {
                    meetRequirements = false;
                }
                // condition: maximum of 4 different note chars
                if (meetRequirements && !meetNoteCharsCondition(note)) {
                    meetRequirements = false;
                }
                // condition: minimum of 3 semitones between adjacent notes
                if (meetRequirements && (Math.abs(lastNote.getPitch() - note.getPitch())) <
                        MIN_PITCH_DISTANCE) {
                    meetRequirements = false;
                }
            } else {
                // current note is starting note
                meetRequirements = true;
            }
        }
        // 2. reset/finish the processor if the requirements are broken
        if (!meetRequirements) {

            // abort: current note doesn't fit into current arpeggio, so try without first note
            if (arpeggioNotes.size() > 0 && arpeggioNotes.size() < MIN_NOTES_COUNT &&
                    !note.isRest()) {
                arpeggioNotes.remove(0);
                // reprocess the current note
                processNote(note);
                return;
            }
            // current note doesn't meet requirements, so we are finished
            finish();
            // feed the processor with the last note if it meets the start node requirements
            if (meetStartNoteReq) {
                arpeggioNotes.add(note);
            }
        } else {
            // current note meets requirements, so add it as arpeggio note member
            arpeggioNotes.add(note);
        }
    }

    @Override
    public final void reset() {
        arpeggioNotes.clear();
    }

    @Override
    public void finish() {
        // condition: minimum of 3 notes
        if (arpeggioNotes.size() >= MIN_NOTES_COUNT) {
            // condition: maximum of 4 different note chars and minimum of 2 different note chars
            if (meetNoteCharsCondition(null)) {
                // signal arpeggio event to all registered listeners
                signal(new ArpeggioEvent(arpeggioNotes));
            }
        }
        // reset the processor
        reset();
    }

    /**
     * Check if the given absolute pitch value is already present in arpeggio processor.
     *
     * @param pitch the pitch to be checked
     * @return true if the pitch is already present, false else
     */
    private boolean isPitchPresent(final int pitch) {
        for (final Note n : arpeggioNotes) {
            if (n.getPitch() == pitch)
                return true;
        }
        return false;
    }

    /**
     * Check if the current arpeggio candidate meets the note chars conditions. If the note
     * parameter is not null it will be considered as if the note was added to the arpeggio.
     * (see also {@link ArpeggioProcessor::MIN_NOTE_CHARS},
     * {@link ArpeggioProcessor::MAX_NOTE_CHARS})
     *
     * @param note the note to be checked or null if only current arpeggio notes should be checked
     * @return note chars count
     */
    private boolean meetNoteCharsCondition(@Nullable final Note note) {
        final byte[] noteRegister = new byte[scale.length];

        if (note != null) {
            // just to be sure
            assert !HarmonyHelper.isNoteInScale(note, scale);
            // register note inside scale
            noteRegister[note.getPitch() % 12] = 1;
        }
        int result = note != null ? 1 : 0;

        // register already present arpeggio notes
        for (final Note n : arpeggioNotes) {
            noteRegister[n.getPitch() % 12] = 1;
        }
        // count all unique scale notes
        for (byte scaleNotePresent : noteRegister) {
            result += scaleNotePresent;
        }

        // condition: maximum of 4 different note chars
        if (note != null) {
            // don't check the lower bounds condition yet
            return result <= MAX_NOTE_CHARS;
        }
        // condition: maximum of 4 different note chars and minimum of 2 different note chars
        return result >= MIN_NOTE_CHARS && result <= MAX_NOTE_CHARS;
    }
}
