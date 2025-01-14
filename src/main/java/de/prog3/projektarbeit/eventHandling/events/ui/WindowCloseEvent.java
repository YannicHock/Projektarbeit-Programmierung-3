package de.prog3.projektarbeit.eventHandling.events.ui;

import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.ui.views.View;

public class WindowCloseEvent extends Event {

    private final View view;

    public WindowCloseEvent(View view) {
        super("WindowCloseEvent");
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
