package de.freebits.omt.core.processing.streams;

import java.util.ArrayList;

/**
 * A clustering that contains clusters.
 * 
 * @author Marcel Karras
 */
public class Clustering extends ArrayList<Cluster> {

	// serialization version ID
	private static final long serialVersionUID = -983162348224423803L;

	@Override
	public String toString() {
		String ret = "";
		int i = 1;
		for (final Cluster c : this) {
			ret += "Cluster " + (i++) + " [" + c.getName() + "]: \n"
					+ c.toString() + "\n";
		}
		return ret;
	}
}