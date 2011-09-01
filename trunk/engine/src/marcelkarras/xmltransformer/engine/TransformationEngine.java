package marcelkarras.xmltransformer.engine;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.filter.ContentFilter;
import org.jdom.filter.ElementFilter;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;

import com.sun.org.apache.xpath.internal.operations.And;

public class TransformationEngine {

	private Document events_document = null;
	private Document structures_document = null;
	private Element leadsheets = null;
	private Element patterns = null;
	private Element nodes = null;
	private Element curLeadsheet = null;

	/**
	 * Initialization of the transformation engine with the two given XML files.
	 * 
	 * @param events_xml
	 *            LSDB_Events.xml file handle
	 * @param structures_xml
	 *            LSDB_Structures .xml file handle
	 */
	public TransformationEngine(final File events_xml, final File structures_xml) {
		// create the XML SAX object
		SAXBuilder builderStructures = new SAXBuilder();
		// build document objects from given files
		try {
			events_document = builderStructures.build(events_xml);
			structures_document = builderStructures.build(structures_xml);
			// get top level children of root element (leadsheets, patterns,
			// nodes)
			this.leadsheets = structures_document.getRootElement().getChild(
					"leadsheets");
			this.patterns = structures_document.getRootElement().getChild(
					"pattern_structures");
			this.nodes = structures_document.getRootElement().getChild(
					"node_structures");
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Start the transformation based on the two files initialized on object
	 * creation.
	 */
	public void transform() {
		// iterate over every leadsheet ID to get the references to the nodes
		// that form each leadsheet
		for (Object element : leadsheets.getChildren("leadsheet")) {
			// current leadsheet
			final Element leadsheet = (Element) element;
			curLeadsheet = leadsheet;
			// root node ID of the leadsheet
			final Attribute lsRootNodeID = leadsheet
					.getAttribute("reference_node_id");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> "
					+ "Leadsheet Root Node: " + lsRootNodeID.getValue()
					+ " >>>>>>>>>>>>>>>>>>>>>>>");
			// get all leadsheet root nodes from the node_structures list
			// that match the current leadsheet root node ID (usually only one)
			Iterator iter = nodes.getDescendants(new AttributeFilter(nodes,
					"id", lsRootNodeID.getValue()));
			if (iter.hasNext()) {
				// current leadsheet root node
				Element lsNode = (Element) iter.next();
				System.out.println("Current LS Rootnode: "
						+ lsNode.getAttributeValue("id"));
				walkToPatternContainer(lsNode, 0, 0.0);
			} else {
				System.out.println("Pattern Container: "
						+ lsRootNodeID.getValue());
			}
		}
	}

	/**
	 * Run the depth first search on the given node element until we are sure
	 * that the next descendant can only be a pattern container.
	 * 
	 * @param curElement
	 *            node element
	 * @param level
	 *            current level
	 * @param levelTime
	 *            current level time
	 */
	protected final void walkToPatternContainer(final Element curElement,
			final int level, final double levelTime) {
		System.out.println(getLevelSpace(level)
				+ "Current node container element = "
				+ curElement.getAttributeValue("id"));
		Double currentTime = levelTime;
		// current child index for the current container
		int i = 0;
		// list of children (can either be pattern or node containers)
		List children = curElement.getChildren("node");
		boolean firstChild = true;
		// iterate over all children
		for (Object child : children) {
			i++;
			Element childElem = (Element) child;
			// increase the current element time component
			currentTime += Double.parseDouble(childElem
					.getAttributeValue("min_deltatime"));
			System.out.print(getLevelSpace(level + 1) + i + ".\tchild: "
					+ childElem.getAttributeValue("id"));
			// get the child's "id"
			final Attribute idAttr = childElem.getAttribute("id");
			// get all nodes from the node_structures list that match the
			// current child's "id" attribute (should match exactly one)
			Iterator iter = nodes.getDescendants(new AttributeFilter(nodes,
					idAttr.getName(), idAttr.getValue()));
			// if there is a further node structure level we will step deeper
			if (iter.hasNext()) {
				// NODE CONTAINER
				System.out.println("... is a node structures container "
						+ " with deltaTime = "
						+ childElem.getAttributeValue("min_deltatime")
						+ " Time = " + currentTime);
				while (iter.hasNext()) {
					Element nextElem = (Element) iter.next();
					walkToPatternContainer(nextElem, level + 1, currentTime);
				}
			} else {
				// PATTERN CONTAINER
				System.out.println(getLevelSpace(level + 1)
						+ "... is a pattern node container "
						+ " with deltaTime = "
						+ childElem.getAttributeValue("min_deltatime")
						+ " Time = " + currentTime);
				// get the pattern node of the pattern structure list
				// (should match exactly once)
				Iterator patternIter = patterns
						.getDescendants(new AttributeFilter(patterns, idAttr
								.getName(), idAttr.getValue()));
				if (patternIter.hasNext()) {
					// iterate over all pattern children
					Double patternTime = currentTime;
					Element patternContainer = (Element) patternIter.next();
					List patternChildren = patternContainer
							.getChildren("pattern");
					for (Object patternChild : patternChildren) {
						Element patternElem = (Element) patternChild;
						Integer patternElemId = Integer.parseInt(patternElem
								.getAttributeValue("id"));
						Iterator eventLSIter = events_document
								.getRootElement()
								.getDescendants(
										new AttributeFilter(events_document
												.getRootElement(), "id",
												curLeadsheet.getAttributeValue("id")));
						Element eventLS = null;
						if (eventLSIter.hasNext()) {
							eventLS = (Element) eventLSIter.next();
						}
						System.out.println(eventLS.getAttributeValue("title"));

						// increase time counter for every pattern element
						patternTime += Double.parseDouble(patternElem
								.getAttributeValue("min_deltatime"));
						System.out.println(getLevelSpace(level + 3)
								+ "pattern: "
								+ patternElem.getAttributeValue("id")
								+ " deltaTime = "
								+ patternElem
										.getAttributeValue("min_deltatime")
								+ " and Time = " + patternTime);
					}
				} else {
					System.err
							.println("Pattern container referenced but not in pattern structures list!");
				}
			}
		}
	}

	private String getLevelSpace(final int level) {
		String ret = "";
		for (int i = 0; i < level; i++) {
			ret += "    ";
		}
		return ret;
	}

}
