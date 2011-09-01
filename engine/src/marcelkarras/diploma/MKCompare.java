/**
 * 
 */
package marcelkarras.diploma;

import java.io.File;

import marcelkarras.diploma.controller.CompareMidi;
import mdb.jcosmic.application.framework.gui.DialogManager;
import mdb.jcosmic.application.framework.sound.MidiPlayer;

/**
 * Application for comparing two midi files.
 * 
 * @author Marcel Karras (toka@freebits.de)
 */
public class MKCompare {

	/**
	 * Application entry point.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		MidiPlayer mp = new MidiPlayer();

		// Info message
		DialogManager.infoMessage("Information",
				"Two file chooser dialogues will pop up now in order"
						+ " to read the MIDI files you want to compare...");
		// Choose the files
		File midi_orig = DialogManager.chooseMIDIFile();
		File midi_var = DialogManager.chooseMIDIFile();
		if (midi_orig == null || midi_var == null)
			return;
		// play the files?
		if (DialogManager.yesOrNoQuestion("Do you want to play the file: \n"
				+ midi_orig.getName())) {
			mp.playFile(midi_orig);
		}
		if (DialogManager.yesOrNoQuestion("Do you want to play the file: \n"
				+ midi_var.getName())) {
			mp.playFile(midi_var);
		}
		// Compare the files
		DialogManager.infoMessage("Start comparison",
				"Click OK to start the comparison...");
		CompareMidi.compare(midi_orig, midi_var);
	}
}
