package de.freebits.omt.core.processing.listener;

import de.freebits.omt.core.processing.events.ProcessingEvent;

/**
 * Interface for processing listener classes.
 * 
 * @author Marcel Karras
 */
public interface ProcessingListener {

	/**
	 * Signal the given event to the listener.
	 * 
	 * @param processingEvent
	 *            the event to be signaled
	 */
	public void signal(final ProcessingEvent processingEvent);

}
