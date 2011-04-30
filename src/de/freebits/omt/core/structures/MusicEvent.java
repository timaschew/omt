package de.freebits.omt.core.structures;

import java.util.List;

/**
 * Interface for all music events.
 * 
 * @author Marcel Karras
 */
public interface MusicEvent {

	/**
	 * Get the previous music event.
	 * 
	 * @return previous music event
	 */
	public MusicEvent getPrev();

	/**
	 * Get the next music event.
	 * 
	 * @return next music event
	 */
	public MusicEvent getNext();

	/**
	 * Set the previous music event.
	 * 
	 * @param prev
	 *            previous music event
	 */
	public void setPrev(final MusicEvent prev);

	/**
	 * Set the next music event.
	 * 
	 * @param next
	 *            next music event
	 */
	public void setNext(final MusicEvent next);

	/**
	 * Get the music event start time in seconds.
	 * 
	 * @return music event start time in seconds
	 */
	public double getStartTime();

	/**
	 * Get the music event performance tempo.
	 * 
	 * @return music event tempo
	 */
	public double getTempo();

	/**
	 * Get all notes inside this event.
	 * 
	 * @return list of notes
	 */
	public List<MusicEventNote> getNoteList();

	/**
	 * Get the music event end time in seconds.
	 * 
	 * @return music event end time in seconds
	 */
	public double getEndTime();

}
