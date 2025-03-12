package de.prog3.projektarbeit.ui.pages.laterna.team;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.factories.TeamFactory;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamsPage extends LaternaPage {

    private final Window window;
    private final LaternaView view;
    private final String name;
    private final HashMap<Integer, Team> teams;
    private final Table<String> table;
    private final ArrayList<EventListener<? extends Event>> listeners;

    public TeamsPage(LaternaView view) {
        this.name = "Team übersicht";
        this.teams = TeamFactory.getAll();
        this.window = new BasicWindow(name);
        this.view = view;
        this.table = new Table<>("ID", "Teamname", "Spieleranzahl");
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

    private void registerListener(){
    }


    @Override
    protected Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainpanel = new Panel(new GridLayout(2));
        GridLayout gridLayout = (GridLayout) mainpanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        mainpanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(1)));

        for (Team team : teams.values()) {
            table.getTableModel().addRow(team.getId() + "", team.getName(), team.getPlayers().size() + "");
        }

        table.setSelectAction(() -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            Integer key = Integer.parseInt(data.getFirst());
            new OpenPageEvent(view, PageType.TEAM, teams.get(key)).call();
        });

        mainpanel.addComponent(table);

        contentPanel.addComponent(mainpanel);


        contentPanel.addComponent(footer(false));
        return contentPanel;
    }

    @Override
    protected Window getWindow() {
        return window;
    }

    @Override
    protected LaternaView getLaternaView() {
        return view;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }
}
