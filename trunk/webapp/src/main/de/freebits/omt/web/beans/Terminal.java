package de.freebits.omt.web.beans;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Terminal bean.
 *
 * @author Marcel Karras
 */
@Named
@SessionScoped
public class Terminal implements Serializable {

    @Inject
    private Growl growl;

    public Growl getGrowl() {
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public String handleCommand(final String command, final String[] params) {
        growl.showMessage("TESTNACHRICHT");
        return "Befehl nicht gefunden: " + command;
    }
}
