package de.prog3.projektarbeit.ui.pages.laterna.tournament;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.objects.Match;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.data.objects.Tournament;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerCreationFinishedListener;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;
import de.prog3.projektarbeit.utils.Formatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Die TournamentPage-Klasse repräsentiert eine Seite, die ein Turnier in der Laterna-Ansicht anzeigt.
 */
public class TournamentPage extends LaternaPage {
    private final Window window;
    private final LaternaView view;
    private final Tournament tournament;
    private final ArrayList<EventListener<? extends Event>> listeners;

    /**
     * Konstruktor der TournamentPage-Klasse.
     * Initialisiert das Fenster, die Ansicht und das Turnier und öffnet die Seite.
     *
     * @param view Die LaternaView-Instanz, die diese Seite enthält.
     */
    public TournamentPage(LaternaView view) {
        this.window = new BasicWindow("Spieler erstellen");
        ArrayList<Team> teams = new ArrayList<>();
        teams.add(new Team("Team A"));
        teams.add(new Team("Team B"));
        teams.add(new Team("Team C"));
        teams.add(new Team("Team D"));
        teams.add(new Team("Team E"));
        teams.add(new Team("Team F"));
        this.tournament = new Tournament("Testteam", teams);
        this.view = view;
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

    /**
     * Registriert die Event-Listener für die Seite.
     */
    private void registerListener(){
    }

    /**
     * Erstellt und gibt die Hauptkomponente der Seite zurück.
     *
     * @return Die Hauptkomponente der Seite.
     */
    @Override
    public Component get() {

        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainPanel = new Panel(new GridLayout(2));

        // Vorrunde
        Table<String> firstLegtable = new Table<>("HomeTeam", "AwayTeam", "Date");
        List<Match> matches = tournament.getMatches();
        for (int i = 0; i < (matches.size())/2; i++) {
            Match match = matches.get(i);
            firstLegtable.getTableModel().addRow(
                    match.getHomeTeam().getName(),
                    match.getAwayTeam().getName(),
                    Formatter.parseDateToString(match.getDate())
            );
        }

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Panel leftPanel = new Panel();
        leftPanel.addComponent(firstLegtable);
        panel.addComponent(leftPanel.withBorder(Borders.singleLine("Vorrunde")));

        // Rückrunde
        Table<String> secondLegtable = new Table<>("HomeTeam", "AwayTeam", "Date");
        System.out.println("Teams: " + tournament.getMatches().size());
        for (int i = (matches.size()) / 2; i < matches.size(); i++) {
            Match match = matches.get(i);
            secondLegtable.getTableModel().addRow(
                    match.getHomeTeam().getName(),
                    match.getAwayTeam().getName(),
                    Formatter.parseDateToString(match.getDate())
            );
        }

        Panel rightPanel = new Panel();
        rightPanel.addComponent(secondLegtable);
        panel.addComponent(rightPanel.withBorder(Borders.singleLine("Rückrunde")));

        window.setComponent(panel.withBorder(Borders.singleLine("Spielplan")));

        contentPanel.addComponent(panel);
        contentPanel.addComponent(footer(false));

        return contentPanel;
    }

    /**
     * Gibt das Fenster der Seite zurück.
     *
     * @return Das Fenster der Seite.
     */
    @Override
    public Window getWindow() {
        return window;
    }

    /**
     * Gibt die LaternaView-Instanz der Seite zurück.
     *
     * @return Die LaternaView-Instanz.
     */
    @Override
    public LaternaView getLaternaView() {
        return view;
    }

    /**
     * Gibt den Namen der Seite zurück.
     *
     * @return Der Name der Seite.
     */
    @Override
    public String getName() {
        return "Spieler erstellen";
    }

    /**
     * Gibt die Liste der Event-Listener zurück.
     *
     * @return Eine Liste von Event-Listenern.
     */
    @Override
    public ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }
}