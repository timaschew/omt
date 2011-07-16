package de.freebits.omt.web.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class TestBean implements Serializable{

    private String testString;

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }
}
