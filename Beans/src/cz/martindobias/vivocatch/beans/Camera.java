package cz.martindobias.vivocatch.beans;

public abstract class Camera {
    private String ip;

    Camera(String ip) {
        this.ip = ip;
    }

    @CameraProperty(name = "hostname", subdir = CameraSubdir.ADMIN, type = String.class)
    public abstract void setName(String name);

    @CameraProperty(name = "hostname", subdir = CameraSubdir.ADMIN, type = String.class)
    public abstract String getName();
}
