package de.freebits.omt.core.structures;

import java.util.List;

/**
 * A basic music event is a default implementation of the {@link MusicEvent}
 * interface.
 * 
 * @author Marcel Karras
 */
public abstract class BasicMusicEvent implements MusicEvent {

	// previous note event
	private MusicEvent prev;
	// next note event
	private MusicEvent next;
	// music event start time
	private final double startTime;
	// music event performance tempo
	private final double tempo;

	/**
	 * Initialize the music event.
	 * 
	 * @param prev
	 *            previous music event
	 * @param next
	 *            next music event
	 * @param startTime
	 *            music event start time
	 */
	public BasicMusicEvent(final MusicEvent prev, final MusicEvent next,
			final double startTime, final double tempo) {
		this.prev = prev;
		this.next = next;
		this.startTime = startTime;
		this.tempo = tempo;
	}

	@Override
	public MusicEvent getNext() {
		return next;
	}

	@Override
	public MusicEvent getPrev() {
		return prev;
	}

	@Override
	public double getStartTime() {
		return startTime;
	}

	@Override
	public double getTempo() {
		return this.tempo;
	}

	@Override
	public void setNext(final MusicEvent next) {
		this.next = next;
	}

	@Override
	public void setPrev(final MusicEvent prev) {
		this.prev = prev;
	}

	@Override
	public double getEndTime() {
		// maximum of one event in music event structure
		// TODO: maybe need of mean value over all endtimes
		final List<MusicEventNote> menList = getNoteList();
		double maxEndTime = menList.get(0).getEndTime();
		for (final MusicEventNote men : menList) {
			if (men.getEndTime() > maxEndTime) {
				maxEndTime = men.getEndTime();
			}
		}
		return maxEndTime;
	}
}
