package de.prog3.projektarbeit.eventHandling.listeners.ui;

import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;

public abstract class WindowCloseEventListener extends EventListener<WindowCloseEvent> {
    protected WindowCloseEventListener() {
        super(WindowCloseEvent.class);
    }


    @Override
    public abstract void onEvent(WindowCloseEvent event);
}
