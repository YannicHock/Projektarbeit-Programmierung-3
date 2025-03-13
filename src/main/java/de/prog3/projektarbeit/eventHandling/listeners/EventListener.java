package de.prog3.projektarbeit.eventHandling.listeners;

import de.prog3.projektarbeit.eventHandling.EventHandler;
import de.prog3.projektarbeit.eventHandling.events.Event;

public abstract class EventListener<T extends Event> {
    private final Class<T> eventType;

    protected EventListener(Class<T> eventType) {
        this.eventType = eventType;
        register();
    }

    private void register() {
        EventHandler.getInstance().registerListener(eventType,  this);
    }

    public void unregister() {
        EventHandler.getInstance().unregisterListener(eventType, this);
    }

    @Override
    public String toString() {
        return "EventListener{" +
                "eventType=" + eventType +
                '}';
    }

    public abstract void onEvent(T event);
}