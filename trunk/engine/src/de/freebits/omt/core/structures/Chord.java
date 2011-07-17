package de.freebits.omt.core.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A chord music event represents a list of notes that occur at the same time.
 * (do not necessarily have to be equal in length) The chord notes are sorted
 * ascending by pitch.
 *
 * @author Marcel Karras
 */
public class Chord extends BasicMusicEvent {

    private final List<MusicEventNote> noteList = new ArrayList<MusicEventNote>();

    /**
     * Create a new chord music event with a list of notes and a start time.
     *
     * @param noteList  list of notes
     * @param startTime start time
     */
    public Chord(final List<MusicEventNote> noteList, final double startTime,
                 final double tempo) {
        super(null, null, startTime, tempo);
        for (final MusicEventNote n : noteList) {
            n.setMusicEvent(this);
            n.setSampleStartTime(startTime);
        }
        this.noteList.addAll(noteList);
    }

    /**
     * Create a new chord music event with one initial note and a start time.
     *
     * @param note      initial note
     * @param startTime start time
     */
    public Chord(final MusicEventNote note, final double startTime,
                 final double tempo) {
        super(null, null, startTime, tempo);
        addNote(note);
    }

    /**
     * Add a note to the list of chord notes.
     *
     * @param note note to add
     */
    public void addNote(final MusicEventNote note) {
        // add the new note in correct sort order
        int insertIndex = 0;
        for (final MusicEventNote men : noteList) {
            if (men.getPitch() > note.getPitch()) {
                break;
            }
            insertIndex++;
        }
        noteList.add(insertIndex, note);
        note.setMusicEvent(this);
    }

    @Override
    public final List<MusicEventNote> getNoteList() {
        return noteList;
    }

    /**
     * Get all chord notes sorted by start time ascending or descending.
     *
     * @param timeSorted true for ascending, false else
     * @return sorted note list
     */
    public final List<MusicEventNote> getNoteList(boolean timeSorted) {
        // clone source list
        List<MusicEventNote> sortedList = new ArrayList<MusicEventNote>();
        sortedList.addAll(noteList);
        Collections.sort(sortedList, new Comparator<MusicEventNote>() {
            @Override
            public int compare(final MusicEventNote o1, final MusicEventNote o2) {
                double diff = o1.getSampleStartTime() - o2.getSampleStartTime();
                if (diff == 0)
                    return 0;
                else if (diff < 0)
                    return -1;
                else
                    return 1;
            }
        });
        return sortedList;
    }

    @Override
    public String toString() {
        String ret = "Chord with notes: \n";
        for (final MusicEventNote men : getNoteList()) {
            ret += men.toString() + "\n";
        }
        return ret;
    }

    /**
     * Get the minimum end time over all chord notes. (end times can vary due to
     * tolerances in recordings)
     *
     * @return minimum endtime
     */
    public final double getMinEndTime() {
        final List<MusicEventNote> menList = getNoteList();
        // invariant: minimum of one chord note exists
        double time = menList.get(0).getEndTime();
        for (int i = 1; i < menList.size(); i++) {
            if (menList.get(i).getEndTime() < time) {
                time = menList.get(i).getEndTime();
            }
        }
        return time;
    }

    /**
     * Get the maximum end time over all chord notes. (end times can vary due to
     * tolerances in recordings)
     *
     * @return maximum endtime
     */
    public final double getMaxEndTime() {
        final List<MusicEventNote> menList = getNoteList();
        // invariant: minimum of one chord note exists
        double time = menList.get(0).getEndTime();
        for (int i = 1; i < menList.size(); i++) {
            if (menList.get(i).getEndTime() > time) {
                time = menList.get(i).getEndTime();
            }
        }
        return time;
    }
}
