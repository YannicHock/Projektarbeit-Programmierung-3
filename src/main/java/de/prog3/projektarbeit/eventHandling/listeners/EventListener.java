package de.prog3.projektarbeit.eventHandling.listeners;

import de.prog3.projektarbeit.eventHandling.EventHandler;
import de.prog3.projektarbeit.eventHandling.events.Event;

/**
 * Abstrakte Basisklasse für Listener, die auf bestimmte Events reagieren.
 *
 * @param <T> Typ des Events, auf das dieser Listener reagiert.
 */
public abstract class EventListener<T extends Event> {

    /**
     * Die Klasse des Event-Typs, auf den dieser Listener reagiert.
     */
    private final Class<T> eventType;

    /**
     * Die Priorität des Listeners. Listener mit höherer Priorität
     * werden früher aufgerufen als Listener mit niedrigerer Priorität.
     */
    private final Priority priority;

    /**
     * Erstellt einen neuen Listener mit normaler Priorität
     * und registriert ihn automatisch beim {@link EventHandler}.
     *
     * @param eventType Die Klasse des Event-Typs, den dieser Listener verarbeiten soll.
     */
    protected EventListener(Class<T> eventType) {
        this.eventType = eventType;
        this.priority = Priority.NORMAL;
        register();
    }

    /**
     * Erstellt einen neuen Listener mit angegebener Priorität
     * und registriert ihn automatisch beim {@link EventHandler}.
     *
     * @param eventType Die Klasse des Event-Typs, den dieser Listener verarbeiten soll.
     * @param priority  Die gewünschte Priorität für diesen Listener.
     */
    protected EventListener(Class<T> eventType, Priority priority) {
        this.eventType = eventType;
        this.priority = priority;
        register();
    }

    /**
     * Registriert diesen Listener beim {@link EventHandler}, sodass er auf Events
     * des angegebenen Typs reagieren kann.
     */
    private void register() {
        EventHandler.getInstance().registerListener(eventType,  this);
    }

    /**
     * Meldet diesen Listener vom {@link EventHandler} ab, sodass er keine
     * weiteren Events mehr empfängt.
     */
    public void unregister() {
        EventHandler.getInstance().unregisterListener(eventType, this);
    }

    /**
     * Gibt die Priorität dieses Listeners als numerischen Wert zurück.
     *
     * @return Die Ordinalzahl der Priorität.
     */
    public int getPriority() {
        return priority.ordinal();
    }

    /**
     * Verarbeitet ein Event des Typs T. Diese Methode wird aufgerufen,
     * sobald ein passendes Event eintrifft.
     *
     * @param event Das eingetroffene Event.
     */
    public abstract void onEvent(T event);

    @Override
    public String toString() {
        return "EventListener{" +
                "eventType=" + eventType +
                '}';
    }
}