package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.gui2.*;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;

public class TitlePage extends LaternaPage {

    private final Window window;
    private final LaternaView view;

    public TitlePage(LaternaView view){
        this.window = new BasicWindow("Hauptmenu");
        this.view = view;
        open();
    }

    public Component get(){

        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));


        //Player Management
        Panel playerManagement = new Panel();
        playerManagement.addComponent(new Button("Spieler erstellen", () -> new OpenPageEvent(view, PageType.CREATE_PLAYER).call()));
        playerManagement.addComponent(new Button("Vereinslose Spieler anzeigen", () -> new OpenPageEvent(view, PageType.FREE_AGENTS).call()));
        mainPanel.addComponent(playerManagement.withBorder(Borders.singleLine("Spielerverwaltung")));


        //Team Management
        Panel teamManagement = new Panel();
        teamManagement.addComponent(new Button("Teamübersicht", () -> new OpenPageEvent(view, PageType.TEAMS).call()));
        teamManagement.addComponent(new Button("Team erstellen", () -> new OpenPageEvent(view, PageType.CREATE_TEAM).call()));
        mainPanel.addComponent(teamManagement.withBorder(Borders.singleLine("Teamverwaltung")));


        //Tournament Management
        Panel tournamentManagement = new Panel();
        mainPanel.addComponent(tournamentManagement.withBorder(Borders.singleLine("Turnierverwaltung")));
        tournamentManagement.addComponent(new Button("Turnierübersicht", () -> new OpenPageEvent(view, PageType.TOURNAMENTS).call()));
        contentPanel.addComponent(mainPanel);


        //Seitenende
        contentPanel.addComponent(footer(true));

        return contentPanel;
    }

    @Override
    protected ArrayList<EventListener<? extends Event>> getListeners() {
        return new ArrayList<>();
    }

    @Override
    public Window getWindow() {
        return window;
    }

    @Override
    public LaternaView getLaternaView() {
        return view;
    }

    @Override
    public String getName() {
        return "Hauptmenu";
    }
}
