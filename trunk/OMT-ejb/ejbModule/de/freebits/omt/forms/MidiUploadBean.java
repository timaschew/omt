package de.freebits.omt.forms;

import java.util.ArrayList;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

@Stateful
@Name("midiUpload")
@Scope(ScopeType.CONVERSATION)
public class MidiUploadBean implements MidiUpload {

	@Logger
	Log log;

	private ArrayList<UploadItem> uploadItems = new ArrayList<UploadItem>();
	private int uploadsAvailable = 5;
	private boolean useFlash = true;

	@Create
	public void create() {
		log.info("Create midiUpload");
	}

	@Destroy
	public void destroy() {
		log.info("Destroy midiUpload");
	}

	@Remove
	public void remove() {
		log.info("Remove midiUpload");
	}

	public void listener(UploadEvent event) throws Exception {
		UploadItem item = event.getUploadItem();
		uploadItems.add(item);
		uploadsAvailable--;
		if (uploadsAvailable == 0) {
			endConv();
		}
		log.info("Uploads available = #0", uploadsAvailable);
	}

	@End
	public void endConv() {
	}
	@Begin
	public void beginConv() {
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
}
