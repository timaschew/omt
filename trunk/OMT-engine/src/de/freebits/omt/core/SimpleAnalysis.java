package de.freebits.omt.core;

import java.awt.FileDialog;
import java.awt.Frame;
import java.util.Enumeration;

import jm.JMC;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.Read;
import jm.util.View;

/**
 * This class does statistical analysis on MIDI files It prompts the user to
 * select a file this proceedure repeats until the user selects 'cancel'
 * 
 * @author Andrew Brown
 */

public final class SimpleAnalysis extends Frame implements JMC {
	static int highNote = 0;
	static int lowNote = 127;
	static double longestRhythm = 0.0;
	static double shortestRhythm = 1000.0;
	static int shortLength = 1000000;
	static int longLength = 0;
	static int ascending = 0;
	static int descending = 0;
	static int prev = 60;
	static Score s = new Score();
	static int scoreCount = 0;

	private static final String PRE_TEXT = "========================================================\n";

	public static void main(String[] args) {
		new SimpleAnalysis();
	}

	public SimpleAnalysis() {
		FileDialog fd;
		Frame f = new Frame();

		for (;;) {
			// open a MIDI file
			fd = new FileDialog(f, "Open MIDI file or choose cancel to"
					+ " finish.", FileDialog.LOAD);
			fd.show();

			// break out when user presses cancel
			if (fd.getFile() == null)
				break;

			Read.midi(s, fd.getDirectory() + fd.getFile());
			s.setTitle(fd.getFile());

			// keep track of the number of scores analysed
			scoreCount++;
			// reset melody length
			int tempLength = 0;
			// iterate through each note
			// find the highest and lowest notes
			Enumeration e = s.getPartList().elements();
			while (e.hasMoreElements()) {
				Part nextPt = (Part) e.nextElement();
				Enumeration enum2 = nextPt.getPhraseList().elements();
				while (enum2.hasMoreElements()) {
					Phrase nextPhr = (Phrase) enum2.nextElement();
					Enumeration enum3 = nextPhr.getNoteList().elements();
					while (enum3.hasMoreElements()) {
						Note nextNote = (Note) enum3.nextElement();
						int pitch = nextNote.getPitch();
						// check range
						pitchRange(pitch);
						double rv = nextNote.getRhythmValue();
						// check rhythmic values
						rhythmRange(rv);
						// check meldoy length
						tempLength++;
						// check direction
						upOrDown(pitch);
					}
				}
			}
			// update length extremes
			musicLength(tempLength);

			// output the current stats
			System.out.print(PRE_TEXT);
			System.out.println("STATS after " + scoreCount
					+ " files. Last score called " + s.getTitle());
			System.out.print(PRE_TEXT);
			System.out.println("Lowest note is " + lowNote);
			System.out.println("Highest note is " + highNote);
			System.out.println("---------------------------------");
			System.out.println("Shortest note is " + shortestRhythm);
			System.out.println("Longest note is " + longestRhythm);
			System.out.println("---------------------------------");
			System.out.println("Smallest score contains " + shortLength
					+ " notes");
			System.out.println("Largest score contains " + longLength
					+ " notes");
			System.out.println("---------------------------------");
			System.out.println("Upward movements were " + ascending);
			System.out.println("Downward movements were " + descending);
			System.out.println("---------------------------------");
			
			View.show(s);
			View.histogram(s);
			View.print(s);
		}
	}

	public void pitchRange(int pitch) {
		// check the range of the MIDI file
		if (pitch < lowNote)
			lowNote = pitch;
		if (pitch > highNote)
			highNote = pitch;
	}

	public void rhythmRange(double rv) {
		// check the range of the MIDI file
		if (rv < shortestRhythm)
			shortestRhythm = rv;
		if (rv > longestRhythm)
			longestRhythm = rv;
	}

	public void musicLength(int temp) {
		if (temp < shortLength)
			shortLength = temp;
		if (temp > longLength)
			longLength = temp;
	}

	public void upOrDown(int pitch) {
		if (pitch < prev)
			descending++;
		if (pitch > prev)
			ascending++;
		prev = pitch;
	}
}
