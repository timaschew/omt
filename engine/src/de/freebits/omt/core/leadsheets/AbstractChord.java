package de.freebits.omt.core.leadsheets;

/**
 * Abstraction of leadsheet chord.
 */
public class AbstractChord {

    private int rootPitch;
    private double startTime;
    private double length;
    private int deltaPitch;
    private byte[] harmony;

    public AbstractChord(int rootPitch, int deltaPitch, double startTime, double length,
                         byte[] harmony) {
        this.rootPitch = rootPitch;
        this.startTime = startTime;
        this.length = length;
        this.deltaPitch = deltaPitch;
        this.harmony = harmony;
    }

    public int getDeltaPitch() {
        return deltaPitch;
    }

    public void setDeltaPitch(int deltaPitch) {
        this.deltaPitch = deltaPitch;
    }

    public int getRootPitch() {
        return rootPitch;
    }

    public void setRootPitch(int rootPitch) {
        this.rootPitch = rootPitch;
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

    public byte[] getHarmony() {
        return harmony;
    }

    public void setHarmony(byte[] harmony) {
        this.harmony = harmony;
    }
}
