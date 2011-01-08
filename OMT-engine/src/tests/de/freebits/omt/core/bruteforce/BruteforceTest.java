package tests.de.freebits.omt.core.bruteforce;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import jm.music.data.Score;
import jm.util.Read;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.freebits.omt.core.melody.MelodicDistanceHelper;
import de.freebits.omt.core.properties.PropertiesHelper;
import de.freebits.omt.core.xml.PatternsHelper;
import de.freebits.omt.core.xml.entities.patterns.PatternsType;

/**
 * Test class for a brute force comparison of midi file and xml template.
 * 
 * @author Marcel
 */
public class BruteforceTest {

	// midi score
	private static Score score = null;
	// patterns xml structure
	private static PatternsType patterns = null;

	/**
	 * Initialize this test class.
	 */
	@BeforeClass
	public static void initialize() {
		// get OMT properties
		Properties properties = null;
		try {
			properties = PropertiesHelper
					.readPropertiesByFile("omt-engine.properties");
		} catch (FileNotFoundException e) {
			System.err.println("Property file could not be found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("I/O Problems reading the property file");
			e.printStackTrace();
		}
		if (properties == null)
			return;
		// get schema directory
		final String schema_dir = properties.getProperty("schema_dir");
		// get patterns structure
		patterns = PatternsHelper.getPatternsStructure(schema_dir
				+ "/Patterns.xml");
		// read midi score
		score = new Score();
		Read.midi(score, "src/tests/ressources/ContourHelperTest.mid");
	}

	/**
	 * Brute force comparison of a score with a patterns node.
	 */
	@Test
	public void testComparison() {
		// TODO: melodic brute force
		int[] melody_1 = { 1, 2, 3 };
		int[] melody_2 = { 1, 2, 3 };
		System.out.println(MelodicDistanceHelper.bruteForceDistance(melody_1,
				melody_2));
		Assert.assertTrue(MelodicDistanceHelper.bruteForceDistance(melody_1,
				melody_2));
		// TODO: rhythmic brute force
		// TODO: harmonic brute force
	}
}
