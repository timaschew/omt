package tests.de.freebits.omt.core.harmony;

import jm.music.data.Note;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.freebits.omt.core.harmony.HarmonyHelper;
import de.freebits.omt.core.harmony.Scales;
import de.freebits.omt.core.harmony.exceptions.ScaleNotSupportedException;

/**
 * Test Cases for the {@link HarmonyHelper} class.
 * 
 * @author Marcel Karras
 */
public class HarmonyHelperTest {

	/**
	 * Initialize this test class.
	 */
	@BeforeClass
	public static void initialize() {
	}

	/**
	 * Test method for {@link HarmonyHelper#getScaleByHarmony(byte[], int)}
	 */
	@Test
	public void testGetScaleByHarmony() {
		// Test for C major
		final byte[] harmony = { 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0 };
		try {
			final int deltatone = 4;
			final byte[] scale = HarmonyHelper.getScaleByHarmony(harmony,
					deltatone);
			final byte[] testScale = HarmonyHelper.shiftScale(
					Scales.MAJOR_SCALE, deltatone);
			for (byte i = 0; i < scale.length; i++) {
				Assert.assertEquals(scale[i], testScale[i]);
			}
		} catch (ScaleNotSupportedException e) {
			Assert.fail(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link HarmonyHelper#isNoteInScale(jm.music.data.Note, byte[])}
	 */
	@Test
	public void testIsNoteInScale() {
		Assert.assertTrue(HarmonyHelper.isNoteInScale(new Note(Note.C),
				Scales.MAJOR_SCALE));
		Assert.assertFalse(HarmonyHelper.isNoteInScale(new Note(Note.C_SHARP),
				Scales.MAJOR_SCALE));
		Assert.assertTrue(HarmonyHelper.isNoteInScale(new Note(Note.C_SHARP),
				HarmonyHelper.shiftScale(Scales.MAJOR_SCALE, 1)));
		Assert.assertTrue(HarmonyHelper.isNoteInScale(new Note(Note.D_SHARP),
				Scales.MINOR_SCALE));
	}

	/**
	 * Test method for {@link HarmonyHelper#isNoteNeighbor(Note, Note, byte[])}
	 */
	@Test
	public void testIsNoteNeighbour() {
		Assert.assertTrue(HarmonyHelper.isNoteNeighbor(new Note(Note.C),
				new Note(Note.D), Scales.MAJOR_SCALE));
		Assert.assertTrue(HarmonyHelper.isNoteNeighbor(new Note(Note.E),
				new Note(Note.F), Scales.MAJOR_SCALE));
		Assert.assertTrue(HarmonyHelper.isNoteNeighbor(new Note(Note.F),
				new Note(Note.E), Scales.MAJOR_SCALE));
		Assert.assertTrue(HarmonyHelper.isNoteNeighbor(new Note(Note.D),
				new Note(Note.C), Scales.MAJOR_SCALE));
		Assert.assertFalse(HarmonyHelper.isNoteNeighbor(new Note(Note.C),
				new Note(Note.C_SHARP), Scales.MAJOR_SCALE));
		Assert.assertTrue(HarmonyHelper.isNoteNeighbor(new Note(Note.B),
				new Note(Note.C), Scales.MAJOR_SCALE));
		Assert.assertTrue(HarmonyHelper.isNoteNeighbor(new Note(Note.C),
				new Note(Note.B), Scales.MAJOR_SCALE));
		Assert.assertFalse(HarmonyHelper.isNoteNeighbor(new Note(Note.B),
				new Note(Note.C_SHARP), Scales.MAJOR_SCALE));
		Assert.assertTrue(HarmonyHelper.isNoteNeighbor(new Note(Note.D),
				new Note(Note.D_SHARP), Scales.MINOR_SCALE));
		Assert.assertFalse(HarmonyHelper.isNoteNeighbor(new Note(Note.C),
				new Note(Note.E), Scales.MAJOR_SCALE));
		Assert.assertFalse(HarmonyHelper.isNoteNeighbor(new Note(Note.C),
				new Note(Note.D), Scales.CHROMATIC_SCALE));
		Assert.assertTrue(HarmonyHelper.isNoteNeighbor(new Note(Note.C),
				new Note(Note.C_SHARP), Scales.CHROMATIC_SCALE));
		Assert.assertTrue(HarmonyHelper.isNoteNeighbor(new Note(Note.C),
				new Note(Note.B), Scales.CHROMATIC_SCALE));
	}

}
