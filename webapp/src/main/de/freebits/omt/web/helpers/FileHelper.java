package de.freebits.omt.web.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Helper class for common file operations.
 */
public class FileHelper {

	/**
	 * Write a file input stream to a file output stream.
	 *
	 * @param inputStream  file input stream
	 * @param outputStream file output stream
	 * @throws IOException thrown if there's an error
	 */
	public static void writeInputToOutputStream(final InputStream inputStream, final OutputStream outputStream)
			throws IOException {
		byte[] buffer = new byte[6124];

		int bulk;
		while (true) {
			bulk = inputStream.read(buffer);
			if (bulk < 0) {
				break;
			}
			outputStream.write(buffer, 0, bulk);
			outputStream.flush();
		}

		outputStream.close();
		inputStream.close();
	}
}
