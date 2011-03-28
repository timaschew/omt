package de.freebits.omt.core.streams;

import static org.junit.Assert.fail;

import java.util.List;

import jm.music.data.Score;
import jm.util.Read;

import org.junit.BeforeClass;
import org.junit.Test;

import de.freebits.omt.core.processing.streams.StreamAnalyzer;
import de.freebits.omt.core.structures.MusicEvent;
import de.freebits.omt.core.tools.jMusicHelper;

/**
 * @deprecated
 */
public class StreamAnalyerTest {

	private static List<MusicEvent> eventList = null;

	/**
	 * Initialize this test class.
	 */
	@BeforeClass
	public static void initialize() {
		// read midi score
		final Score score = new Score();
		Read.midi(score,
				"src/tests/ressources/Mozart Sonata K332 - Allegro Assai.mid");
		eventList = jMusicHelper.generateMusicEventList(score);
	}

	/**
	 * Test method for
	 * {@link StreamAnalyzer#segment(java.util.List, int, int, int)}
	 */
	@Test
	public void testSegment() {
		StreamAnalyzer.segment(eventList, 5, 5, 10);
		fail("Not yet implemented");
	}

}
