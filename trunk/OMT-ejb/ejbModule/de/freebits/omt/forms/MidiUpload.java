package de.freebits.omt.forms;

import java.util.ArrayList;

import javax.ejb.Local;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

@Local
public interface MidiUpload {

	public boolean isUseFlash();

	public void setUseFlash(boolean useFlash);

	public int getUploadsAvailable();

	public void setUploadsAvailable(int uploadsAvailable);

	public int getSize();

	public ArrayList<UploadItem> getUploadItems();

	public void setUploadItems(ArrayList<UploadItem> uploadItems);

	public String clearUploadData();

	public void destroy();
	
	public void create();
	
	public void remove();
	
	public void listener(UploadEvent event) throws Exception;
	
	public void endConv();
	public void beginConv();

	// add additional interface methods here

}
