package de.prog3.projektarbeit.eventHandling.listeners;

import de.prog3.projektarbeit.eventHandling.EventHandler;
import de.prog3.projektarbeit.eventHandling.events.Event;

public abstract class EventListener<T extends Event> {
    private final Class<T> eventType;
    private final Priority priority;

    protected EventListener(Class<T> eventType) {
        this.eventType = eventType;
        this.priority = Priority.NORMAL;
        register();
    }

    protected EventListener(Class<T> eventType, Priority priority) {
        this.eventType = eventType;
        this.priority = priority;
        register();
    }

    private void register() {
        EventHandler.getInstance().registerListener(eventType,  this);
    }

    public void unregister() {
        EventHandler.getInstance().unregisterListener(eventType, this);
    }

    public int getPriority() {
        return priority.ordinal();
    }

    @Override
    public String toString() {
        return "EventListener{" +
                "eventType=" + eventType +
                '}';
    }

    public abstract void onEvent(T event);
}