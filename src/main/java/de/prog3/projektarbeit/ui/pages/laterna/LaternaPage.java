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

/**
 * Abstrakte Klasse LaternaPage, die eine Seite in der Laterna-Ansicht repräsentiert.
 */
public abstract class LaternaPage implements Page {

    /**
     * Gibt das Fenster der Seite zurück.
     *
     * @return Das Fenster der Seite.
     */
    protected abstract Window getWindow();

    /**
     * Gibt die LaternaView-Instanz der Seite zurück.
     *
     * @return Die LaternaView-Instanz.
     */
    protected abstract LaternaView getLaternaView();

    /**
     * Erstellt und gibt die Hauptkomponente der Seite zurück.
     *
     * @return Die Hauptkomponente der Seite.
     */
    protected abstract Component get();

    /**
     * Gibt die Liste der Event-Listener zurück.
     *
     * @return Eine Liste von Event-Listenern.
     */
    protected abstract ArrayList<EventListener<? extends Event>> getListeners();

    /**
     * Öffnet die Seite, registriert das Fenster und setzt die Hauptkomponente.
     */
    public void open() {
        getLaternaView().registerWindow(getWindow());
        getWindow().setComponent(get());
        getWindow().waitUntilClosed();
    }

    /**
     * Erstellt und gibt die Fußzeilenkomponente der Seite zurück.
     *
     * @param newPage Gibt an, ob ein Button für ein neues Fenster angezeigt werden soll.
     * @return Die Fußzeilenkomponente der Seite.
     */
    public Component footer(boolean newPage) {
        return footer(newPage, true);
    }

    /**
     * Erstellt und gibt die Fußzeilenkomponente der Seite zurück.
     *
     * @param newPage Gibt an, ob ein Button für ein neues Fenster angezeigt werden soll.
     * @param topPadding Gibt an, ob ein oberer Abstand hinzugefügt werden soll.
     * @return Die Fußzeilenkomponente der Seite.
     */
    public Component footer(boolean newPage, boolean topPadding) {
        Panel footerPanel = new Panel(new GridLayout(2)).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2));
        if (topPadding) {
            footerPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        }
        footerPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        if (newPage) {
            buttonPanel.addComponent(new Button("Neues Fenster", () -> new RequestNewViewEvent(ViewType.LATERNA).call()));
        }
        buttonPanel.addComponent(new Button("Schließen", this::close));
        footerPanel.addComponent(buttonPanel.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(2)));
        return footerPanel;
    }

    /**
     * Schließt die Seite, entfernt die Event-Listener und ruft das WindowCloseEvent auf.
     */
    @Override
    public void close() {
        getWindow().close();
        getListeners().forEach(EventListener::unregister);
        new WindowCloseEvent(getLaternaView()).call();
    }
}