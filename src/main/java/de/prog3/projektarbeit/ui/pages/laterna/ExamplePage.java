package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.gui2.*;
import de.prog3.projektarbeit.Player;
import de.prog3.projektarbeit.data.PlayerFactory;
import de.prog3.projektarbeit.data.TeamFactory;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.View;
import de.prog3.projektarbeit.ui.views.ViewType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.UUID;

public class ExamplePage implements LaternaPage{

    private String test;
    private String name;
    private final Window window;
    private final LaternaView view;

    public ExamplePage(LaternaView view, String test) {
        this.name = "test  - " + test;
        this.test = test;
        this.window = new BasicWindow();
        this.view = view;
        open();
    }

    private Component get(){
        Panel contentPanel = new Panel(new GridLayout(2));

        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);


        contentPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        contentPanel.addComponent(new Label(test));
        contentPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        buttonPanel.addComponent(new Button("Neues Fenster", () -> new RequestNewViewEvent(ViewType.LATERNA).call()));
        buttonPanel.addComponent(new Button("Schließen", this::close));
        contentPanel.addComponent(buttonPanel.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(2)));

        return contentPanel;
    }

    public void open() {
        this.view.getTextGUI().addWindow(window);
        window.setComponent(get());
        window.waitUntilClosed();
    }

    @Override
    public Window getWindow() {
        return window;
    }

    @Override
    public void close() {
        window.close();
        new WindowCloseEvent(this.view).call();
    }

    @Override
    public String getName() {
        return "";
    }
}
