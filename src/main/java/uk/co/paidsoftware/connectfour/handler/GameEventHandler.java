package uk.co.paidsoftware.connectfour.handler;

import uk.co.paidsoftware.connectfour.handler.events.Event;
import uk.co.paidsoftware.connectfour.handler.events.Listener;
import uk.co.paidsoftware.connectfour.scheduling.ConnectRunner;
import uk.co.paidsoftware.connectfour.scheduling.ConnectThread;
import uk.co.paidsoftware.connectfour.scheduling.TaskSetSync;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class GameEventHandler implements EventHandler {
    private static EventHandler gameEventHandler;
    private final ConnectThread connectThread;

    private GameEventHandler() {
        connectThread = new ConnectThread(new ConnectRunner(), "ConnectEventThread");
    }

    @Override
    public boolean emitEvent(Event event) {
        HandlerList handlerList = getStaticHandlers(event.getClass());

        handlerList.getListeners().forEach((listener, methods) -> {
            methods.forEach(method -> {
                try {
                    if (event.isAsync()) {
                        connectThread.getConnectRunner().submitTask(() -> {
                            try {
                                method.invoke(listener, event);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } else {
                        method.invoke(listener, event);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        return false;
    }

    @Override
    public void registerListener(Listener listener) {
        for (Method m : listener.getClass().getDeclaredMethods()) {
            Class<?>[] params = m.getParameterTypes();

            for (Class<?> c : params) {
                for (Type interfaces : c.getGenericInterfaces()) {
                    if (interfaces.equals(Event.class)) {
                        HandlerList handlerList = getStaticHandlers((Class<? extends Event>) c);

                        if (!handlerList.getListeners().containsKey(listener)) {
                            handlerList.getListeners().put(listener, new LinkedList<>());
                        }

                        List<Method> methods = handlerList.getListeners().get(listener);
                        methods.add(m);

                        break;
                    }
                }
            }
        }
    }

    @Override
    public void unregisterEvent(Event event, Listener listener) {
        HandlerList handlerList = getStaticHandlers(event.getClass());
        handlerList.getListeners().remove(listener);
    }

    private HandlerList getStaticHandlers(Class<? extends Event> event) {
        try {
            return (HandlerList) event.getDeclaredMethod("getHandlers").invoke(event);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Events require static getHandlers method!");
        }
    }

    public static EventHandler getInstance() {
        if (GameEventHandler.gameEventHandler == null) {
            gameEventHandler = new GameEventHandler();
        }

        return gameEventHandler;
    }

    public static void setGameEventHandler(EventHandler gameEventHandler) {
        if (GameEventHandler.gameEventHandler == null) {
            GameEventHandler.gameEventHandler = gameEventHandler;
        }
    }
}
