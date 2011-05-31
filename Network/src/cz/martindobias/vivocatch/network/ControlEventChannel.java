package cz.martindobias.vivocatch.network;

public class ControlEventChannel {

    private DownloadConnection download;
    private UploadConnection upload;
    private String id;

    public ControlEventChannel() {
        this.generateId();
    }

    private void generateId() {
        this.id = "5AasdGTHfgjwsqDdSF33";
    }

    public void open(String server, int port) {
        this.close();
        this.download = new DownloadConnection(server, port, this.id);
        this.upload = new UploadConnection(server, port, this.id);
        this.download.startConnection();
        this.upload.startConnection();
    }

    public boolean isOpen() {
        return false;
    }

    public void close() {
        if(this.isOpen()) {

        }
    }
}
