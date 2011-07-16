package de.freebits.omt.web.javascript;

import org.primefaces.context.RequestContext;

/**
 * Javascript wizard controller.
 *
 * @author Marcel Karras
 */
public class JSWizardDialog {
    /**
     * Process to the next wizard step.
     *
     * @param widgetVar wizard widget variable
     */
    public static final void next(final String widgetVar) {
        RequestContext.getCurrentInstance().execute(widgetVar + ".next();");
    }

    /**
     * Open the wizard dialog.
     *
     * @param widgetVar wizard dialog widget variable
     */
    public static final void open(final String widgetVar) {
        RequestContext.getCurrentInstance().execute(widgetVar + ".show();");
    }

    /**
     * Close the wizard dialog.
     *
     * @param widgetVar wizard dialog widget variable
     */
    public static final void close(final String widgetVar) {
        RequestContext.getCurrentInstance().execute(widgetVar + ".hide();");
    }
}
