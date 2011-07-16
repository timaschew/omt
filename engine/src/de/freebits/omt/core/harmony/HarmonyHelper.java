package de.freebits.omt.core.harmony;

import jm.music.data.Note;
import de.freebits.omt.core.harmony.exceptions.ScaleNotSupportedException;

import java.util.List;

/**
 * Harmony helper methods.
 * 
 * @author Marcel Karras
 */
public class HarmonyHelper {

	/**
	 * Constant defining the length of the harmony array.
	 */
	public static final byte HARMONY_BIT_LENGTH = 12;

	/**
	 * Calculate the scale array of a given harmony and it's root.
	 * 
	 * @param harmony
	 *            array of length 12 with harmony components (index 0 is 'c',
	 *            index 11 is 'b'/'h')
	 * @param deltatone
	 *            relative root note offset to the note 'c'
	 * @return scale notes array of length 12 (index 0 is 'c', index 11 is
	 *         'b'/'h') relative to the given deltatone
	 * 
	 * @author Marcel Karras
	 * @throws ScaleNotSupportedException
	 */
	public static final byte[] getScaleByHarmony(final byte[] harmony, final int deltatone)
			throws ScaleNotSupportedException {

		// 1. determine if major (ionic) or minor (aeolian) scale
		byte[] scale = Scales.MAJOR_SCALE;
		if (isHarmonyInScale(harmony, Scales.MAJOR_SCALE)) {
			scale = Scales.MAJOR_SCALE;
		} else if (isHarmonyInScale(harmony, Scales.MINOR_SCALE)) {
			scale = Scales.MINOR_SCALE;
		} else {
			throw (new ScaleNotSupportedException(
					"There is no suitable scale to match the given harmony"));
		}
		// 2. shift the scales root
		scale = shiftScale(scale, deltatone);

		return scale;
	}

	/**
	 * Check if the given harmony is a subset of the given scale.
	 * 
	 * @param harmony
	 *            the harmony array
	 * @param scale
	 *            the scale array (@see {@link Scales})
	 * @return true if harmony is a subset of scale, else false
	 * 
	 * @author Marcel Karras
	 */
	public static final boolean isHarmonyInScale(final byte[] harmony, final byte[] scale) {
		// only if every 1 in the harmony array matches a 1
		// in the scale array the harmony is a correct scale subset
		for (byte i = 0; i < harmony.length; i++) {
			if (harmony[i] == 1 && scale[i] != 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Shift the scale by the offset of deltaTone.
	 * 
	 * @param scale
	 *            the scale array to shift
	 * @param deltaTone
	 *            the offset value (can also be negative)
	 * @return the shifted scale
	 * 
	 * @author Marcel Karras
	 */
	public static final byte[] shiftScale(final byte[] scale, int deltatone) {

		while (deltatone < 0)
			deltatone += scale.length;

		byte[] shiftedScale = new byte[scale.length];

		for (byte i = 0; i < scale.length; i++) {
			shiftedScale[(i + deltatone) % scale.length] = scale[i];
		}
		return (shiftedScale);
	}

	/**
	 * Check if the given note is valid for the given scale.
	 * 
	 * @param note
	 *            note to be evaluated
	 * @param scale
	 *            the scale to be checked
	 * @return true if the note belongs to the scale, false else
	 */
	public static final boolean isNoteInScale(final Note note, final byte[] scale) {
		return scale[note.getPitch() % 12] == 1;
	}

    /**
	 * Check if the given note is valid for the given scales.
	 *
	 * @param note
	 *            note to be evaluated
	 * @param scales
	 *            the scales to be checked
	 * @return true if the note belongs to one of the given scales, false else
	 */
	public static final boolean isNoteInScales(final Note note, final List<byte[]> scales) {
        for(byte[] scale : scales){
            if(scale[note.getPitch() % 12] == 1)
                return true;
        }
		return false;
	}

	/**
	 * Check if the given notes are scale neighbors.
	 * 
	 * @param refNote
	 *            referring note
	 * @param neighborNote
	 *            neighbor note
	 * @param scale
	 *            scale the notes belong to
	 * @return true, if the two given notes are neighbor in the given scale
	 */
	public static final boolean isNoteNeighbor(final Note refNote, final Note neighborNote,
			final byte[] scale) {
		// check scale belongings
		if (!isNoteInScale(refNote, scale) || !isNoteInScale(neighborNote, scale))
			return false;

		int nIndex = neighborNote.getPitch() % 12;
		int refIndex = refNote.getPitch() % 12;
		boolean foundUpwardsNote = false;
		boolean foundDownwardsNote = false;
		// upwards
		for (int i = 1; i < 12 && (refIndex + i) % 12 != nIndex; i++) {
			if (scale[(refIndex + i) % 12] == 1) {
				foundUpwardsNote |= true;
				break;
			}
		}
		// downwards
		int i = 1;
		int nIndexBounds = (refIndex - i) < 0 ? 12 + (refIndex - i) : (refIndex - i);
		for (; i < 12 && nIndexBounds != nIndex; i++) {
			if (scale[nIndexBounds] == 1) {
				foundDownwardsNote |= true;
				break;
			}
			nIndexBounds = (refIndex - i) < 0 ? 12 + (refIndex - i) : (refIndex - i);
		}
		// if no notes between the two given ones are found, return true
		return !foundUpwardsNote || !foundDownwardsNote;
	}
}
