package mock;

import uk.co.paidsoftware.connectfour.handler.HandlerList;
import uk.co.paidsoftware.connectfour.handler.events.Event;

public class TestEvent implements Event {
    private static HandlerList handlerList = new HandlerList();

    @Override
    public boolean isAsync() {
        return false;
    }

    public static HandlerList getHandlers() {
        return handlerList;
    }
}
