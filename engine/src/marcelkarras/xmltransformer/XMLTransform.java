/**
 * 
 */
package marcelkarras.xmltransformer;

import java.io.File;

import marcelkarras.xmltransformer.engine.TransformationEngine;
import mdb.jcosmic.application.framework.gui.DialogManager;

/**
 * XML Transformation for the LSDB_Events.xml into the LSDB_Structures.xml.
 * 
 * @author Marcel Karras (toka@freebits.de)
 */
public class XMLTransform {
	/** File name of the xml files with midi event definitions */
	private static final String EVENTS_XML = "LSDB_Events.xml";
	/** File name of the xml file with midi structure definitions */
	private static final String STRUCTURES_XML = "LSDB_Structures.xml";

	/**
	 * Application entry point.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		// read xml files
		final File events_xml = DialogManager.chooseXMLFile(EVENTS_XML);
		final File structures_xml = DialogManager.chooseXMLFile(STRUCTURES_XML);
		// create transformer object
		final TransformationEngine tEngine = new TransformationEngine(
				events_xml, structures_xml);
		tEngine.transform();

	}

}
