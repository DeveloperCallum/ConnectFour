package mock;

import uk.co.paidsoftware.connectfour.handler.events.Listener;

public class NotifyListener implements Listener {
    private Object object;
    public boolean ran = false;

    public NotifyListener(Object object) {
        this.object = object;
    }

    public void onTestEvent(TestEvent event){
        synchronized (object){
            ran = true;
            object.notify();
        }
    }
}
