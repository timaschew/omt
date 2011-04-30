package de.freebits.omt.core.tools;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import jm.constants.Frequencies;
import jm.constants.Pitches;
import jm.music.data.Note;
import jm.music.data.Part;
import jm.music.data.Phrase;
import jm.music.data.Score;
import jm.util.View;
import de.freebits.omt.core.AcousticConstants;
import de.freebits.omt.core.processing.streams.Cluster;
import de.freebits.omt.core.processing.streams.Clustering;
import de.freebits.omt.core.structures.Chord;
import de.freebits.omt.core.structures.MusicEvent;
import de.freebits.omt.core.structures.MusicEventNote;
import de.freebits.omt.core.structures.SingleNote;

public class jMusicHelper {
	/**
	 * Get the midi note name for a given pitch value.
	 * 
	 * @param pitch
	 *            a value from 0..127
	 * @return midi note name like in {@link Pitches}
	 * @see Class description of {@link Pitches}.
	 * @see Class description of {@link Frequencies}.
	 * 
	 * @author Marcel Karras
	 */
	public static final String getNameOfMidiPitch(final int pitch) {
		// use java reflection API
		final Field[] fields = Pitches.class.getFields();
		if (fields != null) {
			for (Field f : fields) {
				// try to find the pitch member variable
				try {
					if (f.getInt(null) == pitch) {
						// some exceptions
						String name = f.getName();
						if (name.contains("FF"))
							return name.replace("FF", "E");
						return f.getName();
					}
				} catch (IllegalArgumentException e) {
					return "";
				} catch (IllegalAccessException e) {
					return "";
				}
			}
		}
		return "";
	}

	/**
	 * Generate the music events list from the given jMusic Part object sorted
	 * by event start time.
	 * 
	 * @param part
	 *            the midi part in jMusic context
	 * @return list of higher semantic music structures
	 * 
	 * @author Marcel Karras
	 */
	public static final List<MusicEvent> generateMusicEventList(final Score s) {
		final Part partCopy = s.getPart(0).copy();
		List<MusicEvent> eventList = new ArrayList<MusicEvent>();
		// array of phrase note start times (initialized to 0)
		final double[] phraseNoteStartTimes = new double[partCopy.size()];
		// initialize phrase start times
		for (int i = 0; i < phraseNoteStartTimes.length; i++) {
			phraseNoteStartTimes[i] = partCopy.getPhrase(i).getStartTime();
		}

		while (true) {
			// 1. get the next note by lowest start time
			double minStartTime = phraseNoteStartTimes[0];
			int minStartPhraseIndex = 0;
			for (int i = 1; i < phraseNoteStartTimes.length; i++) {
				if (phraseNoteStartTimes[i] < minStartTime) {
					minStartTime = phraseNoteStartTimes[i];
					minStartPhraseIndex = i;
				}
			}
			// 2. loop end condition?
			final Phrase curPhrase = partCopy.getPhrase(minStartPhraseIndex);
			if (curPhrase.getStartTime() == Double.MAX_VALUE) {
				// end condition
				break;
			}
			final Note curNote = curPhrase.getNote(0);
			double startTimeInSeconds = minStartTime * 60.0 / s.getTempo();
			// double durationInSeconds = curNote.getDuration() * 60.0
			// / s.getTempo();
			curNote.setSampleStartTime(startTimeInSeconds);
			System.err.println(startTimeInSeconds + " (" + curNote.getNote()
					+ "," + curNote.getRhythmValue() + ")");
			if (!curNote.isRest()) {
				// 2. process note
				// if (eventList.size() == 0) {
				// eventList.add(new SingleNote(new MusicEventNote(curNote),
				// startTimeInSeconds, s.getTempo()));
				// } else {
				// final MusicEvent lastEvent = eventList
				// .get(eventList.size() - 1);
				// chord: equal onset times
				// && lastEvent.getEndTime() == (startTimeInSeconds +
				// durationInSeconds)
				// if (lastEvent.getStartTime() == startTimeInSeconds) {
				// if (lastEvent instanceof Chord) {
				// // add note to chord
				// ((Chord) lastEvent).addNote(new MusicEventNote(
				// curNote));
				// } else if (lastEvent instanceof SingleNote) {
				// // create new chord and include previous note
				// final Chord c = new Chord(
				// ((SingleNote) lastEvent).getNote(),
				// lastEvent.getStartTime(), s.getTempo());
				// c.addNote(new MusicEventNote(curNote));
				// eventList.set(eventList.size() - 1, c);
				// }
				// }
				// // single note
				// else {

				// always initialize as single note (higher level structures
				// will be recognized later)
				eventList.add(new SingleNote(new MusicEventNote(curNote),
						startTimeInSeconds, s.getTempo()));
				// }
				// }
			}

			// if (!curNote.isRest())
			// System.out.println("Phrase " + minStartPhraseIndex + ", Dyn " +
			// curNote.getDynamic()
			// + ", Note " + curNote.getPitch() + ", Start " + minStartTime +
			// ", Duration "
			// + curNote.getDuration() + ", Rhythm " +
			// curNote.getRhythmValue());

			// 3. remove note from its phrase
			curPhrase.removeNote(0);
			phraseNoteStartTimes[minStartPhraseIndex] = minStartTime
					+ curNote.getRhythmValue();

			// 4. check if phrase is empty
			if (curPhrase.size() == 0) {
				// no further notes inside this phrase
				phraseNoteStartTimes[minStartPhraseIndex] = Double.MAX_VALUE;
				curPhrase.setStartTime(Double.MAX_VALUE);
				continue;
			}
		}
		// link direct music event neighbors
		if (eventList.size() > 0) {
			// first element
			eventList.get(0).setPrev(null);
			// last element
			eventList.get(eventList.size() - 1).setNext(null);
			// inner elements
			for (int i = 1; i < eventList.size() - 1; i++) {
				if (i == 1) {
					// connect to first list element
					eventList.get(i - 1).setNext(eventList.get(i));
				}
				// set previous musical event
				eventList.get(i).setPrev(eventList.get(i - 1));
				// set next musical event
				eventList.get(i).setNext(eventList.get(i + 1));
				if (i == eventList.size() - 2) {
					// connect to last list element
					eventList.get(i + 1).setPrev(eventList.get(i));
				}
			}
		}
		eventList = groupSingleNotesToChords(eventList);
		return eventList;
	}

	/**
	 * Group the single note events into chords where applicable.
	 * 
	 * @param eventList
	 *            the event list to be analyzed
	 * @return event list with chords where applicable
	 */
	private static List<MusicEvent> groupSingleNotesToChords(
			final List<MusicEvent> eventList) {
		// resulting event list with grouped single notes
		final List<MusicEvent> groupedList = new ArrayList<MusicEvent>();
		// current eventList index
		int index = 0;
		do {
			final SingleNote note = (SingleNote) eventList.get(index);
			// 1. originating from this note search for notes with close onset
			// times
			final Chord chord = new Chord(note.getNote(), note.getStartTime(),
					note.getTempo());
			SingleNote nextNote = (SingleNote) note.getNext();
			index++;
			// null signals that the note event list end is reached
			while (nextNote != null) {
				assert (nextNote.getStartTime() >= note.getStartTime());
				// range check for start time differences
				if (nextNote.getStartTime() - note.getStartTime() <= AcousticConstants.MIN_DURATION_DIFFERENCE) {

					chord.addNote(nextNote.getNote());
					// increase current event list index pointer
					index++;
					// } else {
					// note.getNote().setMusicEvent(note);
					// groupedList.add(note);
					// System.out.println("Duplicate note found: " + nextNote);
					// }
				} else {
					// here we are too far away from chord origin
					break;
				}
				nextNote = (SingleNote) nextNote.getNext();
			}
			// 2. analyze further chord notes when available
			final List<MusicEventNote> chordNotes = chord.getNoteList();
			// one chord note always exists as we put every single note into a
			// chord once
			if (chordNotes.size() == 1) {
				// current single note does not belong to a chord, so we can put
				// it into the new grouped music event list
				note.getNote().setMusicEvent(note);
				groupedList.add(note);
			} else {
				// list of chord hypotheses
				final List<Chord> chordHypoList = new ArrayList<Chord>();
				// at this point all notes with an applicable onset are inside
				// the chord - now check chord IOIs and split where appropriate
				for (final MusicEventNote men : chordNotes) {
					if (chordHypoList.size() == 0) {
						// put the first chord note into a new chord
						chordHypoList.add(new Chord(men, men
								.getSampleStartTime(), note.getTempo()));
						continue;
					}
					// search for a chord candidate with an appropriate endtime
					for (final Chord c : chordHypoList) {
						// get chord notes endtime ranges
						double minEndTime = c.getMinEndTime();
						double maxEndTime = c.getMaxEndTime();
						// new range dimension when note would be added to this
						// chord
						if (men.getEndTime() > maxEndTime) {
							maxEndTime = men.getEndTime();
						} else if (men.getEndTime() < minEndTime) {
							minEndTime = men.getEndTime();
						}
						// check if chord endtime range dimensions still suit
						if (Math.abs(maxEndTime - minEndTime) <= 2
								* AcousticConstants.CHORD_ENDTIMES_DIFF_WEIGHT
								* AcousticConstants.MIN_DURATION_DIFFERENCE) {
							// put note into current chord
							c.addNote(men);
							break;
						} else {
							System.out.print("");
						}
					}
					// is note still in the old unsplitted chord structure?
					if (men.getMusicEvent().equals(chord)) {
						// this note doesn't fit into any existing chord
						// hypothesis, so create a new one
						chordHypoList.add(new Chord(men, men
								.getSampleStartTime(), note.getTempo()));
					} else {
						// note is part of a chord hypothese
						// TODO: what todo here?
					}
				}
				// 3. evaluate chord hypotheses
				for (final Chord c : chordHypoList) {
					assert (c.getNoteList().size() > 0);
					if (c.getNoteList().size() == 1) {
						// if the chord note list size is one, this note doesn't
						// fit into a chord and will be placed in a single note
						// container
						final MusicEventNote men = c.getNoteList().get(0);
						groupedList.add(new SingleNote(men, men
								.getSampleStartTime(), note.getTempo()));
					} else {
						// assert that note is of unique pitch inside the chord
						// - notes with equal pitch are assumed to belong to
						// other higher level structures
						final List<MusicEventNote> toBeRemoved = new ArrayList<MusicEventNote>();
						for (final MusicEventNote n1 : c.getNoteList()) {
							for (final MusicEventNote n2 : c.getNoteList()) {
								if (!((Object) n1).equals((Object) n2)
										&& n1.getPitch() == n2.getPitch()) {
									boolean alreadyRemoved = false;
									for (final MusicEventNote remNote : toBeRemoved) {
										if (remNote.getPitch() == n1.getPitch())
											alreadyRemoved = true;
									}
									if (!alreadyRemoved) {
										n1.setMusicEvent(note);
										groupedList.add(note);
										toBeRemoved.add(n1);
										System.out
												.println("Duplicate note found: "
														+ nextNote);
									}
								}
							}
						}
						c.getNoteList().removeAll(toBeRemoved);
						// the chord can be placed in the grouped event list
						groupedList.add(c);
					}
				}
			}

		} while (index < eventList.size());

		// TODO: linking

		return groupedList;
	}

	/**
	 * Visualize the music event list in a new view.
	 * 
	 * @param meList
	 *            music event list
	 * @throws Exception
	 *             if list is empty
	 */
	public static final void visualizeMusicEventList(
			final List<MusicEvent> meList, final String title) throws Exception {
		if (meList.size() == 0) {
			throw (new Exception(
					"Cannot visualize music event list as list is empty"));
		}
		final List<Part> partList = new ArrayList<Part>();
		for (final MusicEvent me : meList) {
			// single note event
			if (me instanceof SingleNote) {
				Part p = new Part();
				if (((SingleNote) me).getNote().isRest())
					continue;
				p.addNote(((SingleNote) me).getNote(),
						convertToBeatTime(me.getStartTime(), me.getTempo()));
				partList.add(p);
			} else if (me instanceof Chord) {
				for (final MusicEventNote note : me.getNoteList()) {
					Part p = new Part();
					if (note.isRest())
						continue;
					p.addNote(note,
							convertToBeatTime(me.getStartTime(), me.getTempo()));
					partList.add(p);
				}
			}
		}
		final Score score = new Score(title);
		score.setTempo(meList.get(0).getTempo());
		for (final Part p : partList) {
			score.add(p);
		}
		View.show(score);
	}

	public static final void visualizeClustering(final Clustering clustering)
			throws Exception {
		int i = 1;
		for (final Cluster c : clustering) {
			visualizeMusicEventList(convertMusicEventList(c),
					"Cluster " + (i++) + " - Start: " + c.getStartTime()
							+ "s - End: " + c.getEndTime() + "s");
		}
	}

	/**
	 * Convert a music event note list to a jMusic note array.
	 * 
	 * @param menList
	 *            music event note list
	 * @return note array
	 * 
	 * @author Marcel Karras
	 */
	public static final Note[] convertToNoteArray(
			final List<MusicEventNote> menList) {
		final Note[] noteArray = new Note[menList.size()];
		// double length = menList.get(0).getRhythmValue();
		for (int i = 0; i < menList.size(); i++) {
			noteArray[i] = menList.get(i);
			/*
			 * if (noteArray[i].getRhythmValue() != length) {
			 * System.err.println("Chord rhythm values not equal"); }
			 */
		}
		return noteArray;
	}

	/**
	 * Convert a music event note list to a music event list
	 * 
	 * @param menList
	 *            music event note list
	 * @return music event list
	 * 
	 * @author Marcel Karras
	 */
	public static final List<MusicEvent> convertMusicEventList(
			final List<MusicEventNote> menList) {
		final List<MusicEvent> meList = new ArrayList<MusicEvent>();
		// double length = menList.get(0).getRhythmValue();
		for (int i = 0; i < menList.size(); i++) {
			MusicEvent me = menList.get(i).getMusicEvent();
			if (me instanceof Chord) {
				// don't insert chords into view, just the single note
				me = new SingleNote(menList.get(i), menList.get(i)
						.getSampleStartTime(), me.getTempo());
			}
			meList.add(menList.get(i).getMusicEvent());
			/*
			 * if (menList.get(i).getRhythmValue() != length) {
			 * System.err.println("Chord rhythm values not equal"); }
			 */
		}
		return meList;
	}

	/**
	 * Calculate the MD5 hash of the given string.
	 * 
	 * @param s
	 *            string to be calculated
	 * @return md5 hash value
	 */
	public static final String calcMD5Hash(final String s) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			return new String(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Convert the given time in seconds into a beat time.
	 * 
	 * @param time
	 *            time in seconds
	 * @param tempo
	 *            tempo in bpm
	 * @return beat time relative to tempo
	 */
	public static final double convertToBeatTime(final double time,
			final double tempo) {
		return time * tempo / 60.0;
	}
}
