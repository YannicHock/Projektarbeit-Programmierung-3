package de.prog3.projektarbeit.eventHandling.events.ui;

import de.prog3.projektarbeit.eventHandling.EventHandler;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.ui.views.ViewType;

public class RequestNewViewEvent extends Event {
    private final ViewType viewType;

    public RequestNewViewEvent(ViewType viewType) {
        super("RequestNewViewEvent");
        this.viewType = viewType;
    }

    public ViewType getViewType() {
        return viewType;
    }

    @Override
    public void call() {
        new Thread(() -> EventHandler.getInstance().callEvent(this)).start();
    }
}
