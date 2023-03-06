package mock;

import uk.co.paidsoftware.connectfour.handler.events.Listener;
import uk.co.paidsoftware.connectfour.multiplayer.events.PlayerJoinedEvent;

public class TestListener implements Listener {
    public void OnPlayerJoinEvent(PlayerJoinedEvent e){
        System.out.println("YES IT WORKS!");
    }
}
