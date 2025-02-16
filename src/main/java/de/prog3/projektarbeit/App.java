package de.prog3.projektarbeit;


import de.prog3.projektarbeit.data.InsertPlayer;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.ui.UIHandler;
import de.prog3.projektarbeit.ui.views.ViewType;

public class App {

    private void prepareListeners() {
        UIHandler.getInstance().registerListeners();
    }

    public void start() {
        prepareListeners();
        new RequestNewViewEvent(ViewType.LATERNA).call();
    }


    public static void main(String[] args) {
        Player player = new Player("A", 12, "pos", 12);
      InsertPlayer.insertPlayer();
        App app = new App();
        app.start();
    }


    public int magicNumber() {
        return 42;
    }

    public boolean allWaysFalse(){
        return false;
    }
}
