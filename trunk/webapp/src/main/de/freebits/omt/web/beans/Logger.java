package de.freebits.omt.web.beans;

import de.freebits.omt.web.handler.LoggingHandler;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the central logging bean for all events. Other beans can inject and listen to events of this logger
 * in order to process them further on.
 */
@Named
@SessionScoped
public class Logger implements Serializable{

	private Set<LoggingHandler> loggingHandlers = new HashSet<LoggingHandler>();

	public Set<LoggingHandler> getLoggingHandlers() {
		return loggingHandlers;
	}

	public void setLoggingHandlers(Set<LoggingHandler> loggingHandlers) {
		this.loggingHandlers = loggingHandlers;
	}

	public void info(final String infoString){
		for(final LoggingHandler loggingHandler : loggingHandlers){
			loggingHandler.loggingEvent(infoString);
		}
	}

	public void addHandler(final LoggingHandler loggingHandler){
		loggingHandlers.add(loggingHandler);
	}
}
