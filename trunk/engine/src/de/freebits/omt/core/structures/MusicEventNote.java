package de.freebits.omt.core.structures;

import de.freebits.omt.core.AcousticConstants;
import de.freebits.omt.core.processing.events.ProcessingEvent;
import de.freebits.omt.core.tools.jMusicHelper;
import jm.music.data.Note;

/**
 * A music event note is a note that is contained by a music event.
 *
 * @see {@link MusicEvent}
 *
 * @author Marcel Karras
 */
public class MusicEventNote extends Note {

	private static final long serialVersionUID = -8988415942870837377L;

	private MusicEvent me;
    private ProcessingEvent preprocessingEvent = null;

	/**
	 * Create a music event note without a music event (call
	 * {@link #setMusicEvent(MusicEvent)} before further computations)
	 *
	 * @param note
	 */
	public MusicEventNote(final Note note) {
		super(note);
	}

	/**
	 * Create a music event note and link it to the given music event.
	 *
	 * @param note
	 *            jmusic note object
	 * @param me
	 *            music event
	 */
	public MusicEventNote(final Note note, final MusicEvent me) {
		super(note);
		this.me = me;
	}

	/**
	 * Get the music event this event note belongs to.
	 *
	 * @return music event or null if no music event container found
	 */
	public final MusicEvent getMusicEvent() {
		return me;
	}

	/**
	 * Specifies the music event this music event note belongs to.
	 *
	 * @param me
	 *            music event
	 */
	public void setMusicEvent(final MusicEvent me) {
		this.me = me;
	}

	/**
	 * Get the music event note end time in seconds (based on rhythm value).
	 * TODO: evaluate rhythm value vs. duration value usage
	 *
	 * @return end time in seconds
	 */
	public final double getEndTime() {
		return getSampleStartTime() + (getRhythmValue() * 60.0 / me.getTempo());
	}

	/**
	 * Get the virtual endtime of the music event note. The virtual endtime is
	 * equal to real entime for notes that won't be perceived as an impuls. If
	 * the impuls character is given, the virtual endtime is just a view
	 * microseconds greater than the start time.
	 *
	 * @return virtual end time
	 */
	public final double getVirtualEndTime() {
		// does note has impulse character?
		if (getEndTime() - getSampleStartTime() <= AcousticConstants.MAX_IMPULSE_NOTE_DURATION) {
			return getSampleStartTime() + 0.001;
		}
		return getEndTime();
	}

	/**
	 * Check if the given event note is simultaneous in time to this note.
	 *
	 * @param e
	 *            event to be checked against
	 * @return true if event e is simultaneous in time to current one, false
	 *         else
	 */
	public final boolean isSimultaneousTo(final MusicEventNote e) {
		// case 1: event(this) intersects event(e) ___==---
		if (this.getSampleStartTime() < e.getSampleStartTime()
				&& this.getEndTime() > e.getSampleStartTime()) {
			if (this.getEndTime() - e.getSampleStartTime() > AcousticConstants.MAX_NONSIMULTANEOUS_NOTE_INTERSECTION)
				return true;
			else
				return false;
		}
		// case 2: event(e) intersects event(this) ---==___
		else if (this.getEndTime() > e.getEndTime()
				&& this.getSampleStartTime() < e.getEndTime()) {
			if (e.getEndTime() - this.getSampleStartTime() > AcousticConstants.MAX_NONSIMULTANEOUS_NOTE_INTERSECTION)
				return true;
			else
				return false;
		}
		// case 3: event(e) surrounds event(this) or vice
		return this.getEndTime() > e.getSampleStartTime()
				&& e.getEndTime() > this.getSampleStartTime();
	}

	/**
	 * Calculate the pitch co-modulation with the given musical event note over
	 * the given amount of neighbors.
	 *
	 * @param e
	 *            musical event note to check against
	 * @param neighborCount
	 *            value from 1..inf that covers the amount of neightbor notes to
	 *            fullfill the co-modulation condition
	 * @return co-modulation rating with the given musical event note
	 */
	public final double calcComodulationWith(final MusicEventNote e,
			final int neighborCount) {
		if (neighborCount < 1) {
			System.err.println("ERR: [MusicEventNote::isComodulatingTo(...)] "
					+ "- neighbor count has to be >= 1");
		}

		System.out.print("RIGHT...");
		final double coModRight = hasParallelStreamsWith(e, neighborCount, true);
		System.out.println("DONE.");
		System.out.print("LEFT...");
		final double coModLeft = hasParallelStreamsWith(e, neighborCount, false);
		System.out.println("DONE.");

		return coModRight + coModLeft;
	}

	private final double hasParallelStreamsWith(final MusicEventNote e,
			final int neighborCount, boolean moveRight) {
		System.out.print((moveRight ? "R" : "L") + "(" + neighborCount + ")");
		final MusicEvent me = e.getMusicEvent();
		if (!(me instanceof Chord)) {
			System.err
					.println("ERR: [MusicEventNote::hasParallelStreamsWith(...)] "
							+ "- musical event note has to be part of a chord structure");
		}
		// choose left/right neighbor direction
		MusicEvent nextME = moveToChord((Chord) me, moveRight);
		if (nextME != null) {
			double minNeighborComodsRating = Double.POSITIVE_INFINITY;
			// iterate over all pairs of next musical event notes
			for (MusicEventNote nextMEN1 : nextME.getNoteList()) {
				for (MusicEventNote nextMEN2 : nextME.getNoteList()) {
					double curNeighborComodsRating = Double.POSITIVE_INFINITY;
					// condition: nextMEN1 != nextMEN2
					if (nextMEN1.equals(nextMEN2)) {
						continue;
					}
					// if there's a pair (e1,n1) and (e2,n2) with same
					// movement direction then we've got a hypothesis for
					// a movement direction and must check if this is
					// recursively the case
					double pitchDiff1 = nextMEN1.getPitch() - this.getPitch();
					double pitchDiff2 = nextMEN2.getPitch() - e.getPitch();
					if (Math.signum(pitchDiff1) == Math.signum(pitchDiff2)) {
						// calculate the pitch diff rating of current neighbor
						// level
						double pitchDiffRating = calcPitchDiffRating(
								pitchDiff1, pitchDiff2);
						if (neighborCount > 1) {
							// recursive step to next neighbor leven
							double pitchDiffRatingNext = nextMEN1
									.hasParallelStreamsWith(nextMEN2,
											neighborCount - 1, moveRight);
							// calculate the overall pitch difference rating
							if (pitchDiffRatingNext >= 0) {
								curNeighborComodsRating = pitchDiffRating
										+ pitchDiffRatingNext;
							}
						}
					}
					// evaluate the calculated pitch difference rating
					if (minNeighborComodsRating > curNeighborComodsRating) {
						minNeighborComodsRating = curNeighborComodsRating;
					}
				}
			}
			if (minNeighborComodsRating == Double.POSITIVE_INFINITY) {
				// current musical event has no notes to match pitch
				// co-modulation principle for the given neighbor count
				return -1.0;
			}
			System.err.println(minNeighborComodsRating);
			return minNeighborComodsRating;
		}
		// when we reach this point, there're no further neighbors or all
		// considered neighbors match the pitch co-modulation principle
		return 0.0;
	}

	/**
	 * Calculate the rating for interval jumps within a stream.
	 *
	 * @param pitchDiff1
	 *            first pitch difference
	 * @param pitchDiff2
	 *            second pitch difference
	 * @return rating for pitch differences
	 */
	private final double calcPitchDiffRating(final double pitchDiff1,
			final double pitchDiff2) {
		return Math
				.pow((pitchDiff1 / AcousticConstants.STREAM_INTERVAL_PENALTY_THRESHOLD),
						4)
				+ Math.pow(
						(pitchDiff2 / AcousticConstants.STREAM_INTERVAL_PENALTY_THRESHOLD),
						4);
	}

	private final MusicEvent moveToChord(final Chord c, final boolean moveRight) {
		MusicEvent nextEvent = c;
		do {
			nextEvent = moveRight ? nextEvent.getNext() : nextEvent.getPrev();
		} while (nextEvent != null && !(nextEvent instanceof Chord));

		return nextEvent instanceof Chord ? nextEvent : null;
	}

	/**
	 * Get the frequency pitch of this music event in mel (mel scale).
	 *
	 * @return pitch in mel (dimension-less)
	 */
	public final double getPitchMel() {
		return 2595 * Math.log10(1 + (getFrequency() / 700.0));
	}

	@Override
	public String toString() {
		return "MusicEventNote: " + "["
				+ ((me instanceof Chord) ? "ChordNote" : "SingleNote") + "]"
				+ "[Note=" + jMusicHelper.getNameOfMidiPitch(getPitch())
				+ "] [StartTime=" + getSampleStartTime() + "] [Endtime="
				+ getEndTime() + "] [RhythmValue=" + getRhythmValue()
				+ "] [FreqMel=" + getPitchMel() + "]";
	}

    public ProcessingEvent getPreprocessingEvent() {
        return preprocessingEvent;
    }

    public void setPreprocessingEvent(final ProcessingEvent preprocessingEvent) {
        this.preprocessingEvent = preprocessingEvent;
    }
}
