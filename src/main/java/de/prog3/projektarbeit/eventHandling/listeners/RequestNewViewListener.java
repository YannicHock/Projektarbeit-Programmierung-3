package de.prog3.projektarbeit.eventHandling.listeners;

import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;

public abstract class RequestNewViewListener extends EventListener<RequestNewViewEvent> {
    protected RequestNewViewListener() {
        super(RequestNewViewEvent.class);
        System.out.println(this + " registered");
    }

    @Override
    public abstract void onEvent(RequestNewViewEvent event);
}