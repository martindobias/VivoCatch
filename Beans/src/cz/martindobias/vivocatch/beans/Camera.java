package cz.martindobias.vivocatch.beans;

public class Camera {
    public final ServerParameters serverParameters;
    private String address;
    private int port;

    Camera(String address, int port, String base) {
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
}
