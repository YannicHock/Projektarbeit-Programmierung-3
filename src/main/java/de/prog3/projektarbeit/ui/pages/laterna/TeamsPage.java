package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.TeamGenerator;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamsPage implements LaternaPage {

    private final Window window;
    private final LaternaView view;
    private final String name;
    private final ArrayList<Team> teams;
    private final Table<String> table;
    private final ArrayList<EventListener<? extends Event>> listeners;

    public TeamsPage(LaternaView view) {
        this.name = "Team übersicht";
        this.teams = TeamGenerator.generateRandomTeams(5);
        this.window = new BasicWindow(name);
        this.view = view;
        this.table = new Table<>("ID", "Teamname", "Spieleranzahl");
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

    private void registerListener(){
    }

    private void open() {
        window.setHints(List.of(Window.Hint.CENTERED));
        view.getGui().addWindow(window);
        getWindow().setComponent(get());
        getWindow().waitUntilClosed();
    }

    private Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainpanel = new Panel(new GridLayout(2));
        GridLayout gridLayout = (GridLayout) mainpanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        mainpanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(1)));

        for (Team team : teams) {
            table.getTableModel().addRow(team.getId() + "", team.getName(), team.getPlayers().size() + "");
        }

        table.setSelectAction(() -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            Integer key = Integer.parseInt(data.getFirst());
            new OpenPageEvent(view, PageType.TEAM, teams.get(key)).call();
        });

        mainpanel.addComponent(table);

        contentPanel.addComponent(mainpanel);

        Panel footerPanel = new Panel(new GridLayout(2)).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2));
        footerPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        footerPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        buttonPanel.addComponent(new Button("Schließen", this::close));
        footerPanel.addComponent(buttonPanel.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(2)));

        contentPanel.addComponent(footerPanel);

        return contentPanel;
    }

    @Override
    public void close() {
        getWindow().close();
        new WindowCloseEvent(view).call();
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Window getWindow() {
        return window;
    }
}
