package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.gui2.*;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.ViewType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;
import java.util.UUID;

public class TitlePage extends LaternaPage {

    private final Window window;
    private final LaternaView view;
    private final ArrayList<EventListener<? extends Event>> listeners;

    public TitlePage(LaternaView view){
        this.window = new BasicWindow("Hauptmenu");
        this.view = view;
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

    private void registerListener(){
    }

    @Override
    protected Component get(){
        Panel contentPanel = new Panel(new GridLayout(2));

        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);


        contentPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        contentPanel.addComponent(new Button("Spieler erstellen", () -> new OpenPageEvent(view, PageType.CREATE_PLAYER).call()));
        contentPanel.addComponent(new Button("Team erstellen", () -> new OpenPageEvent(view, PageType.CREATE_TEAM).call()));
        contentPanel.addComponent(new Button("Teams", () -> new OpenPageEvent(view, PageType.TEAMS).call()));
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

    @Override
    protected ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }

    public void open() {
        this.view.getGui().addWindow(window);
        window.setComponent(get());
        window.waitUntilClosed();
    }

    @Override
    public Window getWindow() {
        return window;
    }

    @Override
    protected LaternaView getLaternaView() {
        return view;
    }

    @Override
    public void close() {
        window.close();
        new WindowCloseEvent(this.view).call();
    }

    @Override
    public String getName() {
        return "Hauptmenu";
    }
}
