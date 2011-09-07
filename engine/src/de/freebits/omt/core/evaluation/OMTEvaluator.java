package de.freebits.omt.core.evaluation;

import de.freebits.omt.core.harmony.HarmonyHelper;
import de.freebits.omt.core.harmony.Scales;
import de.freebits.omt.core.harmony.exceptions.ScaleNotSupportedException;
import de.freebits.omt.core.leadsheets.AbstractDatabase;
import de.freebits.omt.core.leadsheets.AbstractSong;
import de.freebits.omt.core.melody.MelodicDistanceHelper;
import de.freebits.omt.core.melody.preprocessing.MelodicPreprocessingHelper;
import de.freebits.omt.core.processing.streams.Cluster;
import de.freebits.omt.core.processing.streams.Clustering;
import de.freebits.omt.core.processing.streams.StreamSegregation;
import de.freebits.omt.core.structures.MusicEvent;
import de.freebits.omt.core.tools.jMusicHelper;
import jm.music.data.Score;
import jm.util.Read;

import java.io.File;
import java.util.List;

/**
 * Backend interface for gui frontend interactions.
 */
public class OMTEvaluator {


    /**
     * Only one leadsheet song for demo purposes only.
     */
    public static final int LS_ID_ALLE_VOEGEL_SIND_SCHON_DA = 115;

    /**
     * Calc the overall similarity for the given midi file to the given leadsheet song id.
     *
     * @param leadsheetId identifier for the song in the leadsheet model
     * @param midiFile    midi file resource
     * @return similarity between 0 and 1
     */
    public static double calcSimilarity(final int leadsheetId, final File midiFile) {

        double similarity = 0.0;
        final Score score = new Score();
        List<MusicEvent> eventList = null;

        // import midi into jMusic structure
        Read.midi(score, midiFile.getAbsolutePath());
        // generate a sequential music event list from jmusic part based event structure
        eventList = jMusicHelper.generateMusicEventList(score);
        // get the abstract leadhsheet song structure for given id
        final AbstractSong abstractSong = AbstractDatabase.getSongById(leadsheetId);

        // 1. stream segregation of midi file
        final StreamSegregation strSegr = new StreamSegregation(eventList);
        final Clustering clustering = strSegr.generateClustering();

        // 2. preprocessing of streams
        for (final Cluster stream : clustering) {
            try {
                MelodicPreprocessingHelper.preprocess(stream, HarmonyHelper.getScaleByHarmony(
                        Scales.MAJOR_SCALE, 0));
            } catch (ScaleNotSupportedException e) {
                e.printStackTrace();
            }
        }

        // 3. melodic similarity
        // TODO!!!
        MelodicDistanceHelper.calcDistance()

        // 4. harmonic similarity

        // 5. rhythmic similarity

        // 6. overall similarity

        return similarity;
    }
}
