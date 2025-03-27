package de.prog3.projektarbeit.eventHandling.events;

import de.prog3.projektarbeit.eventHandling.EventHandler;

/**
 * Basisklasse für Events, die vom {@link EventHandler} verarbeitet werden können.
 * Ein Event besitzt einen Namen und kann über die Methode {@link #call()} ausgelöst werden.
 * Da diese Klasse abstrakt ist, können nur abgeleitete Klassen instanziiert werden.
 */
public abstract class Event {

    /**
     * Der Name des Events, z. B. "PlayerCreationEvent".
     */
    private final String name;

    /**
     * Erstellt ein neues Event mit dem angegebenen Namen.
     *
     * @param name Eindeutige Bezeichnung für das Event.
     */
    public Event(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen des Events zurück.
     *
     * @return Name des Events.
     */
    public String getName() {
        return name;
    }

    /**
     * Löst das Event aus, sodass es vom {@link EventHandler} verarbeitet werden kann.
     */
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