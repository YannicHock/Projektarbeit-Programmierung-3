package de.prog3.projektarbeit.ui.views.laterna;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.ui.OpenPageEventListener;
import de.prog3.projektarbeit.eventHandling.listeners.ui.WindowCloseEventListener;
import de.prog3.projektarbeit.ui.views.View;
import de.prog3.projektarbeit.ui.views.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class LaternaView implements View {
    private static final Logger logger = LoggerFactory.getLogger(LaternaView.class);
    private WindowBasedTextGUI gui;
    private final String name;
    EventListener<OpenPageEvent> openPageEventListener;
    EventListener<WindowCloseEvent> closeEventEventListener;

    public LaternaView(){
        this.name = "Laterna " + this.hashCode();
        registerListeners(this);
        create();
    }

    private void registerListeners(View view){
        this.openPageEventListener = new OpenPageEventListener(){
            @Override
            public void onEvent(OpenPageEvent event) {
                if(event.getView().equals(view)) {
                    if(!event.isCancelled()){
                        event.getPageType().openPage(event.getView(), event.getArgs());
                    } else {
                        MessageDialog.showMessageDialog(gui, "Warnung", "Der Zugriff auf die Seite wurde verweigert, weil diese Seite derzeit in einem anderen Fenstern verwendet wird.");
                    }
                }
            }
        };

        this.closeEventEventListener =  new WindowCloseEventListener() {
            @Override
            public void onEvent(WindowCloseEvent event) {
                if(view.equals(event.getView())) {
                    if(event.getView() instanceof LaternaView view){
                        if(view.getGui().getWindows().isEmpty()){
                            try {
                                this.unregister();
                                view.getGui().getScreen().stopScreen();
                            } catch (IOException e) {
                                logger.error("Fehler beim Stoppen des Bildschirms: ", e);
                            }
                        }
                    }
                }
            }
        };
    }

    public String getName() {
        return name;
    }

    public void registerWindow(Window window){
        window.setHints(List.of(Window.Hint.CENTERED));
        gui.addWindow(window);
    }

    public WindowBasedTextGUI getGui() {
        return gui;
    }

    private void create(){
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        try {
            Terminal terminal = terminalFactory.createTerminalEmulator();
            Screen screen = new TerminalScreen(terminal);
            this.gui = new MultiWindowTextGUI(screen);
            screen.startScreen();
        } catch (IOException e) {
            logger.error("Fehler beim Erstellen des Bildschirms: ", e);
        }
    }

    @Override
    public ViewType getType() {
        return ViewType.LATERNA;
    }
}