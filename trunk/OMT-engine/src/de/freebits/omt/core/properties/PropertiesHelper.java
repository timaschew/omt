package de.freebits.omt.core.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {

	/**
	 * Create a new properties table with the values of the given properties
	 * file.
	 * 
	 * @param propertiesFileURI
	 *            file URI
	 * @return properties table
	 * @throws FileNotFoundException
	 * @throws IOException
	 * 
	 * @author Marcel Karras
	 */
	public static final Properties readPropertiesByFile(
			final String propertiesFileURI) throws FileNotFoundException,
			IOException {
		// create empty properties
		Properties properties = new Properties();
		// load properties file
		properties.load(new FileInputStream(propertiesFileURI));

		return properties;

	}

}
