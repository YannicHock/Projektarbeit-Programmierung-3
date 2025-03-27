package de.prog3.projektarbeit.ui;

import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.eventHandling.listeners.ui.RequestNewViewListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;
import de.prog3.projektarbeit.ui.views.View;
import de.prog3.projektarbeit.ui.views.ViewType;

import java.util.ArrayList;

/**
 * Die UIHandler-Klasse verwaltet die UI-Ansichten und deren Listener.
 */
public class UIHandler {

    private final ArrayList<View> views;

    /**
     * Privater Konstruktor, der die Liste der Ansichten initialisiert.
     */
    private UIHandler() {
        views = new ArrayList<>();
    }

    private static UIHandler instance;

    /**
     * Registriert alle notwendigen Listener.
     */
    public void registerListeners(){
        registerViewListener();
    }

    /**
     * Registriert den Listener für neue Ansichten.
     */
    private void registerViewListener(){
        new RequestNewViewListener() {
            @Override
            public void onEvent(RequestNewViewEvent event) {
                if(event.getViewType().equals(ViewType.LATERNA)){
                    View view = new LaternaView();
                    views.add(view);
                    new OpenPageEvent(view, PageType.MAIN).call();
                }
            }
        };
    }

    /**
     * Gibt die Liste der Ansichten zurück.
     *
     * @return Eine Liste der registrierten Ansichten.
     */
    public ArrayList<View> getViews() {
        return views;
    }

    /**
     * Gibt die Instanz des UIHandlers zurück. Erstellt eine neue Instanz, wenn keine existiert.
     *
     * @return Die Singleton-Instanz des UIHandlers.
     */
    public static UIHandler getInstance() {
        if(instance == null) {
            instance = new UIHandler();
        }
        return instance;
    }

}