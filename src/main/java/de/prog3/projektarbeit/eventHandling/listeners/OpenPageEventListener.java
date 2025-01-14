package de.prog3.projektarbeit.eventHandling.listeners;

import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;

public abstract class OpenPageEventListener extends EventListener<OpenPageEvent> {
    protected OpenPageEventListener() {
        super(OpenPageEvent.class);
        System.out.println(this + " registered");
    }


    @Override
    public abstract void onEvent(OpenPageEvent event);
}
