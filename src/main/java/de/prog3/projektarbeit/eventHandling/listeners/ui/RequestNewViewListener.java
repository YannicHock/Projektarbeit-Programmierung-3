package de.prog3.projektarbeit.eventHandling.listeners.ui;

import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class RequestNewViewListener extends EventListener<RequestNewViewEvent> {
    protected RequestNewViewListener() {
        super(RequestNewViewEvent.class);
    }

    @Override
    public abstract void onEvent(RequestNewViewEvent event);
}