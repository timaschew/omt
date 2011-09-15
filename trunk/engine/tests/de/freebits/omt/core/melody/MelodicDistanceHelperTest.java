package de.freebits.omt.core.melody;

import jm.constants.Pitches;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class MelodicDistanceHelperTest {
    /**
     * Initialize this test class.
     */
    @BeforeClass
    public static void initialize() {
        // nothing to do here
    }

    @Test
    public void testMelodicDistance() throws Exception {
        // Alle meine Entchen
        int[] melody1 = {0, Pitches.C3, Pitches.D3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        int[] melody2 = {0, Pitches.C3, Pitches.D3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        double melodicSimilarity = MelodicDistanceHelper.calcDistance(melody1, melody2);
        // same melody is expected to be 1.0
        Assert.assertEquals(1.0,melodicSimilarity);
        System.out.println("Melodic similarity = " + melodicSimilarity);

        // Alle meine Entchen with Errors
        final int[] melody12 = {0, Pitches.C3, Pitches.E3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        final int[] melody22 = {0, Pitches.C3, Pitches.D3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        // D3 --> D4 is just an Octave jump, similarity should not be just 0.77
        // D3 --> E3 is 0.84 which is worse than D3 --> D4
        melodicSimilarity = MelodicDistanceHelper.calcDistance(melody12, melody22);
//        Assert.assertEquals(0.845072310306904, melodicSimilarity);
        System.out.println("Melodic similarity = " + melodicSimilarity);

        // Alle meine Entchen with Errors
        final int[] melody14 = {0, Pitches.C3, Pitches.D4, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        final int[] melody24 = {0, Pitches.C3, Pitches.D3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        // D3 --> D4 is just an Octave jump, similarity should not be just 0.77
        // D3 --> E3 is 0.84 which is worse than D3 --> D4
        melodicSimilarity = MelodicDistanceHelper.calcDistance(melody14, melody24);
//        Assert.assertEquals(0.7745983795180639, melodicSimilarity);
        System.out.println("Melodic similarity = " + melodicSimilarity);

        // Enhanced version: Alle meine Entchen with Errors
        final int[] melody13 = {0, Pitches.C3, Pitches.E3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        final int[] melody23 = {0, Pitches.C3, Pitches.D3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        melodicSimilarity = MelodicDistanceHelper.calcDistanceEnhanced(melody13, melody23);
        System.out.println("Melodic similarity (E3,D3) sollte kleiner sein als 0.760358302915913 = " + melodicSimilarity);

        // Enhanced version: Alle meine Entchen with Errors
        final int[] melody15 = {0, Pitches.C3, Pitches.D3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        final int[] melody25 = {0, Pitches.C3, Pitches.D4, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        melodicSimilarity = MelodicDistanceHelper.calcDistanceEnhanced(melody15, melody25);
        System.out.println("Melodic similarity (D3,D4) sollte größer sein als 0.7071145207586489 = " + melodicSimilarity);

        // Enhanced version: Alle meine Entchen with Errors
        final int[] melody16 = {0, Pitches.C3, Pitches.D3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        final int[] melody26 = {0, Pitches.D3, Pitches.D3, Pitches.E3, Pitches.F3, Pitches.G3,
                Pitches.G3};
        melodicSimilarity = MelodicDistanceHelper.calcDistanceEnhanced(melody16, melody26);
        System.out.println("Melodic similarity (F3,C3) sollte kleiner sein als 0.877737530241347 = " + melodicSimilarity);
    }
}
