package de.freebits.omt.web.javascript;

import org.primefaces.context.RequestContext;

/**
 * Javascript wizard controller.
 *
 * @author Marcel Karras
 */
public class JSWizardDialog extends JSDialog{
    /**
     * Process to the next wizard step.
     *
     * @param widgetVar wizard widget variable
     */
    public static void next(final String widgetVar) {
        RequestContext.getCurrentInstance().execute(widgetVar + ".next();");
    }
}
