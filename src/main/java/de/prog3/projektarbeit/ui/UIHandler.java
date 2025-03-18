package de.prog3.projektarbeit.ui;

import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.eventHandling.listeners.ui.RequestNewViewListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;
import de.prog3.projektarbeit.ui.views.View;
import de.prog3.projektarbeit.ui.views.ViewType;

import java.util.ArrayList;

public class UIHandler {

    private final ArrayList<View> views;

    private UIHandler() {
        views = new ArrayList<>();
    }

    private static UIHandler instance;

    public void registerListeners(){
        registerViewListener();
    }

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

    public ArrayList<View> getViews() {
        return views;
    }

    public static UIHandler getInstance() {
        if(instance == null) {
            instance = new UIHandler();
        }
        return instance;
    }

}
