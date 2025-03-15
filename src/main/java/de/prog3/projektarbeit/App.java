package de.prog3.projektarbeit;


import de.prog3.projektarbeit.data.BlockHandler;
import de.prog3.projektarbeit.data.DataListener;
import de.prog3.projektarbeit.data.JooqContextProvider;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.ui.UIHandler;
import de.prog3.projektarbeit.ui.views.ViewType;

public class App {

    private void prepareListeners() {
        DataListener.register();
        UIHandler.getInstance().registerListeners();
        BlockHandler.getInstance();
    }

    public void start() {
        JooqContextProvider.init();
        prepareListeners();
        new RequestNewViewEvent(ViewType.LATERNA).call();
    }


    public static void main(String[] args) {
        App app = new App();
        app.start();
    }

}
