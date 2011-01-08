package de.freebits.omt.core.tools.events;

import java.util.EventListener;

/**
 * Interface for listeners listening for events with progress information.
 * 
 * @author Marcel Karras
 */
public interface ProgressEventListener extends EventListener {

	/**
	 * Method to be invoked when progress changes occur.
	 * 
	 * @param event
	 *            progress event
	 */
	public void signalProgressChange(final ProgressEvent event);

}
