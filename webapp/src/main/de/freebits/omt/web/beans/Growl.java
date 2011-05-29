package de.freebits.omt.web.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Bean for the messages being displayed inside the primefaces growl container.
 *
 * @author Marcel Karras
 */
@Named
@SessionScoped
public class Growl implements Serializable {

	private static final String INITIAL_MESSAGE = "OMT Engine Initialized";
	private static final String INITIAL_MESSAGE_DESCRIPTION = "OMT Engine has been initialized successfully...";

	private Logger logger;

	@PostConstruct
	private void initGrowl() {
		logger = Logger.getLogger(EvaluationWizard.class.getName());
		Handler handler = new Handler() {
			@Override
			public void publish(LogRecord record) {
				showMessage(record.getMessage(), "TEST");
			}

			@Override
			public void flush() {
			}

			@Override
			public void close() throws SecurityException {

			}
		};
		//logger.addHandler(handler);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

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
	 * @see Growl#showMessage(String, String)
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

	/**
	 * Show a new growl error message.
	 *
	 * @param message	 message header
	 * @param description message description
	 */
	public void showErrorMessage(final String message, final String description) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message,
				description));
	}
}
