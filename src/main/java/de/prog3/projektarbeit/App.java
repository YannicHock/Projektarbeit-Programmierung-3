package de.prog3.projektarbeit;

import de.prog3.projektarbeit.data.BlockHandler;
import de.prog3.projektarbeit.data.DataListener;
import de.prog3.projektarbeit.data.JooqContextProvider;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.ui.UIHandler;
import de.prog3.projektarbeit.ui.views.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//-DconsoleLogLevel=debug VM Option for debugging
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private void prepareListeners() {
        logger.info("Registriere Daten-Listener");
        DataListener.register();
        logger.info("Registriere UI-Listener");
        UIHandler.getInstance().registerListeners();
        logger.info("Initialisiere BlockHandler");
        BlockHandler.getInstance();
    }

    public void start() {
        logger.info("Initialisiere JooqContextProvider");
        JooqContextProvider.init();
        prepareListeners();
        logger.info("Fordere neue Ansicht an: LATERNA");
        new RequestNewViewEvent(ViewType.LATERNA).call();
    }

    public static void main(String[] args) {
        logger.info("Starte Anwendung");
        App app = new App();
        app.start();
        logger.info("Anwendung erfolgreich gestartet");
    }
}