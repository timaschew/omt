package de.freebits.omt.core.tools;

import java.lang.reflect.Field;

import jm.constants.Frequencies;
import jm.constants.Pitches;

public class jMusicHelper {
	/**
	 * Get the midi note name for a given pitch value.
	 * 
	 * @param pitch
	 *            a value from 0..127
	 * @return midi note name like in {@link Pitches}
	 * @see Class description of {@link Pitches}.
	 * @see Class description of {@link Frequencies}.
	 * 
	 * @author Marcel Karras
	 */
	public static final String getNameOfMidiPitch(final int pitch) {
		// use java reflection API
		final Field[] fields = Pitches.class.getFields();
		if (fields != null) {
			for (Field f : fields) {
				// try to find the pitch member variable
				try {
					if (f.getInt(null) == pitch) {
						// some exceptions
						String name = f.getName();
						if (name.contains("FF"))
							return name.replace("FF", "E");
						return f.getName();
					}
				} catch (IllegalArgumentException e) {
					return "";
				} catch (IllegalAccessException e) {
					return "";
				}
			}
		}
		return "";
	}
}
