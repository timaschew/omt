package de.freebits.omt.core.leadsheets;

import de.freebits.omt.core.evaluation.OMTEvaluator;
import jm.constants.Pitches;
import jm.constants.RhythmValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract leadseet database which only contains necessary properties.
 */
public class AbstractDatabase {

    private static final List<AbstractSong> songList = new ArrayList<AbstractSong>();

    static {
        /**
         * Demo song: "Alle Vögel Sind Schon Da"
         */
        final AbstractSong alleVoegel = new AbstractSong(OMTEvaluator
                .LS_ID_ALLE_VOEGEL_SIND_SCHON_DA);
        alleVoegel.setBeatLength(RhythmValues.QUARTER_NOTE);
        alleVoegel.setBeatsPerMetre(4);
        alleVoegel.setTempo(120);
        alleVoegel.setMetricLvl(2);
        alleVoegel.setMetricBase(2);

        //---- Melodielinie im ersten Motiv ----
        AbstractMelody alleVoegelMelody = alleVoegel.getMelody();
        // A-
        alleVoegelMelody.addMelodyNote(new AbstractNote(Pitches.C4, 0.0, 0.748));
        // lle
        alleVoegelMelody.addMelodyNote(new AbstractNote(Pitches.E4, 0.750, 0.248));
        // Vö-
        alleVoegelMelody.addMelodyNote(new AbstractNote(Pitches.G4, 1.000, 0.498));
        // gel
        alleVoegelMelody.addMelodyNote(new AbstractNote(Pitches.C5, 1.500, 0.498));
        // Sind
        alleVoegelMelody.addMelodyNote(new AbstractNote(Pitches.A4, 2.000, 0.498));
        // Scho-
        alleVoegelMelody.addMelodyNote(new AbstractNote(Pitches.C5, 2.500, 0.248));
        // on
        //alleVoegelMelody.addMelodyNote(new AbstractNote(Pitches.A4, 2.750, 0.248));
        // da
        //alleVoegelMelody.addMelodyNote(new AbstractNote(Pitches.G4, 3.000, 0.998));

        // ---- Akkorde im ersten Motiv ----
        List<AbstractChord> alleVoegelChords = alleVoegel.getChords();
        byte[] cChordHarmony = {1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0};
        // chord: C (root is C3) -> C3-E3-G3
        alleVoegelChords.add(new AbstractChord(Pitches.C3, 0, 0.0, 1.998, cChordHarmony));
        // chord F (root is C3, Shift is 5 semitones)
        alleVoegelChords.add(new AbstractChord(Pitches.C3, 5, 2.0, 0.998, cChordHarmony));
        // chord: C (root is C3) -> C3-E3-G3
        alleVoegelChords.add(new AbstractChord(Pitches.C3, 0, 3.0, 0.998, cChordHarmony));

        // add demo song to song database
        songList.add(alleVoegel);
    }

    /**
     * Get a song by a leadsheet song identifier.
     *
     * @param leadsheetId song id in leadsheet
     * @return song structure
     * @see OMTEvaluator::LS_ID_ALLE_VOEGEL_SIND_SCHON_DA
     */
    public static AbstractSong getSongById(final int leadsheetId) {
        for (final AbstractSong as : songList) {
            if (as.getLeadsheetId() == leadsheetId) {
                return as;
            }
        }
        return null;
    }
}
