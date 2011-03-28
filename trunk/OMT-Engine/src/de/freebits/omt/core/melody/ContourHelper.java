package de.freebits.omt.core.melody;

import java.util.ArrayList;
import java.util.List;

import jm.music.data.Note;

/**
 * Contour helper methods.
 * 
 * @author Marcel Karras
 */
public class ContourHelper {

	/**
	 * Get the contour of the given note array.
	 * 
	 * @param noteArray
	 *            array of notes
	 * @return list of Parsons Code strings
	 */
	public static final List<String> getContour(final Note[] noteArray) {
		List<String> contour = new ArrayList<String>();
		// return an empty list if note array size is too small
		if (noteArray == null || noteArray.length < 1)
			return contour;
		for (byte i = 1; i < noteArray.length; i++) {
			// generate Parsons-Code character
			if (noteArray[i - 1].getPitch() < noteArray[i].getPitch()) {
				contour.add("U");
			} else if (noteArray[i - 1].getPitch() > noteArray[i].getPitch()) {
				contour.add("D");
			} else {
				contour.add("R");
			}
		}

		return contour;
	}

}
