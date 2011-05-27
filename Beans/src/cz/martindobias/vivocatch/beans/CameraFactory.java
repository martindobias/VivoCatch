package cz.martindobias.vivocatch.beans;

public class CameraFactory {

    public Camera createInstance(String url) {
        return new Camera(url);
    }

}
