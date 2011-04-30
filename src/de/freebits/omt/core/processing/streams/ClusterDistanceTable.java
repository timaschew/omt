package de.freebits.omt.core.processing.streams;

import java.util.HashMap;

public class ClusterDistanceTable extends HashMap<String, Double> {

	private static final long serialVersionUID = -1329214291029828381L;

	@Override
	public String toString() {
		String ret = "";
		for (final String key : keySet()) {
			ret += key + "='" + get(key) + "'\n";
		}
		ret += "\n";
		return ret;
	}
}
