package de.freebits.omt.core.leadsheets;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of a leadsheet melody line.
 */
public class AbstractMelody {

    private List<AbstractNote> melodyNotes = new ArrayList<AbstractNote>();

    public void addMelodyNote(final AbstractNote note){
        melodyNotes.add(note);
    }

    public List<AbstractNote> getMelodyNotes(){
        return melodyNotes;
    }

    public int getSize(){
        return melodyNotes.size();
    }

    public AbstractNote getNoteAt(final int pos){
        return melodyNotes.get(pos);
    }
}
