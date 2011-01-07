package de.freebits.omt.core.rhythm;

import jm.music.data.Note;

/**
 * Helper class for rhythm distance calculations.
 * 
 * @author Marcel Karras
 */
public class RhythmDistanceHelper {

	/**
	 * Get the duration change of a comparison note compared to a reference
	 * note.
	 * 
	 * @param refNote
	 *            reference note
	 * @param compNote
	 *            compared note
	 * @return the relative change in +/- percent
	 */
	public static final double getDurationChange(final Note refNote,
			final Note compNote) {
		double sign = refNote.getDuration() > compNote.getDuration() ? -1 : 1;
		double relDur = refNote.getDuration() > compNote.getDuration() ? compNote
				.getDuration() / refNote.getDuration()
				: refNote.getDuration() / compNote.getDuration();
		return relDur >= 1 ? relDur - 1 : sign * (1 - relDur);
	}

	/**
	 * Checks if the given two notes are in the allowed bounds considering their
	 * relative duration changes.
	 * 
	 * @param refNote
	 *            refering note
	 * @param compNote
	 *            note to be compared
	 * @param relativeDurationChange
	 *            allowed relative duration change (between 0 and 1)
	 * @return
	 */
	public static final boolean isInsideDurationChangeBounds(
			final Note refNote, final Note compNote,
			final double relativeDurationChange) {
		double lowerBounds = refNote.getDuration() - refNote.getDuration()
				* relativeDurationChange;
		double upperBounds = refNote.getDuration() + refNote.getDuration()
				* relativeDurationChange;
		return compNote.getDuration() >= lowerBounds
				&& compNote.getDuration() <= upperBounds;
	}

}
