package cz.martindobias.vivocatch.beans;

public class ServerParameters {
    public final SystemParameterGroup system;

    public ServerParameters(String url) {
        this.system = (SystemParameterGroup) ParameterGroupFactory.createInstance(SystemParameterGroup.class, url);
    }
}
