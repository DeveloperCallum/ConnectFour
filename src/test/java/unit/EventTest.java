package unit;

import mock.NotifyListener;
import mock.TestEvent;
import mock.TestListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.paidsoftware.connectfour.handler.EventHandler;
import uk.co.paidsoftware.connectfour.handler.GameEventHandler;
import uk.co.paidsoftware.connectfour.scheduling.GameScheduler;

public class EventTest {

    @Test
    public void emitTest() throws InterruptedException {
        EventHandler eventHandler = GameEventHandler.getInstance();

        NotifyListener notifyListener = new NotifyListener(this);
        eventHandler.registerListener(notifyListener);

        GameScheduler.getInstance().submitTask(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            eventHandler.emitEvent(new TestEvent());
        });

        synchronized (this){
            this.wait(2000);
            Assertions.assertTrue(notifyListener.ran);
        }

        boolean pass = false;

        try {
            throw new Exception();
        } catch (Exception e) {
            pass = true;
        }

        Assertions.assertTrue(pass);
    }
}
