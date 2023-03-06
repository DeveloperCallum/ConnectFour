package uk.co.paidsoftware.connectfour.handler.events;

import uk.co.paidsoftware.connectfour.ConnectGameMultiplayer;

public interface GameEvent extends Event{
     ConnectGameMultiplayer getGame();
}
