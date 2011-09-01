package marcelkarras.diploma.helper;

import java.util.List;

import mdb.jcosmic.application.framework.gui.DialogManager;
import mdb.jcosmic.musicdata.JCosmicTone;
import mdb.jcosmic.musicdata.JCosmicTrack;

public class MidiHelper {

	public static final double midiNoteToFrequency(int n) {
		double result = -1;
		if (n >= 0 && n <= 119)
			result = 440.0 * Math.pow(2.0, (n - 57.0) / 12.0);
		return result;
	}

	public static final double[] getFrequenciesOfTrack(final JCosmicTrack track) {
		List<JCosmicTone> tones = track.getTones();
		double[] freq = new double[track.getSize()];
		// extract frequencies from original track
		for (int i = 0; i < track.getSize(); i++) {
			JCosmicTone tone = tones.get(i);
			double f = MidiHelper.midiNoteToFrequency(tone.getTone());
			if (f != -1) {
				freq[i] = f;
			} else {
				DialogManager.errorMessage("Calculation error",
						"Invalid frequency for midi tone: " + tone.getTone());
				return null;
			}
		}
		return freq;
	}
}
