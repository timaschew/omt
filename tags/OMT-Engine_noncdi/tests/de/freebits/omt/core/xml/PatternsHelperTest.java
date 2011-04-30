package de.freebits.omt.core.xml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;

import jm.constants.Pitches;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.freebits.omt.core.properties.PropertiesHelper;
import de.freebits.omt.core.xml.PatternsHelper;
import de.freebits.omt.core.xml.entities.patterns.NodeType;
import de.freebits.omt.core.xml.entities.patterns.NoteType;
import de.freebits.omt.core.xml.entities.patterns.PatternsType;

/**
 * Test Cases for the {@link PatternsHelper} class.
 * 
 * @author Marcel Karras
 */
public class PatternsHelperTest {

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
	}

	/**
	 * Test method for
	 * {@link de.freebits.omt.core.xml.PatternsHelper#getNodeById(PatternsType, java.math.BigInteger)}
	 * .
	 */
	@Test
	public void testGetNodeById() {
		// one motif on "Alle meine Entchen"
		final NodeType node = PatternsHelper.getNodeById(patterns,
				new BigInteger("331"));
		Assert.assertNotNull(node);
		final int[] notes = { Pitches.C0, Pitches.D0, Pitches.E0, Pitches.F0,
				Pitches.G0, Pitches.G0 };
		int currentNote = Pitches.C0;
		for (byte i = 0; i < node.getNote().size(); i++) {
			NoteType note = node.getNote().get(i);
			currentNote += note.getDeltatone().intValue();
			Assert.assertEquals(notes[i], currentNote);
			// System.out.println(jMusicHelper.getNameOfMidiPitch(currentNote));
		}
	}
}
