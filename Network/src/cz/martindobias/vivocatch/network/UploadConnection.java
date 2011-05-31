package cz.martindobias.vivocatch.network;

public class UploadConnection extends Connection {

    protected UploadConnection(final String server, final int port, final String id) {
        super(server, port, id);
    }

    @Override
    public boolean prepare() {
        return true;
    }

    @Override
    public boolean handle() {
        return true;
    }

    @Override
    public void cleanup() {
    }
}
