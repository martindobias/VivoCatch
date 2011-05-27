package cz.martindobias.vivocatch.beans;

import static cz.martindobias.vivocatch.beans.ParameterAccess.ADMIN;
import static cz.martindobias.vivocatch.beans.ParameterType.*;

@ParameterGroupProperty(group = "system")
public abstract class SystemParameterGroup extends ParameterGroup {

    public SystemParameterGroup(String url) {
        super(url);
    }

    @ParameterProperty(name = "hostname", subdir = ADMIN, type = STRING)
    public abstract void setHostname(String hostname);

    @ParameterProperty(name = "hostname", subdir = ADMIN, type = STRING)
    public abstract String getHostname();

    @ParameterProperty(name = "timezoneindex", subdir = ADMIN, type = INTEGER)
    public abstract int getTimezoneIndex();

    @ParameterProperty(name = "ledoff", subdir = ADMIN, type = BOOLEAN)
    public abstract void setLedOff(boolean value);

    @ParameterProperty(name = "ledoff", subdir = ADMIN, type = BOOLEAN)
    public abstract Boolean getLedOff();
}
