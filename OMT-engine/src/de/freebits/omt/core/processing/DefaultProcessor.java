package de.freebits.omt.core.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.freebits.omt.core.processing.events.ProcessingEvent;
import de.freebits.omt.core.processing.listener.ProcessingListener;

/**
 * Default implementation of a generic processor.
 * 
 * @author Marcel Karras
 */
public abstract class DefaultProcessor implements Processor {

	// list of registered event listeners
	private Map<Class<?>, List<ProcessingListener>> listeners = new HashMap<Class<?>, List<ProcessingListener>>();

	@Override
	public void listen(final Class<?> processingEventClass,
			final ProcessingListener processingListener) {
		// check if initial list has to be created for this event
		List<ProcessingListener> listenerList = null;
		if ((listenerList = listeners.get(processingEventClass)) == null) {
			listenerList = new ArrayList<ProcessingListener>();
		}
		// check if the listener is already registered to this event
		if (!listenerList.contains(processingListener)) {
			listenerList.add(processingListener);
		}
		// add/replace listener list
		listeners.put(processingEventClass, listenerList);
	}

	@Override
	public void signal(ProcessingEvent processingEvent) {
		for (ProcessingListener processingListener : listeners.get(processingEvent.getClass())) {
			processingListener.signal(processingEvent);
		}		
	}	
}
