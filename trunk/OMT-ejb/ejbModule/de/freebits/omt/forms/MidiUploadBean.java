package de.freebits.omt.forms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import jm.music.data.Score;
import jm.util.Read;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.log.Log;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

@Stateful
@Name("midiUpload")
@Scope(ScopeType.SESSION)
@Restrict
public class MidiUploadBean implements MidiUpload {

	private static final long serialVersionUID = -3089339676458374970L;

	@Logger
	Log log;

	private ArrayList<UploadItem> uploadItems = new ArrayList<UploadItem>();
	private int uploadsAvailable = 1;
	private boolean useFlash = true;
	private String analyzerResult = "<no result yet>";
	private boolean analyzeState = false;

	@Create
	public void create() {
		log.info("Create midiUpload");
	}

	@Remove
	@Destroy
	public void remove() {
		log.info("Destroy midiUpload");
	}

	public void upload(UploadEvent event) throws Exception {
		// UploadItem item = event.getUploadItem();
		// uploadItems.add(item);
		uploadsAvailable--;
	}

	public String clearUploadData() {
		uploadItems.clear();
		setUploadsAvailable(5);
		return null;
	}

	/**
	 * @param useFlash
	 *            the useFlash to set
	 */
	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

	/**
	 * @return the useFlash
	 */
	public boolean isUseFlash() {
		return useFlash;
	}

	/**
	 * @return the uploadsAvailable
	 */
	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	/**
	 * @param uploadsAvailable
	 *            the uploadsAvailable to set
	 */
	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	/**
	 * @return the uploadItems
	 */
	public ArrayList<UploadItem> getUploadItems() {
		return uploadItems;
	}

	/**
	 * @param uploadItems
	 *            the uploadItems to set
	 */
	public void setUploadItems(ArrayList<UploadItem> uploadItems) {
		this.uploadItems = uploadItems;
	}

	public int getSize() {
		return uploadItems.size();
	}

	public String getAnalyzerResult() {
		return analyzerResult;
	}

	public void analyze() {
		Score s = new Score();
		Read.midi(s, uploadItems.get(0).getFile().getAbsolutePath());
		analyzerResult = "Highest pitch = " + s.getHighestPitch();
		analyzeState = true;
	}

	public void playAudio(OutputStream stream, Object value) throws IOException {
		float[] data = Read.audio(uploadItems.get(0).getFile()
				.getAbsolutePath());
		log.info(data);
		// stream.write(data);
		stream.flush();
		stream.close();
	}

	public boolean isAnalyzeState() {
		return analyzeState;
	}

	public void setAnalyzeState(boolean analyzeState) {
		this.analyzeState = analyzeState;
	}

	public void resetView() {
		uploadItems.clear();
		setUploadsAvailable(1);
		setAnalyzeState(false);
	}
}
