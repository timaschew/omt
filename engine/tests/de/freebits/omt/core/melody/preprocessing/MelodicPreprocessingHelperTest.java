package de.freebits.omt.core.melody.preprocessing;

import java.io.IOException;
import java.util.List;

import de.freebits.omt.core.processing.streams.Clustering;
import de.freebits.omt.core.processing.streams.StreamSegregation;
import jm.music.data.Score;
import jm.util.Read;

import org.junit.BeforeClass;
import org.junit.Test;

import de.freebits.omt.core.harmony.HarmonyHelper;
import de.freebits.omt.core.harmony.Scales;
import de.freebits.omt.core.harmony.exceptions.ScaleNotSupportedException;
import de.freebits.omt.core.melody.preprocessing.MelodicPreprocessingHelper;
import de.freebits.omt.core.structures.MusicEvent;
import de.freebits.omt.core.tools.jMusicHelper;

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
		Read.midi(score, "tests/ressources/preprocessing/Arpeggio_002.mid");
	}

	/**
	 * Test method for
	 * {@link MelodicPreprocessingHelper#preprocess(java.util.List, byte[])}
	 * .
	 */
	@Test
	public void testPreprocess() {
		final List<MusicEvent> eventList = jMusicHelper.generateMusicEventList(score);
		try {
                                            // 1. stream segregation of midi file
        final StreamSegregation strSegr = new StreamSegregation(eventList);
        final Clustering clustering = strSegr.generateClustering();
            try {
                jMusicHelper.visualizeClustering(clustering);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            MelodicPreprocessingHelper.preprocess(clustering.get(0), HarmonyHelper.getScaleByHarmony(
					Scales.MAJOR_SCALE, 0));
		} catch (ScaleNotSupportedException e) {
			e.printStackTrace();
		}
        try {
            System.in.read(new byte[20]);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
