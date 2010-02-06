package de.freebits.omt.forms;

import javax.ejb.Local;

@Local
public interface TestForm
{
    public void testForm();
    public String getValue();
    public void setValue(String value);
    public void destroy();

    // add additional interface methods here

}
