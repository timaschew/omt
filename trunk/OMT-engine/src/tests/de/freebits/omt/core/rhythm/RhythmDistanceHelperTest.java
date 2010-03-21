package tests.de.freebits.omt.core.rhythm;

import jm.constants.Durations;
import jm.music.data.Note;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.freebits.omt.core.rhythm.RhythmDistanceHelper;

public class RhythmDistanceHelperTest {
	/**
	 * Initialize this test class.
	 */
	@BeforeClass
	public static void initialize() {
		// nothing to do here
	}

	/**
	 * Test method for
	 * {@link RhythmDistanceHelper#getDurationChange(jm.music.data.Note, jm.music.data.Note)}
	 * .
	 */
	@Test
	public void testGetDurationChange() {
		final Note n1 = new Note(Note.C);
		final Note n2 = new Note(Note.D);
		n1.setDuration(Durations.SIXTEENTH_NOTE);
		n2.setDuration(Durations.SIXTEENTH_NOTE_TRIPLET);
		double durChange = RhythmDistanceHelper.getDurationChange(n1, n2);
		Assert.assertTrue(durChange < -0.3 && durChange > -0.34);
		n1.setDuration(Durations.QUARTER_NOTE);
		n2.setDuration(Durations.QUARTER_NOTE_TRIPLET);
		durChange = RhythmDistanceHelper.getDurationChange(n1, n2);
		Assert.assertTrue(durChange < -0.3 && durChange > -0.34);
		durChange = RhythmDistanceHelper.getDurationChange(n2, n1);
		Assert.assertTrue(durChange > 0.3 && durChange < 0.34);
		
	}

}
