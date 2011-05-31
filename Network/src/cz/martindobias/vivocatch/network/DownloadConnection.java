package cz.martindobias.vivocatch.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadConnection extends Connection {

    private HttpURLConnection connection;
    private byte[] readBuffer = new byte[1024 * 8];
    private byte[] messageBuffer = new byte[1024 * 128];
    private int messageBufferLength = 0;
    private boolean needsConfirmation = true;

    protected DownloadConnection(final String server, final int port, final String id) {
        super(server, port, id);
    }

    @Override
    public boolean handle() {
        try {
            if(this.connection != null) {
                // read as much as possible
                InputStream is = this.connection.getInputStream();
                int count;
                do {
                    if(is.available() > 0) {
                        count = is.read(this.readBuffer);
                        if(count > 0) {
                            System.arraycopy(this.readBuffer, 0, this.messageBuffer, this.messageBufferLength, count);
                            this.messageBufferLength += count;
                        }
                    } else {
                        count = 0;
                    }
                } while(count > 0);

                // decode
                if(this.needsConfirmation) {
                    // first message has defined format: http tunnel accept=0|1
                    if(this.messageBufferLength >= 20) {
                        if(new String(this.messageBuffer, 0, 19, "ASCII").equals("http tunnel accept=")) {
                            if(this.messageBuffer[19] == '1') {
                                this.shiftMessageBuffer(20);
                                this.needsConfirmation = false;
                                return false;
                            }
                        }
                        return true;
                    }
                } else {
                    // decode XML message (at least 3 bytes read - delimeter, length, data)
                    if(this.messageBufferLength >= 3) {
                        // try to resync first (search for 0xFF)
                        int i;
                        for(i = 0; i < this.messageBufferLength && this.messageBuffer[i] != -1; i++) {
                        }
                        if(i > 0) {
                            this.shiftMessageBuffer(i);
                        }

                        // decode data length
                        int realLength = 0;
                        int length = this.messageBuffer[1];
                        if(length < 0) {
                            // need to read length first
                            length += 128;
                            // only continue if whole length arrived
                            if(this.messageBufferLength >= length + 2) {
                                for(int j = 0; j < length; j++) {
                                    realLength *= 256;
                                    realLength += this.messageBuffer[j + 2];
                                }
                                // set length var to length of read field
                                length += 1;
                            }
                        } else {
                            realLength = length;
                            // set length var to length of read field
                            length = 1;
                        }

                        // are data ready? consume!
                        if(this.messageBufferLength >= realLength + length + 1) {
                            String xml = new String(this.messageBuffer, length + 1, this.messageBufferLength - length - 1, "ASCII");
                            this.shiftMessageBuffer(realLength + length + 1);
                            System.out.println(xml);
                        }
                    }

                    if(count == -1) {
                        return true;
                    }
                }
            }
        } catch(IOException ignored) {
            ignored.printStackTrace();
        }
        return false;
    }

    private void shiftMessageBuffer(final int length) {
        this.messageBufferLength -= length;
        System.arraycopy(this.messageBuffer, length, this.messageBuffer, 0, this.messageBufferLength);
    }

    @Override
    public void cleanup() {
        if(this.connection != null) {
            this.connection.disconnect();
            this.connection = null;
        }
    }

    @Override
    public boolean prepare() {
        this.cleanup();
        try {
            URL url = new URL("http", this.server, this.port, "/cgi-bin/admin/ctrlevent.cgi");
            this.connection = (HttpURLConnection) url.openConnection();
            this.connection.setRequestMethod("GET");
            this.connection.setRequestProperty("User-Agent", "TunnelClient");
            this.connection.setRequestProperty("x-sessioncookie", this.id);
            this.connection.setRequestProperty("Accept", "application/x-vvtk-tunnelled");
            this.connection.setRequestProperty("Pragma", "no-cache");
            this.connection.setRequestProperty("Cache-Control", "no-cache");
            this.connection.setRequestProperty("Connection", "Keep-Alive");

            this.connection.connect();
            return false;
        } catch(IOException ignored) {
            ignored.printStackTrace();
            return true;
        }
    }
}
