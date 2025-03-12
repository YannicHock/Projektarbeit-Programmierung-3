package de.prog3.projektarbeit.eventHandling.listeners.ui;

import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.views.View;

public abstract class WindowCloseEventListener extends EventListener<WindowCloseEvent> {
    private View view;
    protected WindowCloseEventListener(View view) {
        super(WindowCloseEvent.class);
        this.view = view;
    }


    @Override
    public abstract void onEvent(WindowCloseEvent event);
}
