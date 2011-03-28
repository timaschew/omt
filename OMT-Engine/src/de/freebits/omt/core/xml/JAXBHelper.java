package de.freebits.omt.core.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class JAXBHelper {

	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(File xmlFile, Class<T> clss)
			throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clss.getPackage()
				.getName());
		Unmarshaller u = jaxbContext.createUnmarshaller();
		JAXBElement<T> doc = (JAXBElement<T>) u.unmarshal(xmlFile);
		return doc.getValue();
	}
}
