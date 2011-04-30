import org.primefaces.event.FlowEvent;

import javax.faces.event.PhaseId;
import javax.inject.Named;

/**
 * Bean for data that will be configured using the evaluation creation wizard.
 */
@Named
public class EvaluationWizardBean {
	private Song song;

	public final void setSong(final Song song) {
		this.song = song;
	}

	public String handleFlow(FlowEvent event) {
		final PhaseId pi = event.getPhaseId();
		return event.getNewStep();
	}
}
