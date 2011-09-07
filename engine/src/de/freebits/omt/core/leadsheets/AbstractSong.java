package de.freebits.omt.core.leadsheets;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract leadsheet song structure.
 */
public class AbstractSong {

    // leadsheet ID of the song
    private int leadsheetId;
    // tempo of the song
    private double tempo;
    // beats per metre
    private int beatsPerMetre;
    // beat length
    private double beatLength;
    // song melody line
    private AbstractMelody melody = new AbstractMelody();
    // song chord/harmonics
    private List<AbstractChord> chords = new ArrayList<AbstractChord>();

    public AbstractSong(final int leadsheetId) {
        this.leadsheetId = leadsheetId;
    }

    public int getBeatsPerMetre() {
        return beatsPerMetre;
    }

    public void setBeatsPerMetre(int beatsPerMetre) {
        this.beatsPerMetre = beatsPerMetre;
    }

    public double getBeatLength() {
        return beatLength;
    }

    public void setBeatLength(double beatLength) {
        this.beatLength = beatLength;
    }

    public int getLeadsheetId() {
        return leadsheetId;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public final AbstractMelody getMelody() {
        return melody;
    }

    public void setMelody(AbstractMelody melody) {
        this.melody = melody;
    }

    public final List<AbstractChord> getChords() {
        return chords;
    }

    public void addChord(AbstractChord chord) {
        chords.add(chord);
    }
}
