package de.freebits.omt.core.structures;

import java.util.ArrayList;
import java.util.List;

/**
 * A single note music event represents a note event that is unique in time.
 * 
 * @author Marcel Karras
 */
public class SingleNote extends BasicMusicEvent {

	private final MusicEventNote note;

	public SingleNote(final MusicEventNote note, final double startTime,
			final double tempo) {
		super(null, null, startTime, tempo);
		this.note = note;
		this.note.setSampleStartTime(startTime);
		this.note.setMusicEvent(this);
	}

	public final MusicEventNote getNote() {
		return note;
	}

	@Override
	public List<MusicEventNote> getNoteList() {
		final List<MusicEventNote> noteList = new ArrayList<MusicEventNote>();
		noteList.add(note);
		return noteList;
	}
	
	@Override
	public String toString() {
		String ret = "Single Note: \n";
		for (final MusicEventNote men : getNoteList()) {
			ret += men.toString() + "\n";
		}
		return ret;
	}
}
