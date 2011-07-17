package de.freebits.omt.core.processing.streams;

import de.freebits.omt.core.structures.MusicEvent;
import jm.music.data.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stream recognition helper.
 * 
 * @author Marcel Karras
 * @deprecated
 */
public final class StreamAnalyzer {

	/**
	 * Check if two notes fully coincide (parallel notes).
	 * 
	 * @param n_i
	 *            first note
	 * @param n_j
	 *            second note
	 * @return true if notes fully coincide, else false
	 */
	public static final boolean coincide(final Note n_i, final Note n_j) {
		boolean coincide = true;
		// onset has to be the same
		if (n_i.getSampleStartTime() != n_j.getSampleStartTime()) {
			coincide &= false;
		}
		// note length has to be the same
		if (n_i.getDuration() != n_i.getDuration()) {
			coincide &= false;
		}

		return coincide;
	}

	/**
	 * Verify two coinciding notes for parallel streams (positive or negative
	 * correlated movements) - Synchronous Note, Pitch Co-Modulation principles
	 * 
	 * @param n_i
	 *            first note
	 * @param n_j
	 *            second note
	 * @param cL
	 *            window length to be (maximum stream segment size to look for)
	 * @return true if there's a positive correlated movement of two parallel
	 *         streams
	 */
	public static final boolean verifyCoincide(final Note n_i, final Note n_j, final int cL) {

		return false;
	}

	public static final boolean nonOverlap(final Note n_i, final Note n_j) {
		return false;
	}

	public static final double normPD(final Note n_i, final Note n_j) {
		return 0.0;
	}

	public static final double normIOI(final Note n_i, final Note n_j) {
		return 0.0;
	}

	// public static final double kNNCluster(final List<Note> N, final
	// Map<Map<Note, Note>, Double> D, final int k){
	// foreach ni ∈ N
	// L(ni) = kNN(k, N, D);
	// foreach ni ∈ N
	// foreach nj ∈ N
	// if nj ∈ L(ni) and ni ∈ L(nj)
	// MVN(ni,nj) = pos(nj,L(ni)) +
	// pos(ni,L(nj));
	// else
	// MVN(ni,nj) = ∞;
	// for m = 2 to 2*k
	// find all ni,nj∈N with MVN(ni,nj)==m
	// assign ni,nj to the same cluster
	// }

	public static final void segment(final List<MusicEvent> eventList, final int k, final int cL,
			final int w) {
		// generate note list from event list
		final List<Note> N = new ArrayList<Note>();
		for (final MusicEvent me : eventList) {
			N.addAll(me.getNoteList());
		}
		final Map<Map<Note, Note>, Double> D = new HashMap<Map<Note, Note>, Double>();
		for (final Note n_i : N) {
			for (final Note n_j : N) {
				// check equal notes to be skipped
				if (n_i.equals(n_j))
					continue;
				if (coincide(n_i, n_j)) {
					if (verifyCoincide(n_i, n_j, cL)) {
						final Map<Note, Note> hm = new HashMap<Note, Note>();
						hm.put(n_i, n_j);
						D.put(hm, 0.0);
					}
				} else if (nonOverlap(n_i, n_j)) {
					final Map<Note, Note> hm = new HashMap<Note, Note>();
					hm.put(n_i, n_j);
					D.put(hm, 0.5 * normPD(n_i, n_j) + normIOI(n_i, n_j));
				} else {
					final Map<Note, Note> hm = new HashMap<Note, Note>();
					hm.put(n_i, n_j);
					D.put(hm, Double.MAX_VALUE);
					// kNNCluster(N, D, k);
				}

			}
		}
		return;
	}
}
