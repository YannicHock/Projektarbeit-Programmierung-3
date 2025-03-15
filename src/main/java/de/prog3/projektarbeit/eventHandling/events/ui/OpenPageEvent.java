package de.prog3.projektarbeit.eventHandling.events.ui;

import de.prog3.projektarbeit.eventHandling.events.Cancellable;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.View;

public class OpenPageEvent extends Event implements Cancellable {

    private final View view;
    private final PageType pageType;
    private final Object[] args;
    private boolean cancelled;

    public OpenPageEvent(View view, PageType pageType, Object... args) {
        super("OpenPageEvent");
        cancelled = false;
        this.view = view;
        this.pageType = pageType;
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

    public View getView() {
        return view;
    }

    public PageType getPageType() {
        return pageType;
    }

    @Override
    public String toString() {
        return super.toString() + " " + pageType;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
