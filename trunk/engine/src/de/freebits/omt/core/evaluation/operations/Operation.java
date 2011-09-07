package de.freebits.omt.core.evaluation.operations;

import de.freebits.omt.core.structures.MusicEventNote;

/**
 * Base class for note operations.
 */
public class Operation {

    /**
     * The modified note of the source note set.
     */
    private MusicEventNote sourceNote;

    public MusicEventNote getSourceNote() {
        return sourceNote;
    }

    public void setSourceNote(MusicEventNote sourceNote) {
        this.sourceNote = sourceNote;
    }
}
