package de.freebits.omt.core.processing;

import de.freebits.omt.core.processing.events.ProcessingEvent;
import de.freebits.omt.core.processing.listener.ProcessingListener;

/**
 * Generic interface for processor classes.
 * 
 * @author Marcel Karras
 */
public interface Processor {

	/**
	 * Reset the processor.
	 */
	public void reset();

	/**
	 * Finish the process and calculate/signal results.
	 */
	public void finish();

	/**
	 * Register a listener to the given processing event.
	 * 
	 * @param processingEventClass
	 *            processing event class to listen to
	 * @param processingListener
	 *            processing listener to signal on event occurrence
	 */
	public void listen(final Class<?> processingEventClass,
			final ProcessingListener processingListener);

	/**
	 * Signal a processing event to all registered processing listeners.
	 * 
	 * @param processingEvent
	 *            processing event to be signaled
	 */
	public void signal(final ProcessingEvent processingEvent);

}
