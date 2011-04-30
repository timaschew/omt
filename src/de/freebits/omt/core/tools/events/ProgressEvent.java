package de.freebits.omt.core.tools.events;

/**
 * Event signalling progress changes.
 * 
 * @author Marcel Karras
 */
public interface ProgressEvent {

	/**
	 * Get the current progress in percent.
	 * 
	 * @return progess (0..100)
	 */
	public int getProgress();

	/**
	 * Get the description of the current progress step.
	 * 
	 * @return progress step description
	 */
	public String getDescription();
}
