package de.freebits.omt.core;

/**
 * This interface contains various psychoacoustic constants.
 * 
 * @author Marcel Karras
 */
public interface AcousticConstants {

	/**
	 * Minimum duration difference between two tones (in seconds) in order to
	 * not get grouped.
	 */
	public static final double MIN_DURATION_DIFFERENCE = 0.025;

	/**
	 * Maximum pause time between two tones (in seconds) in order to
	 * get grouped.
	 */
	public static final double MAX_PAUSE = 2.0;

	/**
	 * Maximum allowed intersecting time in seconds that classify two notes as
	 * being simultaneous. (empirical determined)
	 */
	public static final double MAX_NONSIMULTANEOUS_NOTE_INTERSECTION = 0.05;

	/**
	 * Penalty threshold for stream interval differences. Values below this
	 * threshold will be honored, values above will be penalized.
	 */
	public static final int STREAM_INTERVAL_PENALTY_THRESHOLD = 4;

	/**
	 * Weight factor for the endtime differences of chord notes.
	 * 
	 * @see {@link #MIN_DURATION_DIFFERENCE}
	 */
	public static final int CHORD_ENDTIMES_DIFF_WEIGHT = 3;

	/**
	 * Maximum duration of a note to be classified as an impulse.
	 */
	public static final double MAX_IMPULSE_NOTE_DURATION = 0.01;

	/**
	 * Maximum interval span from lowest to highest chord note.
	 */
	public static final int MAX_CHORD_INTERVAL = 14;

	/**
	 * Maximum amount of chord notes.
	 */
	public static final int MAX_CHORD_NOTES = 4;
}
