package de.prog3.projektarbeit.ui.views.laterna;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import de.prog3.projektarbeit.ui.views.View;
import de.prog3.projektarbeit.ui.views.ViewType;

import java.io.IOException;


public class LaternaView implements View {

    private WindowBasedTextGUI textGUI;

    public LaternaView(){
        create();
        Listener.registerListeners();
    }

    public WindowBasedTextGUI getTextGUI() {
        return textGUI;
    }

    private void create(){
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Screen screen = null;
        try {
            screen = terminalFactory.createScreen();
            textGUI = new MultiWindowTextGUI(screen);
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
