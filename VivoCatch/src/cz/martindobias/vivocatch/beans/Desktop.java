package cz.martindobias.vivocatch.beans;

import java.util.List;

public class Desktop {
    private int width;
    private List cameras;
    private int rtspCaching;
    private int realRtspCaching;
    private int rtpCaching;

    public List<Camera> getCameras() {
        return this.cameras;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setCameras(List cameras) {
        this.cameras = cameras;
    }

    public void setRtspCaching(int rtspCaching) {
        this.rtspCaching = rtspCaching;
    }

    public int getRtspCaching() {
        return this.rtspCaching;
    }

    public void setRealRtspCaching(int realRtspCaching) {
        this.realRtspCaching = realRtspCaching;
    }

    public int getRealRtspCaching() {
        return this.realRtspCaching;
    }

    public void setRtpCaching(int rtpCaching) {
        this.rtpCaching = rtpCaching;
    }

    public int getRtpCaching() {
        return this.rtpCaching;
    }
}
