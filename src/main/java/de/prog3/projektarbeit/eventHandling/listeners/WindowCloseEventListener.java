package de.prog3.projektarbeit.eventHandling.listeners;

import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;

public abstract class WindowCloseEventListener extends EventListener<WindowCloseEvent> {
    protected WindowCloseEventListener() {
        super(WindowCloseEvent.class);
        System.out.println(this + " registered");
    }


    @Override
    public abstract void onEvent(WindowCloseEvent event);
}
