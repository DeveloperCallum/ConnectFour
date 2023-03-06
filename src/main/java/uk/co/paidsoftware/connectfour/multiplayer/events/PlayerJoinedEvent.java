package uk.co.paidsoftware.connectfour.multiplayer.events;

import uk.co.paidsoftware.connectfour.handler.HandlerList;
import uk.co.paidsoftware.connectfour.handler.events.PlayerEvent;
import uk.co.paidsoftware.connectfour.ConnectGameMultiplayer;
import uk.co.paidsoftware.connectfour.players.Player;

public class PlayerJoinedEvent implements PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    private ConnectGameMultiplayer game;
    private Player player;

    public PlayerJoinedEvent(ConnectGameMultiplayer game, Player player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public ConnectGameMultiplayer getGame() {
        return game;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean isAsync() {
        return true;
    }

    public static HandlerList getHandlers() {
        return handlerList;
    }
}
