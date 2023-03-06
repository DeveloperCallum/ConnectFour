package uk.co.paidsoftware.connectfour.scheduling;

public class ConnectThread extends Thread {
    private ConnectRunner connectRunner;

    public ConnectThread(ConnectRunner connectRunner) {
        super(connectRunner);
        this.connectRunner = connectRunner;
    }

    public ConnectThread(ConnectRunner connectRunner, String name) {
        super(connectRunner, name);
        this.connectRunner = connectRunner;
    }

    public ConnectRunner getConnectRunner() {
        return connectRunner;
    }
}
