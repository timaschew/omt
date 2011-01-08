package tests.de.freebits.omt.core.streams;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import jm.constants.Pitches;
import jm.constants.RhythmValues;
import jm.music.data.Note;
import jm.music.data.Score;
import jm.util.Read;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.freebits.omt.core.processing.streams.Clustering;
import de.freebits.omt.core.processing.streams.StreamSegregation;
import de.freebits.omt.core.structures.MusicEvent;
import de.freebits.omt.core.structures.MusicEventNote;
import de.freebits.omt.core.structures.SingleNote;
import de.freebits.omt.core.tools.jMusicHelper;
import de.freebits.omt.core.tools.events.ProgressEvent;
import de.freebits.omt.core.tools.events.ProgressEventListener;

/**
 * Testclass for stream segregation of a given pattern. The pattern can contain
 * single/multiple voices.
 * 
 * @author Marcel Karras
 */
public class StreamSegregationTest {

	private static List<MusicEvent> eventList = null;
	private static JProgressBar progressBar = null;
	private static JPanel panel = null;
	private static JFrame frame = null;
	private static JTextArea taskOutput = null;
	private static JScrollPane scrollPane = null;

	/**
	 * Swing Worker for the stream segregation background process.
	 * 
	 * @author Marcel Karras
	 */
	class ClusteringWorker extends SwingWorker<Clustering, Void> {

		@Override
		public Clustering doInBackground() {
			final StreamSegregation strSegr = new StreamSegregation(eventList);
			// register progress listeners
			strSegr.addProgressListener(new ProgressEventListener() {
				@Override
				public void signalProgressChange(ProgressEvent event) {
					setProgress(event.getProgress());
					taskOutput.append(String.format(
							"[%5d%%]\t" + event.getDescription() + "\n",
							event.getProgress()));

				}
			});
			// generate clustering
			final Clustering clustering = strSegr.generateClustering();
			return clustering;
		}

		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			try {
				jMusicHelper
						.visualizeMusicEventList(eventList, "Original MIDI");
				jMusicHelper.visualizeClustering(get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initialize this test class.
	 */
	@BeforeClass
	public static void initialize() {
		frame = new JFrame("Clustering Test");
		panel = new JPanel(new BorderLayout());
		frame.setContentPane(panel);
		frame.setSize(500, 500);
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);
		taskOutput.setAutoscrolls(true);
		taskOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPane = new JScrollPane(taskOutput);

		panel.add(progressBar, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		// frame.pack();

		// read midi score
		final Score score = new Score();
		// Read.midi(score,
		// "src/tests/ressources/Mozart Sonata K332 - Allegro Assai.mid");
		// Read.midi(score, "src/tests/ressources/ChopinPreludeNo4.mid");
		// Read.midi(score, "src/tests/ressources/ClusterTest_new.mid");
		Read.midi(score, "src/tests/ressources/StreamTest_ChordPlusMelody.mid");
		// Read.midi(score,
		// "src/tests/ressources/SegmentationChordsWithBreaksAndMelody.mid");
		//Read.midi(score, "src/tests/ressources/Chord_Recognition_Parallel.mid");
		//Read.midi(score, "src/tests/ressources/Beatles - Michelle.mid");
		eventList = jMusicHelper.generateMusicEventList(score);
	}

	/**
	 * Test method for {@link StreamSegregation#generateClustering()}
	 */
	@Test
	public void testClustering() {
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		final ClusteringWorker cw = new ClusteringWorker();
		cw.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if (pce.getPropertyName() == "progress")
					progressBar.setValue((Integer) pce.getNewValue());
			}
		});
		cw.execute();
		System.out.println("Cluster Test done.");
		byte[] input = new byte[2];
		try {
			System.in.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link StreamSegregation#calcEventTimeDistance(MusicEventNote, MusicEventNote)}
	 */
	@Test
	public void testCalcEventTimeDistance() {
		SingleNote me1 = new SingleNote(new MusicEventNote(new Note(Pitches.c2,
				RhythmValues.CROTCHET)), 0, 60);
		SingleNote me2 = new SingleNote(new MusicEventNote(new Note(Pitches.g2,
				RhythmValues.CROTCHET)), 3, 60);
		// me1 < me2 (2.0 secs difference)
		Assert.assertEquals(
				StreamSegregation.calcEventTimeDistance(me1.getNote(),
						me2.getNote()), 2.0);
		// me1 = me1 (-1.0 secs difference)
		System.out.println(StreamSegregation.calcEventTimeDistance(
				me1.getNote(), me1.getNote()));
		Assert.assertEquals(
				StreamSegregation.calcEventTimeDistance(me1.getNote(),
						me1.getNote()), -1.0);

	}
}
