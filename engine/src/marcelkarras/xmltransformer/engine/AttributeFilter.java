package marcelkarras.xmltransformer.engine;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.filter.ElementFilter;

/**
 * A filter to match against a special attribute and value pair.
 * 
 * @author Marcel Karras (toka@freebits.de)
 */
public class AttributeFilter extends ElementFilter {

	private String attrName;
	private Object attrValue;
	private Element root;

	public AttributeFilter(final Element root, final String name,
			final Object value) {
		// attribute name<->value pair to be matched
		this.attrName = name;
		this.attrValue = value;
		this.root = root;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matches(Object arg) {
		if (super.matches(arg)) {
			// only node elements are permitted here
			if (arg instanceof Element) {
				Element elem = (Element) arg;
				if (!elem.getParent().equals(root)) {
					return false;
				}
				Attribute attr = elem.getAttribute(this.attrName);
				if (attr.getValue().equals(this.attrValue)) {
					/*System.out.println(arg.toString() + " == "
							+ this.attrValue.toString());*/
					return true;
				}

			}
		}
		return false;
	}

}
