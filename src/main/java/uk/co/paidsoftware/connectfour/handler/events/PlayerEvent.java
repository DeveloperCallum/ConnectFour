package uk.co.paidsoftware.connectfour.handler.events;

import uk.co.paidsoftware.connectfour.players.Player;

public interface PlayerEvent extends GameEvent{
    Player getPlayer();
}
