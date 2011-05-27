package cz.martindobias.vivocatch.network;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.Socket;

public class DownloadConnection implements Runnable {

    private final DefaultHttpClientConnection connection;
    private final BasicHttpParams httpParams;
    private String server;
    private int port;

    public DownloadConnection(final String server, final int port, final String id) {
        this.server = server;
        this.port = port;
        this.httpParams = new BasicHttpParams();
        this.httpParams.setParameter(HttpProtocolParams.PROTOCOL_VERSION, new ProtocolVersion("HTTP", 1, 0));
        this.httpParams.setParameter(HttpProtocolParams.USER_AGENT, "TunnelClient");
        this.httpParams.setParameter("x-sessioncookie", id);
        this.httpParams.setParameter("Accept", "application/x-vvtk-tunnelled");
        this.httpParams.setParameter("Pragma", "no-cache");
        this.httpParams.setParameter("Cache-Control", "no-cache");
        this.httpParams.setParameter("Connection", "Keep-Alive");
        this.connection = new DefaultHttpClientConnection();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        try {
            Socket socket = new Socket(this.server, this.port);
            this.connection.bind(socket, this.httpParams);

            BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest("GET", "/cgi-bin/admin/ctrlevent.cgi");
            request.setEntity(new StringEntity("\n"));
            this.connection.sendRequestHeader(request);

            HttpResponse response = this.connection.receiveResponseHeader();
            System.out.println(response);

            do {
                if(this.connection.isResponseAvailable(1)) {
                    this.connection.receiveResponseEntity(response);
                    System.out.println(EntityUtils.toString(response.getEntity()));
                }
                Thread.sleep(100);
            } while(true);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.connection.shutdown();
            } catch(IOException ignored) {
                ignored.printStackTrace();
            }
        }

    }
}
