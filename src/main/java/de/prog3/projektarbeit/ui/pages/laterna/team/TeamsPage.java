package de.prog3.projektarbeit.ui.pages.laterna.team;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.database.query.LeagueQuery;
import de.prog3.projektarbeit.data.database.query.TeamQuery;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerTransferFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.data.team.TeamCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerTransferFinishedListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.team.TeamCreationFinishedListener;
import de.prog3.projektarbeit.exceptions.TeamNotFoundExeption;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TeamsPage extends LaternaPage {

    private static final Logger logger = LoggerFactory.getLogger(TeamsPage.class);

    private final Window window;
    private final LaternaView view;
    private final String name;
    private final HashMap<Integer, Team> teams;
    private final Table<String> table;
    private final ArrayList<EventListener<? extends Event>> listeners;

    public TeamsPage(LaternaView view) {
        this.name = "Team übersicht";
        logger.info("Erstelle TeamsPage mit Namen: {}", name);
        this.teams = TeamQuery.getAll();
        logger.info("Es wurden {} Teams geladen.", teams.size());
        this.window = new BasicWindow(name);
        this.view = view;
        this.table = new Table<>("ID", "Teamname", "Spieleranzahl", "Liga");
        listeners = new ArrayList<>();
        registerListener();
        addClickAction();
        open();
        logger.info("TeamsPage wurde erfolgreich geöffnet.");
    }

    private void registerListener(){
        logger.info("Registriere TeamCreationFinishedListener für TeamsPage.");
        EventListener<TeamCreationFinishedEvent> teamCreationFinishedEventEventListener = new TeamCreationFinishedListener() {
            @Override
            public void onEvent(TeamCreationFinishedEvent event) {
                logger.info("TeamCreationFinishedEvent empfangen.");
                event.getTeam().ifPresent(team -> {
                    logger.info("Füge Team '{}' zur Tabelle hinzu.", team.getName());
                    table.getTableModel().addRow(team.getId() + "", team.getName(), team.getPlayers().size() + "");
                    teams.put(team.getId(), team);
                });
            }
        };
        
        EventListener <PlayerTransferFinishedEvent> playerTransferFinishedEventEventListener = new PlayerTransferFinishedListener() {
            @Override
            public void onEvent(PlayerTransferFinishedEvent event) {
                event.getPlayer().ifPresent(player -> {
                    logger.info("PlayerTransferFinishedEvent empfangen.");
                    teams.values().forEach(team -> {
                        if (team.getId() == player.getTeamId()) {
                            team.incrementPlayerCount();
                        }
                        if(event.getFromTeamId() == team.getId()){
                            team.decrementPlayerCount();
                        }
                    });
                    teams.values().forEach(team -> {
                        int id = getRowByFirstCellContent(table, team.getId() + "");
                        table.getTableModel().setCell(2, id, team.getPlayerCount_Int() + "");
                    });
                });
            }
        };


        listeners.add(playerTransferFinishedEventEventListener);

        listeners.add(teamCreationFinishedEventEventListener);
    }

    private void addClickAction(){
        logger.info("Füge Klickaktion zur Tabelle hinzu.");
        table.setSelectAction(() -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            int id = Integer.parseInt(data.getFirst());
            logger.info("Ausgewählte Zeile in der Tabelle: ID = {}", id);
            try {
                Team team = TeamQuery.getTeamById(id);
                new OpenPageEvent(view, PageType.TEAM, team).call();
                logger.info("OpenPageEvent erfolgreich für Team mit ID {} ausgelöst.", id);
            } catch (TeamNotFoundExeption e) {
                logger.error("TeamNotFoundExeption: Team mit ID {} konnte nicht gefunden werden.", id);
                MessageDialog.showMessageDialog(window.getTextGUI(), "Fehler beim Aktualisieren des Spielers", "");
            }
        });
    }

    @Override
    protected Component get() {
        logger.debug("Erstelle UI-Komponenten für TeamsPage.");
        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainpanel = new Panel(new GridLayout(2));
        GridLayout gridLayout = (GridLayout) mainpanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        mainpanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(1)));

        for (Team team : teams.values()) {
            table.getTableModel().addRow(team.getId() + "", team.getName(), team.getPlayerCount() + "", LeagueQuery.getNameById(team.getLeagueId()));
        }

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


    public static int getRowByFirstCellContent(Table<String> table, String content) {
        for (int row = 0; row < table.getTableModel().getRowCount(); row++) {
            if (table.getTableModel().getCell(0, row).equals(content)) {
                return row;
            }
        }
        return -1; // Content not found
    }
}
