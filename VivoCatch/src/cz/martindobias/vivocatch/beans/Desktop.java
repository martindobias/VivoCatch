package cz.martindobias.vivocatch.beans;

import java.util.List;

public class Desktop {
    private int width;
    private List cameras;

    public List<Camera> getCameras() {
        return cameras;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setCameras(List cameras) {
        this.cameras = cameras;
    }

}
