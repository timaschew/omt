package de.freebits.omt.core.melody;

import de.freebits.omt.core.tools.jMusicHelper;
import jm.util.Convert;

public class MelodicDistanceHelper {

    private static final double k1 = 0.000019;
    private static final double k2 = 0.0000033;
    private static final double c1 = 1.0;
    private static final double c2 = -2.0;
    // Helmholtz: cents = 1200 / log_e (2) * log_e( f2 / f1 )
    // 1200 Cents = 1 Oktave -> Cents entsprechen additivem Intervallempfinden des Gehörs
    private static final double eval_1200_log_e_2 = 1731.2340490667561;

    /**
     * Calculate the similarity of two melodies.
     *
     * @param m1 first melody array containing midi pitches
     * @param m2 second melody array containing midi pitches
     * @return double the similarity value between 0...1
     */
    public static double calcDistance(final int[] m1, final int[] m2) {

        double[] melody1 = new double[m1.length];
        double[] melody2 = new double[m2.length];
        for (int i = 0; i < m1.length; i++) {
            // convert midi pitches to frequencies
            melody1[i] = Convert.getFrequencyByMidiPitch(m1[i]);
        }
        for (int i = 0; i < m2.length; i++) {
            // convert midi pitches to frequencies
            melody2[i] = Convert.getFrequencyByMidiPitch(m2[i]);
        }

        int n = (melody1.length > melody2.length) ? melody2.length : melody1.length;
        double[] Vs = new double[n];
        double[] Vi1_cents = new double[n];
        double[] Vi2_cents = new double[n];
        double VsAdvancedVectorSum = 0, F1 = 0;
        double ViAdvancedVectorSum = 0, F2 = 0;
        double sp = 0;

        // melodic chain length
        n = n - 1;

        // Berechnung der einfachen Vs Vektor-Werte
        for (int l = 1; l <= n; l++) {
            Vs[l] = Math.log(melody1[l] / melody2[l]) * eval_1200_log_e_2;
        }
        // Berechnung der einfachen Vi Vektor-Werte in Form von Cents
        for (int l = 1; l <= n - 1; l++) {
            Vi1_cents[l] = Math.log(melody1[l + 1] / melody1[l]) * eval_1200_log_e_2;
            Vi2_cents[l] = Math.log(melody2[l + 1] / melody2[l]) * eval_1200_log_e_2;
        }

        // Berechnung der erweiterten Vektorsummen zur Endberechnung von F1
        for (int l = 1; l <= n; l++) {
            // c1 = 1
            VsAdvancedVectorSum = VsAdvancedVectorSum
                    + Math.pow(
                    Math.exp((-k1 / Math.pow(n, c1)) * Math.pow(Vs[l], 2)),
                    2);
        }
        // Berechnung von F1
        F1 = Math.sqrt(VsAdvancedVectorSum / n);

        if (n <= 1) {
            // wenn Länge zu klein, so Intervall nicht betrachten
            F2 = 1;
        } else {

            for (int l = 1; l <= n - 1; l++) {
                // Berechnung der erweiterten Vektorsummen zur Endberechnung von F2
                ViAdvancedVectorSum = ViAdvancedVectorSum
                        + Math.pow(Math.exp((-k2 / Math.pow(n - 1, c2))
                        * Math.pow((Vi1_cents[l] - Vi2_cents[l]), 2)), 2);
            }
            // Berechnung von F2
            F2 = Math.sqrt(ViAdvancedVectorSum / (n - 1));
        }
        System.out.println("F1=" + F1 + " F2=" + F2);
        sp = F1 * F2;

        return sp;
    }

    /**
     * Calculate the similarity of two melodies.
     *
     * @param m1 first melody array containing midi pitches
     * @param m2 second melody array containing midi pitches
     * @return double the similarity value between 0...1
     */
    public static double calcDistanceEnhanced(final int[] m1, final int[] m2) {

        double[] melody1 = new double[m1.length];
        double[] melody2 = new double[m2.length];
        for (int i = 1; i < m1.length; i++) {
            // convert midi pitches to frequencies
            melody1[i] = Convert.getFrequencyByMidiPitch(m1[i]);
        }
        for (int i = 1; i < m2.length; i++) {
            // convert midi pitches to frequencies
            melody2[i] = Convert.getFrequencyByMidiPitch(m2[i]);
        }

        int n = (melody1.length > melody2.length) ? melody2.length : melody1.length;
        double[] Vs_cents = new double[n];
        double[] Vi1_cents = new double[n];
        double[] Vi2_cents = new double[n];
        double VsAdvancedVectorSum = 0, F1 = 0;
        double ViAdvancedVectorSum = 0, F2 = 0;
        double sp = 0;

        double[] gradusVs = new double[n];

        // melodic chain length
        n = n - 1;

        // Berechnung der einfachen Vs Vektor-Werte
        for (int l = 1; l <= n; l++) {
            // Berechnung von Intervallunterschieden in cents
            gradusVs[l] = jMusicHelper.calcGradusSuavis(m1[l], m2[l]);
            gradusVs[l] = gradusVs[l] == 1 ? 1 : 1 / ((gradusVs[l] / (Math.exp(5.0 / gradusVs[l])))
                    + 1);
            System.out.println(gradusVs[l]);
            //gradusVs[l] = 1 / (Math.pow(gradusVs[l], 0.8));
            Vs_cents[l] = Math.log(melody1[l] / melody2[l]) * eval_1200_log_e_2;
        }
        // Berechnung der einfachen Vi Vektor-Werte in Form von Cents
        for (int l = 1; l <= n - 1; l++) {
            Vi1_cents[l] = Math.log(melody1[l + 1] / melody1[l]) * eval_1200_log_e_2;
            Vi2_cents[l] = Math.log(melody2[l + 1] / melody2[l]) * eval_1200_log_e_2;
        }

        // Berechnung der erweiterten Vektorsummen zur Endberechnung von F1
        for (int l = 1; l <= n; l++) {
            // c1 = 1
            VsAdvancedVectorSum = VsAdvancedVectorSum
                    + Math.pow(
                    Math.exp((-k1 / Math.pow(n, c1)) * Math.pow(Vs_cents[l], 2)),
                    2);
        }
        // Berechnung von F1
        F1 = Math.sqrt(VsAdvancedVectorSum / n);

        if (n <= 1) {
            // wenn Länge zu klein, so Intervall nicht betrachten
            F2 = 1;
        } else {

            for (int l = 1; l <= n - 1; l++) {
                // Berechnung der erweiterten Vektorsummen zur Endberechnung von F2
                ViAdvancedVectorSum = ViAdvancedVectorSum
                        + Math.pow(Math.exp((-k2 / Math.pow(n - 1, c2))
                        * Math.pow((Vi1_cents[l] - Vi2_cents[l]), 2)), 2);
            }
            // Berechnung von F2
            F2 = Math.sqrt(ViAdvancedVectorSum / (n - 1));
        }


        double F_gradus = 0;
        for (int i = 1; i < gradusVs.length; i++) {
            F_gradus += Math.pow(gradusVs[i], 2) / (n);
        }
        F_gradus = Math.sqrt(F_gradus);
        F1 = (F1 + F_gradus) / (2.0);
        sp = F1 * F2;


        System.out.println("F1 = " + F1);
        System.out.println("F2 = " + F2);
        System.out.println("F_gradus = " + F_gradus);
        return sp;
    }

    public static boolean bruteForceDistance(final int[] pitches_l, final int[] pitches_r) {
        if (pitches_l.length != pitches_r.length)
            return false;
        for (int i = 0; i < pitches_l.length; i++) {
            if (pitches_l[i] != pitches_r[i])
                return false;
        }
        return true;
    }
}
