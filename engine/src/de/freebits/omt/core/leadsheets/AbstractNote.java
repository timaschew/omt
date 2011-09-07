package de.freebits.omt.core.leadsheets;

/**
 * Abstraction of leadsheet pattern note.
 */
public class AbstractNote {

    private int pitch;
    private double startTime;
    private double length;

    public AbstractNote(int pitch, double startTime, double length) {
        this.pitch = pitch;
        this.startTime = startTime;
        this.length = length;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
