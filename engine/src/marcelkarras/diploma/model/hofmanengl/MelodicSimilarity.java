package marcelkarras.diploma.model.hofmanengl;

public class MelodicSimilarity {

    private static final double k1 = 0.000019;
    private static final double k2 = 0.0000033;

    /**
     * Calculate the similarity of two melodies.
     *
     * @param F1 first frequency array
     * @param F2 second frequency array
     * @return double the similarity value between 0...1
     */
    public static final double calculate(final double[] F1, final double[] F2) {
        // constraint: equal size chunks
        int L = (F1.length > F2.length) ? F2.length : F1.length;
        double[] T = new double[L];
        double[] I1 = new double[L];
        double[] I2 = new double[L];
        double T1 = 0, T2 = 0;
        double I3 = 0, I4 = 0;
        double sp = 0;

        /**
         * In fact, setting c1 = 1 and c2 = -2, we obtain a correlation of
         * 87% with the data as produced by the two experiments as
         * conducted by Hofmann-Engl & Parncutt (1998).
         */

        // melodic chain length
        L = L - 1;

        for (int l = 1; l <= L; l++) {
            T[l] = Math.log(F1[l] / F2[l]) * 1731;
        }
        for (int l = 1; l <= L - 1; l++) {
            I1[l] = Math.log(F1[l + 1] / F1[l]) * 1731;
            I2[l] = Math.log(F2[l + 1] / F2[l]) * 1731;
        }

        for (int l = 1; l <= L; l++) {
            // c1 = 1
            // T = Similarity Vector Vs
            T1 = T1
                    + Math.pow(Math.exp((-k1 / Math.pow(L, 1))
                    * Math.pow(T[l], 2)), 2);
        }
        T2 = Math.sqrt(T1 / L);
        if (L <= 1) {
            I4 = 1;
        } else {
            for (int l = 1; l <= L - 1; l++) {
                // c2 = -2
                // I = Interval Vector Vi
                I3 = I3
                        + Math.pow(Math.exp((-k2 / Math.pow(L - 1, -2))
                        * Math.pow((I1[l] - I2[l]), 2)), 2);
            }
            I4 = Math.sqrt(I3 / (L - 1));
        }
        sp = T2 * I4;

        return sp;
    }
}
