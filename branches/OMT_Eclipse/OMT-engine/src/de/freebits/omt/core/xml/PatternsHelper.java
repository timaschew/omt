package de.freebits.omt.core.xml;

import java.io.File;
import java.math.BigInteger;

import javax.xml.bind.JAXBException;

import de.freebits.omt.core.xml.entities.patterns.NodeType;
import de.freebits.omt.core.xml.entities.patterns.PatternsType;

public class PatternsHelper {

	/**
	 * Get the patterns structure by given xml patterns file.
	 * 
	 * @param xmlFileURI
	 *            URI to the patterns xml file
	 * @return patterns container element
	 * 
	 * @author Marcel Karras
	 */
	public static final PatternsType getPatternsStructure(
			final String xmlFileURI) {
		PatternsType patterns = null;
		// read "Patterns.xml" file
		final File xmlFile = new File(xmlFileURI);
		// read patterns xml structure
		Class<?> clss = PatternsType.class;
		try {
			patterns = (PatternsType) JAXBHelper.unmarshal(xmlFile, clss);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return patterns;
	}

	/**
	 * Find a pattern (motif) node by id.
	 * 
	 * @param patternContainer
	 *            motif container
	 * @param id
	 *            motif identificator
	 * @return motif with the given id or null if not found
	 * 
	 * @author Marcel Karras
	 */
	public static final NodeType getNodeById(
			final PatternsType patternContainer, final BigInteger id) {
		for (NodeType node : patternContainer.getNode()) {
			if (node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}

}
