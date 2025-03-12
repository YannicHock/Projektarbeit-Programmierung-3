package de.prog3.projektarbeit.eventHandling.listeners.ui;

import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.views.View;

public abstract class OpenPageEventListener extends EventListener<OpenPageEvent> {
    private View view;

    protected OpenPageEventListener(View view) {
        super(OpenPageEvent.class);
        this.view = view;
    }


    @Override
    public abstract void onEvent(OpenPageEvent event);
}
