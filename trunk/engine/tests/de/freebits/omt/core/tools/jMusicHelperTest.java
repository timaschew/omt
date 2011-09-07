package de.freebits.omt.core.tools;

import jm.constants.Pitches;
import jm.music.data.Score;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test Cases for the {@link de.freebits.omt.core.melody.ContourHelper} class.
 *
 * @author Marcel Karras
 */
public class jMusicHelperTest {

    // midi score
    private static Score score = null;

    /**
     * Initialize this test class.
     */
    @BeforeClass
    public static void initialize() {
    }

    /**
     * Test method for
     * {@link de.freebits.omt.core.tools.jMusicHelper#calcGradusSuavis(double, double)}
     */
    @Test
    public void testCalcGradusSuavis() {
        // Prime
        Assert.assertEquals(1, jMusicHelper.calcGradusSuavis(Pitches.C3, Pitches.C3));
        // Octave
        Assert.assertEquals(2, jMusicHelper.calcGradusSuavis(Pitches.C3, Pitches.C4));
        // 2 Octaves
        Assert.assertEquals(2, jMusicHelper.calcGradusSuavis(Pitches.C3, Pitches.C5));
        // Quint
        Assert.assertEquals(4, jMusicHelper.calcGradusSuavis(Pitches.C3, Pitches.G5));
        // kleine Terz
        Assert.assertEquals(7, jMusicHelper.calcGradusSuavis(Pitches.C3, Pitches.DS1));
        // kleine Septime
        Assert.assertEquals(9, jMusicHelper.calcGradusSuavis(Pitches.D3, Pitches.C4));
        // große Sekunde
        Assert.assertEquals(8, jMusicHelper.calcGradusSuavis(Pitches.D3, Pitches.C3));
    }
}
