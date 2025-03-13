package de.prog3.projektarbeit.eventHandling.events;

import de.prog3.projektarbeit.eventHandling.EventHandler;

public abstract class Event {

    private final String name;

    public Event(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void call() {
        EventHandler.getInstance().callEvent(this);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                '}';
    }
}
