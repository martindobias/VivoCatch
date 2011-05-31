package cz.martindobias.vivocatch.beans;

public class CameraFactory {

    public Camera createInstance(String address, int port, String base) {
        return new Camera(address, port, base);
    }

}
