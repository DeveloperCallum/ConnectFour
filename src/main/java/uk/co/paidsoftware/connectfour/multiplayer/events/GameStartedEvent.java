package uk.co.paidsoftware.connectfour.multiplayer.events;

import uk.co.paidsoftware.connectfour.ConnectGameMultiplayer;
import uk.co.paidsoftware.connectfour.handler.events.GameEvent;

public class GameStartedEvent implements GameEvent {
    private ConnectGameMultiplayer gameMultiplayer;

    public GameStartedEvent(ConnectGameMultiplayer gameMultiplayer) {
        this.gameMultiplayer = gameMultiplayer;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    @Override
    public ConnectGameMultiplayer getGame() {
        return gameMultiplayer;
    }
}
