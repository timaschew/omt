package de.freebits.omt.core.harmony.exceptions;

import de.freebits.omt.core.OMTException;

/**
 * Exception class for exceptions that should be thrown if there is the scale
 * that is not supported.
 * 
 * @author Marcel Karras
 */
public class ScaleNotSupportedException extends OMTException {

	private static final long serialVersionUID = 7284202619774188708L;

	/**
	 * Create a new scale not supported exception with a given message.
	 * 
	 * @param message
	 *            the message describing this exception
	 */
	public ScaleNotSupportedException(String message) {
		super(message);
	}

}
