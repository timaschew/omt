package de.freebits.omt.session;

import javax.ejb.Local;

@Local
public interface Ping
{
    // seam-gen method
    public void ping();

    // add additional interface methods here

}
