package de.freebits.omt.core;

/**
 * Generic exception for OMT related exception handling.
 * 
 * @author Marcel Karras
 */
public class OMTException extends Exception {

	private static final long serialVersionUID = -1649829962667860748L;

	/**
	 * Creates a new OMT Exception with a given message string.
	 * 
	 * @param message
	 *            string describing the exception
	 */
	public OMTException(String message) {
		super(message);
	}
}
