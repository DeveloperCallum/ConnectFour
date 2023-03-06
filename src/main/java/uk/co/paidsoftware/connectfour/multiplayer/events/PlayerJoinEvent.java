package uk.co.paidsoftware.connectfour.multiplayer.events;

import uk.co.paidsoftware.connectfour.ConnectGameMultiplayer;
import uk.co.paidsoftware.connectfour.players.Player;

public class PlayerJoinEvent extends PlayerJoinedEvent{
        public PlayerJoinEvent(ConnectGameMultiplayer game, Player player) {
        super(game, player);
    }
}
