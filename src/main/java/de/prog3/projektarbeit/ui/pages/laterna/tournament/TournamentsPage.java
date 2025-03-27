package de.prog3.projektarbeit.ui.pages.laterna.tournament;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.database.query.LeagueQuery;
import de.prog3.projektarbeit.data.database.query.TeamQuery;
import de.prog3.projektarbeit.data.database.query.TournamentQuery;
import de.prog3.projektarbeit.data.objects.Match;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.data.objects.Tournament;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerCreationFinishedListener;
import de.prog3.projektarbeit.exceptions.TeamNotFoundExeption;
import de.prog3.projektarbeit.exceptions.TournamentNotFoundException;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;
import de.prog3.projektarbeit.utils.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TournamentsPage extends LaternaPage {
    private final Window window;
    private final LaternaView view;
    private final ArrayList<Tournament> tournaments;
    private final ArrayList<EventListener<? extends Event>> listeners;
    private final Table<String> table;

    private static final Logger logger = LoggerFactory.getLogger(TournamentsPage.class);

    public TournamentsPage(LaternaView view) {
        this.window = new BasicWindow("Spielpläne");
        this.table = new Table<>("ID", "Turniername");
        this.tournaments = TournamentQuery.getAll();
        this.view = view;
        listeners = new ArrayList<>();
        registerListener();
        addClickAction();
        open();
    }

    private void registerListener(){
    }

    private void addClickAction(){
        logger.info("Füge Klickaktion zur Tabelle hinzu.");
        table.setSelectAction(() -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            int id = Integer.parseInt(data.getFirst());
            logger.info("Ausgewählte Zeile in der Tabelle: ID = {}", id);
            try {
                Tournament tournament = TournamentQuery.getById(id);
                new OpenPageEvent(view, PageType.TOURNAMENT, tournament).call();
                logger.info("OpenPageEvent erfolgreich für Team mit ID {} ausgelöst.", id);
            } catch (TournamentNotFoundException e) {
                logger.error("TournamentNotFoundException: Team mit ID {} konnte nicht gefunden werden.", id);
                MessageDialog.showMessageDialog(window.getTextGUI(), "Fehler beim Aktualisieren des Spielers", "");
            }
        });
    }


    @Override
    public Component get() {

        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainpanel = new Panel(new GridLayout(2));
        GridLayout gridLayout = (GridLayout) mainpanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        mainpanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(1)));

        for (Tournament tournament : tournaments) {
            table.getTableModel().addRow(tournament.getId() + "", tournament.getName());
        }

        mainpanel.addComponent(table);

        contentPanel.addComponent(mainpanel);
        contentPanel.addComponent(footer(false));

        return contentPanel;
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
        return "Spieler erstellen";
    }

    @Override
    public ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }
}
