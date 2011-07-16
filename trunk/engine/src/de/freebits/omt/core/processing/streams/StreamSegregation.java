package de.freebits.omt.core.processing.streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.freebits.omt.core.AcousticConstants;
import de.freebits.omt.core.structures.MusicEvent;
import de.freebits.omt.core.structures.MusicEventNote;
import de.freebits.omt.core.tools.events.ProgressEvent;
import de.freebits.omt.core.tools.events.ProgressEventListener;

/**
 * The stream segregation engine tries to detect streams on the basis of
 * psychoacoustic phenomena.
 *
 * @author Marcel Karras
 */
public class StreamSegregation {

	/**
	 * Event: cluster has sucessfully been initialized.
	 */
	public static final StreamSegregationProgressEvent EVENT_CLUSTER_INITIALIZED = new StreamSegregationProgressEvent(
			0, "cluster initialized");

	/**
	 * Event: cluster has sucessfully been generated.
	 */
	public static final StreamSegregationProgressEvent EVENT_CLUSTER_COMPLETE = new StreamSegregationProgressEvent(
			100, "stream segregation completed");

	// constant defining the weight of time in the euclidian distance of events
	private static final double TIME_WEIGHT = 1000.0;

	// constant defining the weight of pitch in the euclidian distance of events
	/**
	 * TODO: needs to be quite dynamic (parallel chord movements can make
	 * problems when pitch weight is too low)
	 */
	private static final double PITCH_WEIGHT = 1.0;

	/**
	 * The stream chord bias weight factor defines how much the chord bias of a
	 * cluster/stream will affect the cluster merging.
	 */
	private static final double STREAM_CHORD_BIAS_WEIGHT = 10.0;

	/**
	 * Calculate the distance in pitch between two music events.
	 *
	 * @param e1
	 *            first event
	 * @param e2
	 *            second event
	 * @return distance in mel (perceived pitch)
	 */
	private static final double calcEventPitchDistance(final MusicEventNote e1,
			final MusicEventNote e2) {
		return e1.getPitchMel() - e2.getPitchMel();
	}

	/**
	 * Calculate the distance in time between two music events.
	 *
	 * @param e1
	 *            first event
	 * @param e2
	 *            second event
	 * @return distance in seconds (negative distances means overlapping)
	 */
	public static final double calcEventTimeDistance(final MusicEventNote e1,
			final MusicEventNote e2) {
		// e1 <= e2
		if (e1.getEndTime() <= e2.getSampleStartTime()) {
			return e2.getSampleStartTime() - e1.getEndTime();
		}
		// e2 < e1
		else {
			return e1.getSampleStartTime() - e2.getEndTime();
		}
	}

	// neighbors to be checked when doing pitch co-modulation checks
	private int coModNeighborCount = 10;
	// list of start time sorted music events
	private List<MusicEvent> events;
	// clustering
	private Clustering clustering = new Clustering();
	// inter event distance hash table (to avoid duplicate calculations)
	private Map<String, Double> interEventDistanceHash = new HashMap<String, Double>();

	// inter cluster distance hash table (to avoid duplicate calculations)
	private ClusterDistanceTable interClusterDistanceTable = new ClusterDistanceTable();

	// listener list for stream segregation event listeners
	public static final List<ProgressEventListener> progressListeners = new ArrayList<ProgressEventListener>();

	/**
	 * Initialize the stream segregation class.
	 *
	 * @param eventList
	 */
	public StreamSegregation(final List<MusicEvent> eventList) {
		this.events = eventList;
	}

	/**
	 * Add a listener to be signalled on progress changes.
	 *
	 * @param pel
	 *            progress listener
	 */
	public final void addProgressListener(final ProgressEventListener pel) {
		progressListeners.add(pel);
	}

	/**
	 * Calculate the distance between two clusters. The distance between time
	 * intersecting (simultanous) clusters is infinite. The calculation is
	 * hashed until {@link #generateClustering()} is called.
	 *
	 * @param c1
	 *            first cluster
	 * @param c2
	 *            second cluster
	 * @return distance or {@link Double#POSITIVE_INFINITY}
	 */
	private final double calcInterClusterDistance(final Cluster c1,
			final Cluster c2) {
		// calculate hash key and reversed hash key
		final String key = c1.getName() + "," + c2.getName();
		final String key_rev = c2.getName() + "," + c1.getName();
		// lookup hashtable first
		Double hashValue = interClusterDistanceTable.get(key_rev);
		if (hashValue != null) {
			return hashValue;
		}
		// calculate distance
		double dist = Double.POSITIVE_INFINITY;
		// 1. simultaneous notes (chords)
		if (c1.isEqualIOITo(c2)) {
			// simultaneous note must have pitch co-modulation
			System.out.println("Cluster " + c1.getName() + " || to cluster "
					+ c2.getName());
			// get the co-modulation rating for these two clusters
			double coModRating = c1.calcComodulationWith(c2,
					getCoModNeighborCount());
			// check cluster movements
			if (coModRating >= 0) {
				System.out.println(c1.getName() + " comods " + c2.getName());
				// assert that the current chord only contains notes that are
				// playable by one hand
				final int c1MaxPitch = c1.getMaxPitch(), c2MaxPitch = c2
						.getMaxPitch(), c1MinPitch = c1.getMinPitch(), c2MinPitch = c2
						.getMinPitch();
				final int upperPitchBound = c1MaxPitch > c2MaxPitch ? c1MaxPitch
						: c2MaxPitch;
				final int lowerPitchBound = c1MinPitch < c2MinPitch ? c1MinPitch
						: c2MinPitch;

				if (Math.abs(upperPitchBound - lowerPitchBound) <= AcousticConstants.MAX_CHORD_INTERVAL) {
					if (c1.size() + c2.size() <= AcousticConstants.MAX_CHORD_NOTES)
						dist = 0;
				}
			}
		}
		// 2. non-simultaneous notes
		else if (!c1.isSimultaneousTo(c2)) {
			// get minimum inter-event distance for every pair of events between
			// the two clusters
			for (int i = 0; i < c1.size(); i++) {
				for (int j = 0; j < c2.size(); j++) {
					// get inter-event distance
					double ied = calcInterEventDistance(c1.get(i), c2.get(j));
					if (ied < dist) {
						// the chord bias of both clusters shouldn't differ too
						// much
						double chordsBias1 = c1.calcChordBias();
						double chordsBias2 = c2.calcChordBias();
						// chord bias distance
						double chordBiasDist = calcChordBiasDistance(
								chordsBias1, chordsBias2);
						assert (ied > 0);
						dist = Math.sqrt(Math.pow(ied, 2)
								+ Math.pow(chordBiasDist, 2));
					}
				}
			}
		}
		// create hashtable entries
		interClusterDistanceTable.put(key, dist);
		interClusterDistanceTable.put(key_rev, dist);
		return dist;
	}

	/**
	 * Calculate the distance between two music events. The distance between
	 * time intersecting (parallel) events is infinite. (euclidian distance) The
	 * calculation is hashed until {@link #generateClustering()} is called.
	 *
	 * @param e1
	 *            first event
	 * @param e2
	 *            second event
	 * @return distance or {@link Double#POSITIVE_INFINITY}
	 */
	private final double calcInterEventDistance(final MusicEventNote e1,
			final MusicEventNote e2) {
		// calculate hash key and reversed hash key
		final String key = e1.hashCode() + "," + e2.hashCode();
		final String key_rev = e2.hashCode() + "," + e1.hashCode();
		// lookup hashtable first
		Double hashValue = interEventDistanceHash.get(key_rev);
		if (hashValue != null) {
			return hashValue;
		}
		// calculate distance
		double dist = Double.POSITIVE_INFINITY;
		/*
		 * if (e1.isEqualIOITo(e1)) { // simultaneous note must have pitch
		 * co-modulation System.out.println("MEN " + e1 + " || to MEN " + e2);
		 * // check cluster movements if (c1.isComodulatingTo(c2,
		 * getCoModNeighborCount())) { System.out.println(c1.getName() +
		 * " comods " + c1.getName()); } } else
		 */if (!e1.isSimultaneousTo(e2)) {
			double etd = calcEventTimeDistance(e1, e2);
			if (etd < 0) {
				// minimal distance to not break calculation (precision 8)
				etd = 0.00000001;
			}
			// maximum pause time between two notes must not exceed limit
			//else if (etd <= AcousticConstants.MAX_PAUSE) {
				double epd = calcEventPitchDistance(e1, e2);
				dist = Math.sqrt(Math.pow(TIME_WEIGHT * etd, 2)
						+ Math.pow(PITCH_WEIGHT * epd, 2));
			//}
		}
		// create hashtable entries
		interEventDistanceHash.put(key, dist);
		interEventDistanceHash.put(key_rev, dist);
		return dist;
	}

	/**
	 * Calculate the chord bias distance of two given chord bias values.
	 *
	 * @param cBias1
	 *            first bias
	 * @param cBias2
	 *            second bias
	 * @return chord bias distance
	 * @see {@link #STREAM_CHORD_BIAS_WEIGHT}
	 */
	private final double calcChordBiasDistance(final double cBias1,
			final double cBias2) {
		return Math.abs(cBias1 - cBias2) * STREAM_CHORD_BIAS_WEIGHT;
	}

	/**
	 * Generate a clustering of streams.
	 *
	 * @return stream clustering
	 */
	public final Clustering generateClustering() {
		// initialize clustering with N clusters
		initializeClustering();
		double initClusterSize = clustering.size();
		int t = 0;
		while (true) {
			t++;
			double cDist = Double.POSITIVE_INFINITY;
			int min_i = 0, min_j = 0;
			// find minimum inter-cluster distance
			for (int i = 0; i < clustering.size(); i++) {
				for (int j = 0; j < clustering.size(); j++) {
					if (i == j)
						continue;
					double icd = calcInterClusterDistance(clustering.get(i),
							clustering.get(j));
					if (icd < cDist) {
						cDist = icd;
						min_i = i;
						min_j = j;
					}
				}
			}
			// no more minimum distances found
			if (min_i == 0 && min_j == 0) {
				break;
			}
			// merge clusters
			final Cluster c1 = clustering.get(min_i);
			final Cluster c2 = clustering.get(min_j);
			c1.addAll(c2);
			// new cluster name
			clustering.remove(c2);
			// signal progress event
			signalProgressToListeners(new StreamSegregationProgressEvent(
					(int) ((t / initClusterSize) * 100), "clusters merged ["
							+ c1.getName() + " into " + c2.getName() + "]"));
			c1.setName(c1.getName() + c2.getName());
		}
		// signal progress event
		signalProgressToListeners(new StreamSegregationProgressEvent(100,
				"stream segregation completed - result cluster:\n" + clustering));
		return clustering;
	}

	/**
	 * Get the pitch co-modulation neighbor count.
	 *
	 * @return neighbor count (each assigned to the left and right)
	 */
	public int getCoModNeighborCount() {
		return coModNeighborCount;
	}

	/**
	 * Initialize the clustering so that it contains N clusters each containing
	 * one music event.
	 */
	private final void initializeClustering() {
		// cleanup hash tables
		interClusterDistanceTable.clear();
		interEventDistanceHash.clear();
		int i = 1;
		// create as many clusters as there are music event notes
		for (final MusicEvent me : events) {
			for (final MusicEventNote note : me.getNoteList()) {
				if (!note.isRest()) {
					Cluster c = new Cluster(note);
					c.setName("e" + (i++));
					clustering.add(c);
				}
			}
		}
		// signal progress event
		signalProgressToListeners(EVENT_CLUSTER_INITIALIZED);
	}

	/**
	 * Set the pitch co-modulation neighbor count.
	 *
	 * @param coModNeighborCount
	 *            neighbor count (each assigned to the left and right)
	 */
	public void setCoModNeighborCount(int coModNeighborCount) {
		this.coModNeighborCount = coModNeighborCount;
	}

	/**
	 * Signal progress events to registered listeners.
	 *
	 * @param event
	 *            progress event
	 */
	private final void signalProgressToListeners(final ProgressEvent event) {
		for (final ProgressEventListener pel : progressListeners) {
			pel.signalProgressChange(event);
		}
	}

	/**
	 * Event class for stream segregation progress changes.
	 */
	private static class StreamSegregationProgressEvent implements
			ProgressEvent {

		private int progress;
		private String description;

		public StreamSegregationProgressEvent(final int progress,
				final String description) {
			this.progress = progress;
			this.description = description;
		}

		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public int getProgress() {
			return progress;
		}

	}
}