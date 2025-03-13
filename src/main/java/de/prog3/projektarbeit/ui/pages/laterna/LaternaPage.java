package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.gui2.*;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.pages.Page;
import de.prog3.projektarbeit.ui.views.ViewType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;

public abstract class LaternaPage implements Page {
    protected abstract Window getWindow();
    protected abstract LaternaView getLaternaView();
    protected abstract Component get();
    protected abstract ArrayList<EventListener<? extends Event>> getListeners();

    public void open() {
        getLaternaView().registerWindow(getWindow());
        getWindow().setComponent(get());
        getWindow().waitUntilClosed();
    }


    public Component footer(boolean newPage){
        return footer(newPage, true);
    }

    public Component footer(boolean newPage, boolean topPadding){
        Panel footerPanel = new Panel(new GridLayout(2)).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2));
        if(topPadding){
            footerPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        }
        footerPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        if(newPage){
            buttonPanel.addComponent(new Button("Neues Fenster", () -> new RequestNewViewEvent(ViewType.LATERNA).call()));
        }
        buttonPanel.addComponent(new Button("Schließen", this::close));
        footerPanel.addComponent(buttonPanel.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(2)));
        return footerPanel;
    }

    @Override
    public void close() {
        getWindow().close();
        getListeners().forEach(EventListener::unregister);
        new WindowCloseEvent(getLaternaView()).call();
    }
}
