package de.freebits.omt.web.handler;

/**
 * Event logging handler interface for classes that need to be informed on logging events.
 *
 * @author Marcel Karras
 */
public interface LoggingHandler {

    /**
     * Event handler method that is being invoked on event occurrence.
     *
     * @param logEvent logged event
     */
    public void loggingEvent(final String logEvent);
}
