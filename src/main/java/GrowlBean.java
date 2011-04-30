import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Bean for the messages being displayed inside the primefaces growl container.
 */
@Named
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