package de.freebits.omt.session;

import javax.ejb.Stateless;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
import org.jboss.seam.international.StatusMessages;

@Stateless
@Name("ping")
public class PingBean implements Ping
{
    @Logger private Log log;

    @In StatusMessages statusMessages;

    public void ping()
    {
        // implement your business logic here
        log.info("ping.ping() action called");
        statusMessages.add("ping");
    }

    // add additional action methods

}
