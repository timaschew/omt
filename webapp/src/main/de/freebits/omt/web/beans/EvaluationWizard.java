package de.freebits.omt.web.beans;

import de.freebits.omt.web.handler.LoggingHandler;
import de.freebits.omt.web.helpers.FileHelper;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Bean for the evaluation creation wizard that will get the desired song and midi from the user.
 *
 * @author Marcel Karras
 */
@Named
@SessionScoped
public class EvaluationWizard implements Serializable, LoggingHandler {

	// selected song to be evaluated
	private Song selectedSong;

	// song database of available songs
	@Inject
	private SongDatabase songDatabase;

	// growl bean for display of messages
	@Inject
	private Growl growl;

	// uploaded midi file resource
	private File midiUploadFile;

	private String status;

	@Inject
	private Logger logger;

	private Wizard wizard;

	public Wizard getWizard() {
		return wizard;
	}

	public void setWizard(Wizard wizard) {
		this.wizard = wizard;
	}

	/**
	 * This method will be executed when the song database has been injected.
	 */
	@PostConstruct
	private void initWizard() {
		logger.addHandler(this);
		// set default selected song
		selectedSong = (Song) songDatabase.getSongList()[0].getValue();
	}

	/**
	 * Set the song that to be evaluated.
	 *
	 * @param song song object
	 */
	public void setSelectedSong(final Song song) {
		selectedSong = song;
	}

	/**
	 * Get the song that will be evaluated.
	 *
	 * @return song object
	 */
	public Song getSelectedSong() {
		return selectedSong;
	}

	/**
	 * Handler for midi file uploads.
	 *
	 * @param event upload event
	 */
	public void handleFileUpload(final FileUploadEvent event) {
		// get the real path for the upload directory
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		File midiFile = new File(extContext.getRealPath("/upload") + "/" + event.getFile().getFileName());

		try {
			// write temporary file to a real file destination
			FileHelper.writeInputToOutputStream(event.getFile().getInputstream(), new FileOutputStream(midiFile));
			// set uploaded file for current wizard process
			midiUploadFile = midiFile;
			// success message
			growl.showMessage("MIDI Upload erfolgreich", event.getFile().getFileName() +
					" wurde erfolgreich hochgeladen.");
			setStatus("TEST 123");
		} catch (IOException e) {
			// error message
			growl.showErrorMessage("Fehler", event.getFile().getFileName() + " konnte nicht hochgeladen werden.");
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Listener method for wizard flow processes.
	 *
	 * @param event flow event
	 * @return string representing the next step to go to
	 */
	public String onFlowProcess(final FlowEvent event) {
		//logger.info("Current wizard step:" + event.getOldStep());
		//logger.info("Next step:" + event.getNewStep());

		logger.info("Testnachricht");

		return event.getNewStep();
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void loggingEvent(final String logEvent) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("lala","po"));
	}
}
