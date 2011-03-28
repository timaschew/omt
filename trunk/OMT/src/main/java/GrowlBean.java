import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Bean for the messages being displayed inside the primefaces growl container.
 */
@ManagedBean(name = "growlBean")
@SessionScoped
public class GrowlBean {

	private static final String INITIAL_MESSAGE = "OMT Engine Initialized";
	private static final String INITIAL_MESSAGE_DESCRIPTION = "OMT Engine has been initialized successfully...";

	/**
	 * Display the initial system message when system startup is complete.
	 */
	public void showInitialMessage() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(INITIAL_MESSAGE, INITIAL_MESSAGE_DESCRIPTION));
	}

	/**
	 * Show a new growl message.
	 *
	 * @param message message header
	 * @see GrowlBean#showMessage(String, String)
	 */
	public void showMessage(final String message) {
		showMessage(message, null);
	}

	/**
	 * Show a new growl message.
	 *
	 * @param message	 message header
	 * @param description message description
	 */
	public void showMessage(final String message, final String description) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message, description));
	}
}