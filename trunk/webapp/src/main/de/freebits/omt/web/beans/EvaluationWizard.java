package de.freebits.omt.web.beans;

import de.freebits.omt.web.handler.LoggingHandler;
import de.freebits.omt.web.helpers.FileHelper;
import de.freebits.omt.web.javascript.JSWizardDialog;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
@ConversationScoped
public class EvaluationWizard implements Serializable, LoggingHandler {

    /**
     * Widget variable for the wizard component.
     */
    public static final String WIZARD_WIDGET_VAR = "wvWizard";
    public static final String DIALOG_WIDGET_VAR = "wvCreationWizard";

    @Inject
    private TestBean testBean;

    @Inject
    private Conversation conversation;

    // selected song to be evaluated
    @Inject
    private Song selectedSong;

    // song database of available songs
    @Inject
    private SongDatabase songDatabase;

    // growl bean for display of messages
    @Inject
    private Growl growl;

    private Wizard wizard;

    public Wizard getWizard() {
        return wizard;
    }

    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }

    // uploaded midi file resource
    private File midiUploadFile;

    private String status;

    @Inject
    private Logger logger;

    /**
     * This method will be executed after all beans have been injected.
     */
    @PostConstruct
    private void initWizard() {
        logger.addHandler(this);
        // set default selected song
        selectedSong = (Song) songDatabase.getSongList()[0].getValue();
        testBean.setTestString(System.currentTimeMillis()+"");
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
        if (event.getOldStep().compareTo("tabSelectEvaluation") == 0) {
            return "tabSelectSong";
        }
        return event.getNewStep();
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void loggingEvent(final String logEvent) {
        growl.showMessage(logEvent);
    }

    /**
     * Called if the wizard processes to the next step.
     *
     * @param actionEvent the action event of wizard next step button
     */
    public void onNext(final ActionEvent actionEvent) {
        if (wizard.getStep().compareTo("tabSelectEvaluation") == 0) {
            JSWizardDialog.close(DIALOG_WIDGET_VAR);
            JSWizardDialog.next(WIZARD_WIDGET_VAR);
        } else {
            JSWizardDialog.next(WIZARD_WIDGET_VAR);
        }
    }

    public void startConversation(){
        if(conversation.isTransient()){
            conversation.begin();
            testBean.setTestString(System.currentTimeMillis()+"");
        }
    }

    /**
     * Called on wizard dialog opening.
     *
     * @param actionEvent the action event of the component opening the dialog
     */
    public void onDialogOpen(final ActionEvent actionEvent) {
        // start conversation on first wizard step
        //if (wizard.getStep().compareTo("tabSelectSong") == 0) {
        //    conversation.begin();
        //}
        // start a new conversation on first wizard step
        JSWizardDialog.open(DIALOG_WIDGET_VAR);
    }

    /**
     * Zurücksetzen der Wizard Backing Bean.
     *
     * @param closeEvent the close event of the closing dialog
     */
    public void onDialogClose(final CloseEvent closeEvent) {
        // end conversation when wizard dialog closes
        if(!conversation.isTransient()){
            conversation.end();
        }
    }
}
