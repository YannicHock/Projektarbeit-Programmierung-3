package de.prog3.projektarbeit.ui.views.laterna;

import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.eventHandling.listeners.OpenPageEventListener;
import de.prog3.projektarbeit.eventHandling.listeners.WindowCloseEventListener;

import java.io.IOException;

public class Listener {

    public static void registerListeners() {

        new OpenPageEventListener(){
            @Override
            public void onEvent(OpenPageEvent event) {
                event.getPageType().openPage(event.getView(), event.getArgs());
            }
        };

        new WindowCloseEventListener() {
            @Override
            public void onEvent(WindowCloseEvent event) {
                if(event.getView() instanceof LaternaView view){
                    if(view.getTextGUI().getWindows().isEmpty()){
                        try {
                            view.getTextGUI().getScreen().stopScreen();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };
    }
}
