package cz.martindobias.vivocatch.beans;

public class Camera {
    public final ServerParameters serverParameters;

    Camera(String url) {
        this.serverParameters = new ServerParameters(url);
    }

}
