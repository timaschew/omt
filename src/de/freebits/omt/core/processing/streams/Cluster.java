package de.freebits.omt.core.processing.streams;

import java.util.ArrayList;

import de.freebits.omt.core.AcousticConstants;
import de.freebits.omt.core.structures.Chord;
import de.freebits.omt.core.structures.MusicEventNote;

/**
 * A cluster representing a stream.
 * 
 * @author Marcel Karras
 */
public class Cluster extends ArrayList<MusicEventNote> {

	// serialization version ID
	private static final long serialVersionUID = 5699180267242524798L;

	private String name = "";

	/**
	 * Initialize the cluster with one music event.
	 * 
	 * @param me
	 *            music event
	 */
	public Cluster(final MusicEventNote me) {
		this.add(me);
	}

	/**
	 * Get the start time of the cluster.
	 * 
	 * @return cluster start time
	 */
	public final double getStartTime() {
		// invariant: minimum of one event in cluster
		double startTime = get(0).getSampleStartTime();
		for (int i = 1; i < size(); i++) {
			if (get(i).getSampleStartTime() < startTime) {
				startTime = get(i).getSampleStartTime();
			}
		}
		return startTime;
	}

	/**
	 * Get the end time of the cluster.
	 * 
	 * @return cluster end time
	 */
	public final double getEndTime() {
		// invariant: minimum of one event in cluster
		double endTime = get(0).getEndTime();
		for (int i = 1; i < size(); i++) {
			if (get(i).getEndTime() > endTime) {
				endTime = get(i).getEndTime();
			}
		}
		return endTime;
	}

	/**
	 * Check if the given cluster is simultaneous in time to this cluster.
	 * 
	 * @param c
	 *            cluster to be checked against
	 * @return true if cluster c is simultaneous in time to current one, false
	 *         else
	 */
	public final boolean isSimultaneousTo(final Cluster e) {
		// case 1: event(this) intersects event(e) ___==---
		if (this.getStartTime() < e.getStartTime()
				&& this.getEndTime() > e.getStartTime()) {
			if (this.getEndTime() - e.getStartTime() > AcousticConstants.MAX_NONSIMULTANEOUS_NOTE_INTERSECTION)
				return true;
			else
				return false;
		}
		// case 2: event(e) intersects event(this) ---==___
		else if (this.getEndTime() > e.getEndTime()
				&& this.getStartTime() < e.getEndTime()) {
			if (e.getEndTime() - this.getStartTime() > AcousticConstants.MAX_NONSIMULTANEOUS_NOTE_INTERSECTION)
				return true;
			else
				return false;
		}
		// case 3: event(e) surrounds event(this) or vice
		return this.getEndTime() > e.getStartTime()
				&& e.getEndTime() > this.getStartTime();
	}

	/**
	 * Check if the given cluster perfectly matches the current one in time.
	 * 
	 * @param c
	 *            cluster to be checked against
	 * @return true if the cluster has equal inter-onset-interval (IOI)
	 */
	public final boolean isEqualIOITo(final Cluster c) {
		// TODO: human recordings tolerance factor

		// a cluster is IOI-matching another cluster when their music events
		// IOI-match -> this is only the case if the clusters contain notes
		// belonging to the same chord structure
		for (final MusicEventNote menThis : this) {
			for (final MusicEventNote menC : c) {
				// if there's one cluster note not belonging to the same chord
				// structure like the other cluster's note, the IOI test fails
				if (!menThis.getMusicEvent().equals(menC.getMusicEvent()))
					return false;
			}
		}
		return true;
		// return this.getStartTime() == c.getStartTime()
		// && this.getEndTime() == c.getEndTime();
	}

	/**
	 * Calculate how good the given cluster co-modulates the current one in
	 * pitch dimension. Therefore all possible pairs of musical events from
	 * these clusters are considered and the minimum co-modulation rating is
	 * calculated.
	 * 
	 * @param c
	 *            cluster to check against
	 * @param neighborCount
	 *            value from 1..inf that covers the amount of neightbor notes to
	 *            fullfill the co-modulation condition
	 * @return co-modulation rating with the given cluster
	 */
	public final double calcComodulationWith(final Cluster c,
			final int neighborCount) {
		double minComodRating = Double.POSITIVE_INFINITY;
		// iterate over all current cluster music events
		for (int i = 0; i < size(); i++) {
			// iterate over all given cluster music events
			for (int j = 0; j < c.size(); j++) {
				// if the current musical event isn't co-modulative in pitch
				// dimension with given musical event then the whole cluster
				// isn't co-modulative
				MusicEventNote currentMEN = get(i);
				MusicEventNote givenMEN = c.get(j);
				if (currentMEN.equals(givenMEN))
					continue;
				double curComodRating = currentMEN.calcComodulationWith(
						givenMEN, neighborCount);
				if (curComodRating >= 0) {
					// new minimal pitch co-modulation candidate found
					if (minComodRating > curComodRating) {
						minComodRating = curComodRating;
					}
				}
			}
		}
		if (minComodRating == Double.POSITIVE_INFINITY) {
			return -1.0;
		}
		return minComodRating;
	}

	@Override
	public String toString() {
		String ret = "";
		int i = 1;
		for (final MusicEventNote men : this) {
			ret += "   " + (i++) + ": " + men.toString() + "\n";
		}
		return ret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the maximum pitch value of the whole cluster. (cluster must have
	 * notes)
	 * 
	 * @return maximum pitch as MIDI note number
	 */
	public final int getMaxPitch() {
		assert (this.size() > 0);
		int maxPitch = Integer.MIN_VALUE;
		for (final MusicEventNote men : this) {
			if (maxPitch < men.getPitch()) {
				maxPitch = men.getPitch();
			}
		}
		return maxPitch;
	}

	/**
	 * Get the minimum pitch value of the whole cluster. (cluster must have
	 * notes)
	 * 
	 * @return minimum pitch as MIDI note number
	 */
	public final int getMinPitch() {
		assert (this.size() > 0);
		int minPitch = Integer.MAX_VALUE;
		for (final MusicEventNote men : this) {
			if (minPitch > men.getPitch()) {
				minPitch = men.getPitch();
			}
		}
		return minPitch;
	}

	/**
	 * Calculate the chord bias for the given cluster. This bias is an indicator
	 * for the chord-nature of a cluster/stream.
	 * 
	 * @return bias factor for the chord nature
	 */
	public final double calcChordBias() {
		double chords = 0.0;
		for (final MusicEventNote men : this) {
			if (men.getMusicEvent() instanceof Chord) {
				chords++;
			}
		}
		return chords / this.size();
	}
}