package cz.martindobias.vivocatch.beans;

@ParameterGroupProperty(group = "system")
public abstract class SystemParameterGroup extends ParameterGroup {

    public SystemParameterGroup(String url) {
        super(url);
    }

    @ParameterProperty(name = "hostname", subdir = ParameterAccess.ADMIN, type = String.class)
    public abstract void setHostname(String hostname);
    @ParameterProperty(name = "hostname", subdir = ParameterAccess.ADMIN, type = String.class)
    public abstract String getHostname();

}
