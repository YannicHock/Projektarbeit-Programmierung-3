package de.prog3.projektarbeit.ui.pages.laterna.team;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import de.prog3.projektarbeit.data.database.query.LeagueQuery;
import de.prog3.projektarbeit.data.objects.League;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.team.AttemptTeamCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.team.TeamCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.team.TeamCreationFinishedListener;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stellt eine Seite zum Erstellen eines neuen Teams bereit. Sie enthält ein Textfeld
 * für den Teamnamen und einen Button, der ein {@link AttemptTeamCreationEvent} auslöst.
 * <p>
 * Beim erfolgreichen Anlegen des Teams oder bei Fehlern reagiert ein Listener
 * auf das {@link TeamCreationFinishedEvent} und informiert den Nutzer entsprechend.
 */
public class CreateTeamPage extends LaternaPage {
    private final Window window;
    private final LaternaView view;
    private final ArrayList<EventListener<? extends Event>> listeners;
    private final String name;
    private final HashMap<Integer, Integer> indexMap;

    public CreateTeamPage(LaternaView view) {
        this.name = "Team erstellen";
        this.window = new BasicWindow(name);
        this.view = view;
        listeners = new ArrayList<>();
        indexMap = new HashMap<>();
        registerListener();
        open();
    }

    /**
     * Registriert einen Listener, der auf das {@link TeamCreationFinishedEvent} reagiert.
     * Bei Erfolg wird das Fenster geschlossen, bei Fehlern wird eine Dialogbox mit Fehlermeldungen angezeigt.
     */
    private void registerListener() {
        EventListener<TeamCreationFinishedEvent> creationFinishedListener = new TeamCreationFinishedListener() {
            @Override
            public void onEvent(TeamCreationFinishedEvent event) {
                event.getTeam().ifPresent(team -> window.close());
                event.getExceptions().ifPresent(exceptions -> {
                    StringBuilder builder = new StringBuilder();
                    exceptions.forEach(exception -> {
                        builder.append(exception.getMessage());
                        builder.append("\n");
                    });
                    MessageDialog.showMessageDialog(window.getTextGUI(), "Fehler beim erstellen des Teams", builder.toString());
                });
            }
        };
        listeners.add(creationFinishedListener);
    }


    @Override
    public Component get() {

        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainPanel = new Panel(new GridLayout(2));

        TerminalSize size = new TerminalSize(30, 1);
        Label teamName = new Label("Teamname");
        mainPanel.addComponent(teamName);
        TextBox teamNameTextBox = new TextBox().setPreferredSize(size);
        mainPanel.addComponent(teamNameTextBox);


        Label leagueLable = new Label("Ligazugehörigkeit: ");
        mainPanel.addComponent(leagueLable);
        ComboBox<String> leagueComboBox = new ComboBox<>();
        int index = 0;
        for(League league : LeagueQuery.getAll()){
            indexMap.put(index, league.getId());
            index++;
            leagueComboBox.addItem(league.getName());
        }
        mainPanel.addComponent(leagueComboBox);

        contentPanel.addComponent(mainPanel);

        contentPanel.addComponent(new Button("Fertig", () -> {
            String teamNameString = teamNameTextBox.getText();
            int id = indexMap.get(leagueComboBox.getSelectedIndex());

            new AttemptTeamCreationEvent(teamNameString, id).call();

        }));
        contentPanel.addComponent(footer(false));

        return contentPanel;
    }

    /**
     * Gibt das Hauptfenster dieser Seite zurück.
     *
     * @return Das {@link Window}-Objekt dieser Seite.
     */
    @Override
    public Window getWindow() {
        return window;
    }

    /**
     * Gibt die übergeordnete View zurück, in der diese Seite angezeigt wird.
     *
     * @return Die aktuelle {@link LaternaView}.
     */
    @Override
    public LaternaView getLaternaView() {
        return view;
    }

    /**
     * Gibt den Namen dieser Seite zurück, z. B. für Fenstertitel oder Debug-Ausgaben.
     *
     * @return Der Name dieser Seite.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gibt alle registrierten EventListener dieser Seite zurück.
     * Hier wird vor allem ein {@link TeamCreationFinishedListener} genutzt.
     *
     * @return Eine Liste mit den EventListenern.
     */
    @Override
    public ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }
}
