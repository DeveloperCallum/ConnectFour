package uk.co.paidsoftware.connectfour.multiplayer.events;

import uk.co.paidsoftware.connectfour.ConnectGameMultiplayer;
import uk.co.paidsoftware.connectfour.handler.events.GameEvent;

public class GameWaitingEvent implements GameEvent {
    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public ConnectGameMultiplayer getGame() {
        return null;
    }
}
