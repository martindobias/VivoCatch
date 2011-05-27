package cz.martindobias.vivocatch.network;

public class ControlEventChannel {

    private DownloadConnection download;

    public ControlEventChannel() {
    }

    public void open(String server, int port) {
        this.close();
        this.download = new DownloadConnection(server, port, "5AasdGTHfgjwsqDdSF33");
        Thread thread = new Thread(this.download);
        thread.start();
    }

    public boolean isOpen() {
        return false;
    }

    public void close() {

    }
}
