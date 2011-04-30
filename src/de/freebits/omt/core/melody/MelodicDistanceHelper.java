package de.freebits.omt.core.melody;

public class MelodicDistanceHelper {

	public static final boolean bruteForceDistance(final int[] pitches_l,
			final int[] pitches_r) {
		if (pitches_l.length != pitches_r.length)
			return false;
		for (int i = 0; i < pitches_l.length; i++) {
			if (pitches_l[i] != pitches_r[i])
				return false;
		}
		return true;
	}

	public static final Double calcDistance() {
		// TODO: melodisches Distanzmaß
		return null;
	}

}
