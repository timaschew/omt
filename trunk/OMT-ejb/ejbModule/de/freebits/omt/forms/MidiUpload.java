package de.freebits.omt.forms;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Local;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

@Local
public interface MidiUpload extends Serializable {

	public boolean isUseFlash();

	public void setUseFlash(boolean useFlash);

	public int getUploadsAvailable();

	public void setUploadsAvailable(int uploadsAvailable);

	public int getSize();

	public ArrayList<UploadItem> getUploadItems();

	public void setUploadItems(ArrayList<UploadItem> uploadItems);

	public String clearUploadData();

	public void remove();

	public void create();

	public void upload(UploadEvent event) throws Exception;

	public void analyze();

	public String getAnalyzerResult();

	public boolean isAnalyzeState();

	public void setAnalyzeState(boolean analyzeState);

	public void playAudio(OutputStream stream, Object value) throws IOException;

	public void resetView();
}
