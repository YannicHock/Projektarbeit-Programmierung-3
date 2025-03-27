package de.prog3.projektarbeit.eventHandling.listeners.data.player;

import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerCreationEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

/**
 * Listener zur Verarbeitung von Spielererstellungsversuchen. Diese abstrakte Klasse erweitert den EventListener
 * und ist speziell dafür ausgelegt, auf AttemptPlayerCreationEvent zu hören. Sie stellt einen Konstruktor bereit,
 * um den Ereignistyp zu registrieren und erfordert die Implementierung der onEvent-Methode zur Verarbeitung des Ereignisses.
 */
public abstract class AttemptPlayerCreationListener extends EventListener<AttemptPlayerCreationEvent> {
    protected AttemptPlayerCreationListener() {
        super(AttemptPlayerCreationEvent.class);
    }


    @Override
    public abstract void onEvent(AttemptPlayerCreationEvent event);
}