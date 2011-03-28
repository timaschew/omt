import javax.faces.bean.ManagedBean;

/**
 * Bean for data that will be configured using the evaluation creation wizard.
 */
@ManagedBean
public class EvaluationWizardBean {
	private Song song;

	public final void setSong(final Song song){
		this.song = song;
	}
}
