package de.freebits.omt.core.rhythm;

import jm.music.data.Note;

/**
 * Helper class for rhythm distance calculations.
 *
 * @author Marcel Karras
 */
public class RhythmDistanceHelper {

    private static double ATOMIC_INTERVAL = 0.005;
    private static double MAX_DIFF = 0.02;
    private static double[] RHYTHM_VALUES = {
            1.0,        // whole
            0.5,        // half
            0.375,      // dotted quarter
            0.25,       // quarter
            0.125,      // eighth
            0.08333,    // 12th
            0.0625,     // 16th
            0.05,       // 20th
            0.03125     // 32th
    };

    private static double K1 = 0.15;
    private static double K2 = 1.28;
    // constant for metric influence of penalty factor in (0,1]
    private static double METRIC_INFLUENCE = 0.2;

    public static double calcDistance(final double[] chain1, double[] chain2, int metricLvl,
                                      int metricBase) {

        // TODO: entfernen - nur zum Gleichziehen von jMusic und Modellrhythmuswerten
        for (int l = 0; l < chain1.length; l++) {
            chain1[l] = chain1[l] / 4.0;
        }
        for (int l = 0; l < chain2.length; l++) {
            chain2[l] = chain2[l] / 4.0;
        }

        //quantisizeRhythmValues(chain1);
        //quantisizeRhythmValues(chain2);

        double SumV1 = 0;
        double SumV2 = 0;
        double F1, v, L, F2 = 0, S;
        int n = 0, al2 = 0;
        double[] ch1_atomic = new double[100000];
        double[] ch2_atomic = new double[100000];
        double[] R = new double[100000];

        for (double c1 : chain1) {
            SumV1 = SumV1 + c1;
        }
        for (double c2 : chain2) {
            SumV2 = SumV2 + c2;
        }
        // calc: tempo similarity
        v = -K1 * Math.pow(Math.log(SumV1 / SumV2), 2);
        F1 = Math.exp(v);

        // equalize lengths
        if (SumV2 > SumV1) {
            L = SumV2 / SumV1;
            for (int m = 0; m < chain1.length; m++) {
                chain1[m] = L * chain1[m];
            }
        }
        if (SumV1 > SumV2) {
            L = SumV1 / SumV2;
            for (int m = 0; m < chain2.length; m++) {
                chain2[m] = L * chain2[m];
            }
        }

        // create arrays represented in (quasi) atomic note representation
        for (double c1 : chain1) {
            for (double k = 0.005; k < c1; k += 0.005) {
                n++;
                ch1_atomic[n] = c1;
            }
        }
        for (double c2 : chain2) {
            for (double k = 0.005; k < c2; k += 0.005) {
                al2++;
                ch2_atomic[al2] = c2;
            }
        }

        // logarithmic distances at all points
        double currentRm = 0;
        // penalty factor is 1
        double penaltyFactor = 1.0;
        for (int m = 1; m < n; m++) {
            // m * 0.005 = current chain time in rhythm values
            R[m] = Math.log(ch1_atomic[m] / ch2_atomic[m]) / Math.log(2);
            // check how much rhythms differ (test: 1/64th)
            if (Math.abs(R[m]) > MAX_DIFF) {
                if (currentRm != R[m]) {
                    // penalize more if this is a beat position
                    currentRm = R[m];
                    // position inside rhythm
                    long p = Math.round(m * 0.005 * 4);
                    // penalize the rhythm position according to given metric
                    int rhythmWeight = getWeightOfRhythmPosition(2, 2, p);
                    // get penalty factor (2-e^{1-x}) of [1,2)
                    penaltyFactor = 2 - Math.exp(1 - rhythmWeight);
                    // insert empirical constant
                    penaltyFactor = Math.pow(penaltyFactor - 1, 1.0 / METRIC_INFLUENCE) + 1;
                }
            } else {
                // reset penalty factor
                penaltyFactor = 1.0;
            }
            R[m] = R[m] * penaltyFactor;
        }

        // calculate chronotonic distance
        for (int m = 1; m < n; m++) {
            F2 = F2 + Math.pow(Math.exp(-K2 * Math.pow(R[m], 2)), 2);
        }
        F2 = Math.sqrt(F2 / n);
        // workaround for similarity = 1 (0.999)
        {
            double F2_rounded = Math.round(F2 * 1000);
            F2_rounded = F2_rounded / 1000;
            if (F2_rounded == 0.999) {
                F2 = 1.0;
            }
        }

        // S = F1 * ||vector F2||
        S = F1 * F2;
        // round
        System.out.println("Length of c1: " + SumV1);
        System.out.println("Length of c2: " + SumV2);
        System.out.println("Similarity in Tempo: " + F1);
        System.out.println("Similarity in Distribution: " + Double.toString(F2));
        System.out.println("Similarity: " + Double.toString(S));

        return S;
    }

    public static void quantisizeRhythmValues(final double[] rhythmValues) {
        for (int i = 0; i < rhythmValues.length; i++) {
            boolean found = false;
            for (double rhythmValue : RHYTHM_VALUES) {
                double diff = Math.abs(rhythmValues[i] - rhythmValue);
                if (diff < ATOMIC_INTERVAL) {
                    rhythmValues[i] = rhythmValue;
                    found = true;
                    break;
                } else if (diff == 0) {
                    found = true;
                }
            }
            // cannot find proper rhythm value, something messed up
            assert found : "Didn't find proper rhythm value while quantisizing!";
        }
    }

    /**
     * Get the duration change of a comparison note compared to a reference
     * note.
     *
     * @param refNote  reference note
     * @param compNote compared note
     * @return the relative change in +/- percent
     */
    public static final double getDurationChange(final Note refNote,
                                                 final Note compNote) {
        double sign = refNote.getDuration() > compNote.getDuration() ? -1 : 1;
        double relDur = refNote.getDuration() > compNote.getDuration() ? compNote
                .getDuration() / refNote.getDuration()
                : refNote.getDuration() / compNote.getDuration();
        return relDur >= 1 ? relDur - 1 : sign * (1 - relDur);
    }

    /**
     * Checks if the given two notes are in the allowed bounds considering their
     * relative duration changes.
     *
     * @param refNote                refering note
     * @param compNote               note to be compared
     * @param relativeDurationChange allowed relative duration change (between 0 and 1)
     * @return
     */
    public static final boolean isInsideDurationChangeBounds(
            final Note refNote, final Note compNote,
            final double relativeDurationChange) {
        double lowerBounds = refNote.getDuration() - refNote.getDuration()
                * relativeDurationChange;
        double upperBounds = refNote.getDuration() + refNote.getDuration()
                * relativeDurationChange;
        return compNote.getDuration() >= lowerBounds
                && compNote.getDuration() <= upperBounds;
    }

    /**
     * Get the metric weight for the given rhythm position.
     *
     * @param rhythmBase rhythmic base >= 2
     * @param metricLvl  metric level >= 0
     * @param rhythmPos  rhythm position index to get weight for
     * @return weight of rhythm
     */
    private static int getWeightOfRhythmPosition(int rhythmBase, int metricLvl, long rhythmPos) {
        int weight = 0;
        for (int i = 0; i <= metricLvl; i++) {
            weight += (rhythmPos % Math.pow(rhythmBase, i)) == 0 ? 1 : 0;
        }
        assert weight > 0 : "Rhythm weight has to be greater than 0";
        return weight;
    }

}