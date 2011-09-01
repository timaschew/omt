package de.freebits.omt.web.beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * OMT Settings bean.
 */
@Named
@SessionScoped
public class Settings implements Serializable {

    public static final String LEVEL_EASY = "level_easy";
    public static final String LEVEL_MEDIUM = "level_medium";
    public static final String LEVEL_HARD = "level_hard";

    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
