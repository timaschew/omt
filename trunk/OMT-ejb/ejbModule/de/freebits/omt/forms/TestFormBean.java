package de.freebits.omt.forms;

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.log.Log;

@Stateful
@Name("testForm")
public class TestFormBean implements TestForm
{
    @Logger private Log log;

    @In StatusMessages statusMessages;

    private String value;

    public void testForm()
    {
        // implement your business logic here
        log.info("testForm.testForm() action called with: #{testForm.value}");
        statusMessages.add("testForm #{testForm.value}");
    }

    // add additional action methods

    @Length(max = 10)
    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Destroy
    @Remove
    public void destroy() {}

}
