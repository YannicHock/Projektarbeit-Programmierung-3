package de.prog3.projektarbeit.eventHandling.listeners;

/**
 * Dieses Enum definiert die Prioritätsstufen für Event-Listener.
 * Listener mit höherer Priorität werden vor Listenern mit niedrigerer Priorität verarbeitet.
 */
public enum Priority {
    /**
     * Höchste Priorität. Listener mit dieser Priorität werden als erste ausgeführt.
     */
    HIGHEST,
    /**
     * Hohe Priorität.
     */
    HIGH,
    /**
     * Normale Priorität.
     */
    NORMAL,
    /**
     * Niedrige Priorität.
     */
    LOW,
    /**
     * Niedrigste Priorität. Listener mit dieser Priorität werden zuletzt ausgeführt.
     */
    LOWEST
}