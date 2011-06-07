package cz.martindobias.vivocatch.beans;

public class Camera {
    public final ServerParameters serverParameters;
    private String address;
    private int port;
    private String name;
    private String stream;

    public Camera(String address, int port, String base) {
        this.address = address;
        this.port = port;
        this.serverParameters = new ServerParameters(String.format("http://%s%s%s", address, port > 0 ? ":" + port : "", base));
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getStream() {
        return stream;
    }
}
