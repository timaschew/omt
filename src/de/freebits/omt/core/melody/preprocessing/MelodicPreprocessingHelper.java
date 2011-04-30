package de.freebits.omt.core.melody.preprocessing;

import java.util.List;

import jm.music.data.Note;
import de.freebits.omt.core.processing.AcciaccaturaProcessor;
import de.freebits.omt.core.processing.ArpeggioProcessor;
import de.freebits.omt.core.processing.TremoloProcessor;
import de.freebits.omt.core.processing.TrillProcessor;
import de.freebits.omt.core.processing.events.AcciaccaturaEvent;
import de.freebits.omt.core.processing.events.ArpeggioEvent;
import de.freebits.omt.core.processing.events.ProcessingEvent;
import de.freebits.omt.core.processing.events.TremoloEvent;
import de.freebits.omt.core.processing.events.TrillEvent;
import de.freebits.omt.core.processing.listener.ProcessingListener;
import de.freebits.omt.core.structures.Chord;
import de.freebits.omt.core.structures.MusicEvent;
import de.freebits.omt.core.structures.SingleNote;

/**
 * Helper class for melodic preprocessing methods.
 * 
 * @author Marcel Karras
 */
public class MelodicPreprocessingHelper {

	public static void preprocess(final List<MusicEvent> eventList, final byte[] scale) {
		// create trill processor
		final TrillProcessor trillProc = new TrillProcessor(scale);
		// create tremolo processor
		final TremoloProcessor tremProc = new TremoloProcessor();
		// create arpeggio processor
		final ArpeggioProcessor arpProc = new ArpeggioProcessor();
		// create acciaccatura processor
		final AcciaccaturaProcessor accProc = new AcciaccaturaProcessor();
		// create a processing listener
		final ProcessingListener procListener = new ProcessingListener() {

			@Override
			public void signal(ProcessingEvent processingEvent) {
				if (processingEvent instanceof TrillEvent) {
					System.out.print("==> Trill found: ");
					for (Note n : ((TrillEvent) processingEvent).getTrillNotes()) {
						System.out.print(n.getNote() + " ");
					}
					System.out.println();
				} else if (processingEvent instanceof TremoloEvent) {
					System.out.print("==> Tremolo found: ");
					for (Note n : ((TremoloEvent) processingEvent).getTremoloNotes()) {
						System.out.print(n.getNote() + " ");
					}
					System.out.println();
				} else if (processingEvent instanceof ArpeggioEvent) {
					System.out.print("==> Arpeggio found: ");
					for (Note n : ((ArpeggioEvent) processingEvent).getArpeggioNotes()) {
						System.out.print(n.getNote() + " ");
					}
					System.out.println();
				} else if (processingEvent instanceof AcciaccaturaEvent) {
					System.out.print("==> Acciaccatura found: ");
					for (Note n : ((AcciaccaturaEvent) processingEvent).getAcciaccaturaNotes()) {
						System.out.print(n.getNote() + " ");
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
		// process notes
		for (MusicEvent me : eventList) {
			Note n = null;
			if (me instanceof SingleNote) {
				n = ((SingleNote) me).getNote();
			} else if (me instanceof Chord) {
				System.out.println("Chord found!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				n = ((Chord) me).getNoteList().get(0);
			}
			if (n.isRest())
				System.out.println("[ -- Processing rest -- ]: " + n.getDuration());
			else
				System.out.println("Processing note: " + n.getPitch() + "(" + n.getNote() + ")"
						+ " - " + n.getRhythmValue());
			//trillProc.processNote(n);
			//tremProc.processNote(n);
			//arpProc.processNote(n);
			accProc.processNote(n);
		}
		// finish processor
		trillProc.finish();
		tremProc.finish();
		arpProc.finish();
		accProc.finish();
	}
}
