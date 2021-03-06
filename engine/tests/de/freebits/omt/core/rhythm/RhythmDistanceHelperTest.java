package de.freebits.omt.core.rhythm;

import jm.constants.Durations;
import jm.music.data.Note;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RhythmDistanceHelperTest {
    /**
     * Initialize this test class.
     */
    @BeforeClass
    public static void initialize() {
        // nothing to do here
    }

    @Test
    public void testRhythmDistance() throws Exception {
        final double[] rhythm1 = {Durations.WHOLE_NOTE, Durations.HALF_NOTE,
                Durations.QUARTER_NOTE, Durations.SIXTEENTH_NOTE, Durations.SIXTEENTH_NOTE,
                Durations.EIGHTH_NOTE};
         final double[] rhythm2 = {Durations.WHOLE_NOTE, Durations.HALF_NOTE,
                Durations.QUARTER_NOTE, Durations.SIXTEENTH_NOTE, Durations.SIXTEENTH_NOTE,
            Durations.EIGHTH_NOTE};
        //final double[] rhythm2 = {Durations.HALF_NOTE, Durations.HALF_NOTE,
        //        Durations.SIXTEENTH_NOTE};
        final double rhythmSimilarity = RhythmDistanceHelper.calcDistance(rhythm1, rhythm2, 2, 2);
        System.out.println("Rhythm similarity = " + rhythmSimilarity);
    }

    /**
     * Test method for
     * {@link de.freebits.omt.core.rhythm.RhythmDistanceHelper#getDurationChange(Note, Note)}
     * .
     */
    @Test
    public void testGetDurationChange() {
        final Note n1 = new Note(Note.C);
        final Note n2 = new Note(Note.D);
        n1.setDuration(Durations.SIXTEENTH_NOTE);
        n2.setDuration(Durations.SIXTEENTH_NOTE_TRIPLET);
        double durChange = RhythmDistanceHelper.getDurationChange(n1, n2);
        Assert.assertTrue(durChange < -0.3 && durChange > -0.34);
        n1.setDuration(Durations.QUARTER_NOTE);
        n2.setDuration(Durations.QUARTER_NOTE_TRIPLET);
        durChange = RhythmDistanceHelper.getDurationChange(n1, n2);
        Assert.assertTrue(durChange < -0.3 && durChange > -0.34);
        durChange = RhythmDistanceHelper.getDurationChange(n2, n1);
        Assert.assertTrue(durChange > 0.3 && durChange < 0.34);

    }

}
