package de.prog3.projektarbeit.eventHandling.events.ui;

import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.View;

public class OpenPageEvent extends Event {

    private final View view;
    private final PageType pageType;
    private final Object[] args;

    public OpenPageEvent(View view, PageType pageType, Object... args) {
        super("OpenPageEvent");
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
}
