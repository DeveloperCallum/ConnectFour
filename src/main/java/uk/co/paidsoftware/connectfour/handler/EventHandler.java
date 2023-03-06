package uk.co.paidsoftware.connectfour.handler;

import uk.co.paidsoftware.connectfour.handler.events.Event;
import uk.co.paidsoftware.connectfour.handler.events.Listener;

public interface EventHandler {
    boolean emitEvent(Event e);

    void registerListener(Listener listener);

    void unregisterEvent(Event event, Listener listener);
}
