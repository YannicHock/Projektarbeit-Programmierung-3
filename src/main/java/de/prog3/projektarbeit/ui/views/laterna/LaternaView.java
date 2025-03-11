package de.prog3.projektarbeit.ui.views.laterna;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import de.prog3.projektarbeit.ui.views.View;
import de.prog3.projektarbeit.ui.views.ViewType;

import java.io.IOException;


public class LaternaView implements View {

    private WindowBasedTextGUI gui;

    public LaternaView(){
        create();
        Listener.registerListeners();
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ViewType getType() {
        return ViewType.LATERNA;
    }
}
