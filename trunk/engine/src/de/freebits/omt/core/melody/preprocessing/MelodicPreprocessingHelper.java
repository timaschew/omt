package de.freebits.omt.core.melody.preprocessing;

import de.freebits.omt.core.processing.*;
import de.freebits.omt.core.processing.events.*;
import de.freebits.omt.core.processing.listener.ProcessingListener;
import de.freebits.omt.core.structures.MusicEventNote;
import de.freebits.omt.core.tools.jMusicHelper;
import jm.music.data.Note;

import java.util.List;

/**
 * Helper class for melodic preprocessing methods.
 *
 * @author Marcel Karras
 */
public class MelodicPreprocessingHelper {

    public static void preprocess(final List<MusicEventNote> eventList, final byte[] scale) {
        // create trill processor
        final TrillProcessor trillProc = new TrillProcessor(scale);
        // create tremolo processor
        final TremoloProcessor tremProc = new TremoloProcessor();
        // create arpeggio processor
        final ArpeggioProcessor arpProc = new ArpeggioProcessor(scale);
        // create acciaccatura processor
        final AcciaccaturaProcessor accProc = new AcciaccaturaProcessor();
        // create glissando processor
        final GlissandoProcessor glissProc = new GlissandoProcessor();
        // create chord processor
        final ChordProcessor chordProc = new ChordProcessor();
        // create a processing listener
        final ProcessingListener procListener = new ProcessingListener() {

            @Override
            public void signal(ProcessingEvent processingEvent) {
                if (processingEvent instanceof TrillEvent) {
                    System.out.print("==> Trill found: ");
                    for (Note n : ((TrillEvent) processingEvent).getTrillNotes()) {
                        ((MusicEventNote) n).setPreprocessingEvent(processingEvent);
                        System.out.print(jMusicHelper.getNameOfMidiPitch(n.getPitch()) + " ");
                    }
                    System.out.println();
                } else if (processingEvent instanceof TremoloEvent) {
                    System.out.print("==> Tremolo found: ");
                    for (Note n : ((TremoloEvent) processingEvent).getTremoloNotes()) {
                        ((MusicEventNote) n).setPreprocessingEvent(processingEvent);
                        System.out.print(jMusicHelper.getNameOfMidiPitch(n.getPitch()) + " ");
                    }
                    System.out.println();
                } else if (processingEvent instanceof ArpeggioEvent) {
                    System.out.print("==> Arpeggio found: ");
                    for (Note n : ((ArpeggioEvent) processingEvent).getArpeggioNotes()) {
                        ((MusicEventNote) n).setPreprocessingEvent(processingEvent);
                        System.out.print(jMusicHelper.getNameOfMidiPitch(n.getPitch()) + " ");
                    }
                    System.out.println();
                } else if (processingEvent instanceof AcciaccaturaEvent) {
                    System.out.print("==> Acciaccatura found: ");
                    for (Note n : ((AcciaccaturaEvent) processingEvent).getAcciaccaturaNotes()) {
                        ((MusicEventNote) n).setPreprocessingEvent(processingEvent);
                        System.out.print(jMusicHelper.getNameOfMidiPitch(n.getPitch()) + " ");
                    }
                    System.out.println();
                } else if (processingEvent instanceof GlissandoEvent) {
                    System.out.print("==> Glissando found: ");
                    for (Note n : ((GlissandoEvent) processingEvent).getGlissandoNotes()) {
                        ((MusicEventNote) n).setPreprocessingEvent(processingEvent);
                        System.out.print(jMusicHelper.getNameOfMidiPitch(n.getPitch()) + " ");
                    }
                    System.out.println();
                } else if (processingEvent instanceof ChordEvent) {
                    System.out.print("==> Chord found: ");
                    for (Note n : ((ChordEvent) processingEvent).getChordNotes()) {
                        ((MusicEventNote) n).setPreprocessingEvent(processingEvent);
                        System.out.print(jMusicHelper.getNameOfMidiPitch(n.getPitch()) + " ");
                    }
                    System.out.println();
                }
            }
        };
        // register listener
        trillProc.listen(TrillEvent.class, procListener);
        tremProc.listen(TremoloEvent.class, procListener);
        arpProc.listen(ArpeggioEvent.class, procListener);
        accProc.listen(AcciaccaturaEvent.class, procListener);
        glissProc.listen(GlissandoEvent.class, procListener);
        chordProc.listen(ChordEvent.class, procListener);
        // process notes
        for (MusicEventNote n : eventList) {
            // process single note or chord all chord notes
            if (n.isRest())
                System.out.println("[ -- Processing rest -- ]: " + n.getDuration());
            else
                System.out.println("Processing note: " + n.getPitch() + "(" +
                        jMusicHelper.getNameOfMidiPitch(n.getPitch()) + ")"
                        + " - " + n.getSampleStartTime());
            // process note inside every pr
            trillProc.processNote(n);
            tremProc.processNote(n);
            arpProc.processNote(n);
            accProc.processNote(n);
            glissProc.processNote(n);
            chordProc.processNote(n);
        }
        // finish processor
        trillProc.finish();
        tremProc.finish();
        arpProc.finish();
        accProc.finish();
        glissProc.finish();
        chordProc.finish();
    }
}
