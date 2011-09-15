package de.freebits.omt.core.evaluation;

import de.freebits.omt.core.harmony.HarmonyHelper;
import de.freebits.omt.core.harmony.Scales;
import de.freebits.omt.core.harmony.exceptions.ScaleNotSupportedException;
import de.freebits.omt.core.leadsheets.*;
import de.freebits.omt.core.melody.MelodicDistanceHelper;
import de.freebits.omt.core.melody.preprocessing.MelodicPreprocessingHelper;
import de.freebits.omt.core.processing.streams.Cluster;
import de.freebits.omt.core.processing.streams.Clustering;
import de.freebits.omt.core.processing.streams.StreamSegregation;
import de.freebits.omt.core.rhythm.RhythmDistanceHelper;
import de.freebits.omt.core.structures.MusicEvent;
import de.freebits.omt.core.structures.MusicEventNote;
import de.freebits.omt.core.tools.jMusicHelper;
import jm.constants.Durations;
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
        // get abstract reference melody notes
        final int[] melody_ref = new int[abstractSong.getMelody().getSize()];
        final AbstractMelody abstractMelody = abstractSong.getMelody();
        for(int i=0; i<abstractMelody.getSize(); i++){
            melody_ref[i] = abstractMelody.getNoteAt(i).getPitch();
        }
        // get abstract reference bass notes
        final int[] bass_ref = new int[abstractSong.getChords().size()];
        final List<AbstractChord> abstractChords = abstractSong.getChords();
        for(int i=0; i<abstractChords.size(); i++){
            bass_ref[i] = abstractChords.get(i).getRootPitch();
        }

        // cluster 1 is melody cluster
        final Cluster melodyCluster = clustering.get(1);
        final int[] melody_cur = new int[melodyCluster.size()];
        for(int i=0; i < melodyCluster.size(); i++){
            melody_cur[i] = melodyCluster.get(i).getPitch();
        }

        // 3. melodic similarity
        final double melodyDist = MelodicDistanceHelper.calcDistance(melody_ref, melody_cur);

        // 4. harmonic similarity
        // skip

        // 5. rhythmic similarity
        // Beispielrhythmus: Alle Vögel Sind Schon Da
        final double[] rhythm_ref = {
                Durations.QUARTER_NOTE, Durations.EIGHTH_NOTE,
                Durations.QUARTER_NOTE, Durations.QUARTER_NOTE,
                Durations.QUARTER_NOTE, Durations.EIGHTH_NOTE, Durations.EIGHTH_NOTE,
                Durations.HALF_NOTE};
        final double[] rhythm_cur = {Durations.QUARTER_NOTE, Durations.EIGHTH_NOTE,
                /*Durations.QUARTER_NOTE,*/ Durations.QUARTER_NOTE,
                Durations.QUARTER_NOTE, /*Durations.EIGHTH_NOTE,*/ Durations.EIGHTH_NOTE,
                Durations.HALF_NOTE};
        final double rhythmSimilarity = RhythmDistanceHelper.calcDistance(rhythm_ref, rhythm_cur,
                abstractSong.getMetricLvl(), abstractSong.getMetricBase());


        // 6. overall similarity (TODO)
        final double overallSimilarity = rhythmSimilarity;

        return overallSimilarity;
    }
}
