package de.freebits.omt.core.streams;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	private static final String TEST_FILE_DIR = "tests/ressources/streaming/";

	private static List<MusicEvent> eventList = null;
	private static JProgressBar progressBar = null;
	private static JPanel panel = null;
	private static JPanel pTestChooser = null;
	private static JFrame frame = null;
	private static JFrame fTestChooser = null;
	private static JTextArea taskOutput = null;
	private static JScrollPane scrollPane = null;
	private static JComboBox cbTestChooser = null;
	private static JButton bTestChooser = null;
	private static JLabel lTestChooser = null;

	/**
	 * Initialize this test class.
	 */
	@BeforeClass
	public static void initialize() {
		frame = new JFrame("Clustering Test");
		panel = new JPanel(new BorderLayout());
		frame.setContentPane(panel);
		frame.setSize(500, 500);
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				fTestChooser.setVisible(true);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		fTestChooser = new JFrame("Test Suite Chooser");
		pTestChooser = new JPanel(new BorderLayout());
		cbTestChooser = new JComboBox();
		bTestChooser = new JButton("Analyze");
		lTestChooser = new JLabel("Test Case: ");
		fTestChooser.setContentPane(pTestChooser);
		fTestChooser.setSize(500, 90);
		pTestChooser.add(cbTestChooser, BorderLayout.CENTER);
		pTestChooser.add(bTestChooser, BorderLayout.SOUTH);
		pTestChooser.add(lTestChooser, BorderLayout.WEST);

		// add test case dropdown options
		final File testDir = new File(TEST_FILE_DIR);
		final FileFilter fileFilter = new FileFilter() {
			public boolean accept(File file) {
				return !file.isDirectory();
			}
		};
		final File[] fileList = testDir.listFiles(fileFilter);
		for (final File f : fileList) {
			cbTestChooser.addItem(f.getName());
		}
		// action listener for "analyze" button
		bTestChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fTestChooser.setVisible(false);
				/**
				 * Swing Worker for the stream segregation background process.
				 * 
				 * @author Marcel Karras
				 */
				class ClusteringWorker extends SwingWorker<Clustering, Void> {

					@Override
					public Clustering doInBackground() {
						final StreamSegregation strSegr = new StreamSegregation(
								eventList);
						// register progress listeners
						strSegr.addProgressListener(new ProgressEventListener() {
							@Override
							public void signalProgressChange(ProgressEvent event) {
								setProgress(event.getProgress());
								taskOutput.append(String.format("[%5d%%]\t"
										+ event.getDescription() + "\n",
										event.getProgress()));

							}
						});
						// generate clustering
						final Clustering clustering = strSegr
								.generateClustering();
						return clustering;
					}

					@Override
					public void done() {
						Toolkit.getDefaultToolkit().beep();
						try {
							jMusicHelper.visualizeMusicEventList(eventList,
									"Original MIDI");
							jMusicHelper.visualizeClustering(get());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				Read.midi(score,
						TEST_FILE_DIR + cbTestChooser.getSelectedItem());
				eventList = jMusicHelper.generateMusicEventList(score);
				frame.setVisible(true);
				frame.setAlwaysOnTop(true);
				final ClusteringWorker cw = new ClusteringWorker();
				cw.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent pce) {
						if (pce.getPropertyName().equals("progress"))
							progressBar.setValue((Integer) pce.getNewValue());
					}
				});
				cw.execute();
				System.out.println("Cluster Test done.");
			}
		});
	}

	/**
	 * Test method for {@link StreamSegregation#generateClustering()}
	 */
	@Test
	public void testClustering() {
		fTestChooser.setVisible(true);
		fTestChooser.setAlwaysOnTop(true);
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
