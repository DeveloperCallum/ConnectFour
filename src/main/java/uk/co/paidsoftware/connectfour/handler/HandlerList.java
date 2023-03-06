package uk.co.paidsoftware.connectfour.handler;

import uk.co.paidsoftware.connectfour.handler.events.Listener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HandlerList {
    private final Map<Listener, List<Method>> methodList = new HashMap<>();

    public Map<Listener, List<Method>> getListeners() {
        return methodList;
    }
}
