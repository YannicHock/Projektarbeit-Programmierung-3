package de.prog3.projektarbeit.eventHandling;

import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

import java.util.HashMap;
import java.util.Map;

public class EventHandler {

    private static EventHandler instance;

    private final Map<Class<? extends Event>, EventListener> listeners;

    private EventHandler() {
        listeners = new HashMap<>();
    }

    public static EventHandler getInstance() {
        if(instance == null) {
            instance = new EventHandler();
        }
        return instance;
    }

    public void registerListener(Class<? extends Event> event, EventListener listener) {
        getInstance().listeners.put(event, listener);
    }

    public void callEvent(Event event) {
        listeners.get(event.getClass()).onEvent(event);
    }

}
