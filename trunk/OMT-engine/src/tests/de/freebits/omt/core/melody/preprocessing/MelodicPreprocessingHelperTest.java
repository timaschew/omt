package tests.de.freebits.omt.core.melody.preprocessing;

import jm.music.data.Score;
import jm.util.Read;

import org.junit.BeforeClass;
import org.junit.Test;

import de.freebits.omt.core.harmony.HarmonyHelper;
import de.freebits.omt.core.harmony.Scales;
import de.freebits.omt.core.harmony.exceptions.ScaleNotSupportedException;
import de.freebits.omt.core.melody.preprocessing.MelodicPreprocessingHelper;

/**
 * Test Cases for the {@link MelodicPreprocessingHelper} class.
 * 
 * @author Marcel Karras
 */
public class MelodicPreprocessingHelperTest {

	// midi score
	private static Score score = null;

	/**
	 * Initialize this test class.
	 */
	@BeforeClass
	public static void initialize() {
		// read midi score
		score = new Score();
		Read.midi(score, "src/tests/ressources/PreprocessingTest.mid");
	}

	/**
	 * Test method for
	 * {@link MelodicPreprocessingHelper#preprocess(jm.music.data.Note[], byte[])}
	 * .
	 */
	@Test
	public void testPreprocess() {
		try {
			MelodicPreprocessingHelper.preprocess(score.getPart(0).getPhrase(0).getNoteArray(),
					HarmonyHelper.getScaleByHarmony(Scales.MAJOR_SCALE, 0));
		} catch (ScaleNotSupportedException e) {
			e.printStackTrace();
		}
	}
}
