package de.freebits.omt.web.helpers;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * Helper class for page manipulation methods.
 */
public class PageHelper {

    /**
     * Reload the current active page.
     */
    public static void reloadPage() {
        FacesContext context = FacesContext.getCurrentInstance();

        String viewId = context.getViewRoot().getViewId();

        ViewHandler handler = context.getApplication().getViewHandler();

        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);

    }
}
