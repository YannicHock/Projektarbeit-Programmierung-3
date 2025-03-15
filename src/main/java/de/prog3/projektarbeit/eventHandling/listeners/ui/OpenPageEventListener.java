package de.prog3.projektarbeit.eventHandling.listeners.ui;

import de.prog3.projektarbeit.eventHandling.listeners.Priority;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class OpenPageEventListener extends EventListener<OpenPageEvent> {

    protected OpenPageEventListener() {
        super(OpenPageEvent.class);
    }

    protected OpenPageEventListener(Priority priority) {
        super(OpenPageEvent.class, priority);
    }

    @Override
    public abstract void onEvent(OpenPageEvent event);
}
