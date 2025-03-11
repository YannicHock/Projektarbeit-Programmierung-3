package de.prog3.projektarbeit;


import de.prog3.projektarbeit.data.DataListener;
import de.prog3.projektarbeit.data.DataSourceProvider;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.ui.UIHandler;
import de.prog3.projektarbeit.ui.views.ViewType;

public class App {

    private void prepareListeners() {
        DataListener.register();
        UIHandler.getInstance().registerListeners();
    }

    public void start() {
        DataSourceProvider.init();
        prepareListeners();
        new RequestNewViewEvent(ViewType.LATERNA).call();
    }


    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

}
