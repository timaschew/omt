package de.freebits.omt.core.melody;

import java.util.List;

import jm.music.data.Score;
import jm.util.Read;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.freebits.omt.core.melody.ContourHelper;

/**
 * Test Cases for the {@link ContourHelper} class.
 * 
 * @author Marcel Karras
 */
public class ContourHelperTest {

	// midi score
	private static Score score = null;

	/**
	 * Initialize this test class.
	 */
	@BeforeClass
	public static void initialize() {
		// read midi score
		score = new Score();
		Read.midi(score, "src/tests/ressources/ContourHelperTest.mid");
	}

	/**
	 * Test method for
	 * {@link de.freebits.omt.core.melody.ContourHelper#getContour(jm.music.data.Note[])}.
	 */
	@Test
	public void testGetContour() {
		List<String> contour = ContourHelper.getContour(score.getPart(0)
				.getPhrase(0).getNoteArray());
		if (contour.size() > 0) {
			// simple test
			Assert.assertEquals("U", contour.get(0));
			Assert.assertEquals("R", contour.get(6));
			Assert.assertEquals("D", contour.get(contour.size() - 1));
		}
	}
}
