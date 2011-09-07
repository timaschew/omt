package de.freebits.omt.web.beans;

import de.freebits.omt.core.evaluation.OMTEvaluator;
import de.freebits.omt.core.tools.jMusicHelper;
import de.freebits.omt.web.javascript.JSDialog;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class Dashboard implements Serializable {

    public static final String SETTINGS_DIALOG_WIDGET_VAR = "wvSettingsDialog";
    public static final String EVALUATION_DIALOG_WIDGET_VAR = "wvEvaluationDialog";
    public static final String EVALUATION_PROGRESS_WIDGET_VAR = "wvEvaluationProgress";

    @Inject
    private Settings settings;

    @Inject
    private Growl growl;
    private DashboardModel model;
    private List<EvaluationSetup> evaluationSetups;
    private EvaluationSetup currentEvaluationSetup;
    private boolean showEvaluationProgress;

    @PostConstruct
    private void initDashboard() {
        evaluationSetups = new ArrayList<EvaluationSetup>();
        settings.setLevel(Settings.LEVEL_MEDIUM);
    }

    public Dashboard() {
        model = new DefaultDashboardModel();
        for (int i = 0; i < 10; i++) {
            model.addColumn(new DefaultDashboardColumn());
        }
    }

    public void addEvaluationSetup(final EvaluationSetup evaluationSetup) {
        this.evaluationSetups.add(evaluationSetup);
        model.getColumn(0).addWidget(evaluationSetup.getId());
    }

    public void handleReorder(DashboardReorderEvent event) {
        growl.showMessage("Reordered: " + event.getWidgetId(),
                "Item index: " + event.getItemIndex() + ", " +
                        "Column index: " + event.getColumnIndex() + ", " +
                        "Sender index: " + event.getSenderColumnIndex());

    }

    public void handleClose(CloseEvent event) {
        growl.showMessage("Close evaluation setup");
    }

    public List<EvaluationSetup> getEvaluationSetups() {
        return this.evaluationSetups;
    }

    public DashboardModel getModel() {
        return model;
    }

    public EvaluationSetup getCurrentEvaluationSetup() {
        return currentEvaluationSetup;
    }

    public void setCurrentEvaluationSetup(EvaluationSetup currentEvaluationSetup) {
        this.currentEvaluationSetup = currentEvaluationSetup;
    }

    /**
     * Perform backend evaluation and set evaluation results.
     */
    public void onEvaluationOpen() {
        Map<String, String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        String evaluationSetupId = params.get("evaluationSetup");
        EvaluationSetup evaluationSetup = null;
        // find evaluation setup with given id
        for (final EvaluationSetup es : evaluationSetups) {
            if (es.getId().equals(evaluationSetupId)) {
                evaluationSetup = es;
                break;
            }
        }

        assert (evaluationSetup != null);
        this.currentEvaluationSetup = evaluationSetup;
        final EvaluationResult er = new EvaluationResult();

        /**
         * Here the evaluation process takes place in the background engine.
         */
        double similarity = OMTEvaluator.calcSimilarity(OMTEvaluator.LS_ID_ALLE_VOEGEL_SIND_SCHON_DA,
                currentEvaluationSetup.getUploadedFile().getFile());


        er.setOverallSimilarity(similarity);
        er.addErrorTableEntry("Note entfernt", "hehe 123 -> " + jMusicHelper.getNameOfMidiPitch(65));
        er.addErrorTableEntry("Note entfernt", "hehe asdas dasd asd");
        this.currentEvaluationSetup.setResult(er);

        // show evaluation dialog
        JSDialog.open(EVALUATION_DIALOG_WIDGET_VAR);
    }

    /**
     * Method to be invoked on dashboard reset.
     *
     * @param actionEvent action event
     */
    public void onReset(final ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        // PageHelper.reloadPage(); <-- doesn't work
    }

    public void onSettingsSave(final ActionEvent actionEvent) {
        FacesContext.getCurrentInstance().getViewRoot().findComponent("levelChooser");
        JSDialog.close(SETTINGS_DIALOG_WIDGET_VAR);
    }
}

