package marcelkarras.diploma.controller;

import java.io.File;

import javax.sound.midi.InvalidMidiDataException;

import marcelkarras.diploma.helper.MidiHelper;
import marcelkarras.diploma.model.hofmanengl.MelodicSimilarity;
import marcelkarras.diploma.model.hofmanengl.RhythmicSimilarity;
import mdb.jcosmic.application.framework.gui.DialogManager;
import mdb.jcosmic.musicdata.JCosmicTrack;
import mdb.jcosmic.musicdata.MidiReader;

public class CompareMidi {

	public static final void compare(final File midi_orig, final File midi_var) {
		JCosmicTrack track_orig = null;
		JCosmicTrack track_var = null;
		try {
			track_orig = new MidiReader().readMidiFile(midi_orig);
			track_var = new MidiReader().readMidiFile(midi_var);
		} catch (InvalidMidiDataException ex) {
			DialogManager.errorMessage("MIDI Error", "Cannot read MIDI file:\n"
					+ ex.getLocalizedMessage());
		}

		// arrays with frequencies
		double[] freq_orig = new double[track_orig.getSize()];
		double[] freq_var = new double[track_var.getSize()];

		freq_orig = MidiHelper.getFrequenciesOfTrack(track_orig);
		freq_var = MidiHelper.getFrequenciesOfTrack(track_var);

		double melodicSimilarity = MelodicSimilarity.calculate(freq_orig,
				freq_var);
		double rhythmicSimilarity = RhythmicSimilarity.calculate(freq_orig,
				freq_var);
		DialogManager.infoMessage("Similarity Results", "Melodic similarity: "
				+ melodicSimilarity + "\n" + "Rhythmic similarity: "
				+ rhythmicSimilarity + "\n");
	}
}
