package cz.martindobias.vivocatch.network;

public abstract class Connection implements Runnable {

    private static int SLEEP_TIME = 10;
    private Thread thread;
    private boolean shallStop = false;
    protected String server;
    protected int port;
    protected String id;

    protected Connection(final String server, final int port, final String id) {
        this.server = server;
        this.port = port;
        this.id = id;
    }

    public void startConnection() {
        if(this.thread == null) {
            this.thread = new Thread(this);
            this.shallStop = false;
            this.thread.start();
        }
    }

    public void stopConnection() {
        if(this.thread != null) {
            this.shallStop = true;
        }
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
        boolean shallStopByHandler;
        shallStopByHandler = this.prepare();
        while(!this.shallStop && !shallStopByHandler) {
            shallStopByHandler = this.handle();
            try {
                Thread.sleep(SLEEP_TIME);
            } catch(InterruptedException ignored) {
            }
        }
        this.thread = null;
        this.cleanup();
    }

    public abstract boolean prepare();

    public abstract boolean handle();

    public abstract void cleanup();
}
