package de.freebits.omt.web.javascript;

import org.primefaces.context.RequestContext;

/**
 * Abstract class for javascript based dialogs.
 */
public abstract class JSDialog {

    /**
     * Open the dialog.
     *
     * @param widgetVar dialog widget variable
     */
    public static void open(final String widgetVar) {
        RequestContext.getCurrentInstance().execute(widgetVar + ".show();");
    }

    /**
     * Close the dialog.
     *
     * @param widgetVar dialog widget variable
     */
    public static void close(final String widgetVar) {
        RequestContext.getCurrentInstance().execute(widgetVar + ".hide();");
    }
}
