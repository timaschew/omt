package de.freebits.omt.core.harmony;

/**
 * An interface storing scale constants.
 * 
 * @author Marcel Karras
 */
public interface Scales {

    public static final byte[] 
            CHROMATIC_SCALE = 		{1,1,1,1,1,1,1,1,1,1,1,1}, 
            MAJOR_SCALE = 			{1,0,1,0,1,1,0,1,0,1,0,1}, 
            MINOR_SCALE = 			{1,0,1,1,0,1,0,1,1,0,1,0}, 
            HARMONIC_MINOR_SCALE = 	{1,0,1,1,0,1,0,1,1,0,0,1}, 
            MELODIC_MINOR_SCALE = 	{1,0,1,1,0,1,0,1,1,1,1,1}, 
            NATURAL_MINOR_SCALE = 	{1,0,1,1,0,1,0,1,1,0,1,0}, 
            DIATONIC_MINOR_SCALE = 	{1,0,1,1,0,1,0,1,1,0,1,0},
            AEOLIAN_SCALE = 		{1,0,1,1,0,1,0,1,1,0,1,0},
            DORIAN_SCALE = 			{1,0,1,1,0,1,0,1,0,1,1,0},
            LYDIAN_SCALE = 			{1,0,1,0,1,0,1,1,0,1,0,1},
            MIXOLYDIAN_SCALE = 		{1,0,1,0,1,1,0,1,0,1,1,0},
            PENTATONIC_SCALE =		{1,0,1,0,1,0,0,1,0,1,0,0},
            BLUES_SCALE =			{1,0,1,1,1,1,0,1,0,1,1,1},
            TURKISH_SCALE =			{1,1,0,1,0,1,0,1,0,0,1,1};
}
