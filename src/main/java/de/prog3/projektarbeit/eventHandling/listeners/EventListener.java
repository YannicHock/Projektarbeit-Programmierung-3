package de.prog3.projektarbeit.eventHandling.listeners;

import de.prog3.projektarbeit.eventHandling.EventHandler;
import de.prog3.projektarbeit.eventHandling.events.Event;

public abstract class EventListener<T extends Event> {
    private final Class<T> eventType;

    protected EventListener(Class<T> eventType) {
        this.eventType = eventType;
        register();
    }

    public abstract void onEvent(T event);

    private void register() {
        System.out.println("Registering listener for " + eventType.getSimpleName());
        EventHandler.getInstance().registerListener(eventType, this);
    }

    @Override
    public String toString() {
        return "EventListener{" +
                "eventType=" + eventType +
                '}';
    }
}