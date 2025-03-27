package de.prog3.projektarbeit.ui.pages.laterna.team;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.team.AttemptTeamCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.team.TeamCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.team.TeamCreationFinishedListener;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;

/**
 * Stellt eine Seite zum Erstellen eines neuen Teams bereit. Sie enthält ein Textfeld
 * für den Teamnamen und einen Button, der ein {@link AttemptTeamCreationEvent} auslöst.
 *
 * Beim erfolgreichen Anlegen des Teams oder bei Fehlern reagiert ein Listener
 * auf das {@link TeamCreationFinishedEvent} und informiert den Nutzer entsprechend.
 */
public class CreateTeamPage extends LaternaPage {

    /**
     * Das Hauptfenster dieser Seite, in dem die Komponenten platziert werden.
     */
    private final Window window;

    /**
     * Referenz auf die übergeordnete LaternaView, um das Fenster verwalten zu können.
     */
    private final LaternaView view;

    /**
     * Liste aller EventListener, die diese Seite registriert.
     */
    private final ArrayList<EventListener<? extends Event>> listeners;

    /**
     * Der Anzeigename dieser Seite, z. B. im Fenstertitel.
     */
    private final String name;

    /**
     * Erstellt eine neue Seite zum Anlegen eines Teams, registriert alle benötigten Listener
     * und öffnet das Fenster.
     *
     * @param view Die aktuelle {@link LaternaView}, in der diese Seite angezeigt wird.
     */
    public CreateTeamPage(LaternaView view) {
        this.name = "Team erstellen";
        this.window = new BasicWindow(name);
        this.view = view;
        this.listeners = new ArrayList<>();
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
                    MessageDialog.showMessageDialog(
                            window.getTextGUI(),
                            "Fehler beim Erstellen des Teams",
                            builder.toString()
                    );
                });
            }
        };
        listeners.add(creationFinishedListener);
    }

    /**
     * Baut das UI-Layout für diese Seite auf. Enthält ein Textfeld für den Teamnamen
     * und einen Button, der ein {@link AttemptTeamCreationEvent} auslöst.
     *
     * @return Das Haupt-UI-Element (Panel), das alle Komponenten enthält.
     */
    @Override
    public Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));
        Panel mainPanel = new Panel(new GridLayout(2));

        // Textfeld für den Teamnamen
        TerminalSize size = new TerminalSize(30, 1);
        Label teamName = new Label("Teamname");
        mainPanel.addComponent(teamName);
        TextBox teamNameTextBox = new TextBox().setPreferredSize(size);
        mainPanel.addComponent(teamNameTextBox);

        contentPanel.addComponent(mainPanel);

        // Button, um den Erstellungsprozess zu starten
        contentPanel.addComponent(new Button("Fertig", () -> {
            String teamNameString = teamNameTextBox.getText();
            new AttemptTeamCreationEvent(teamNameString).call();
        }));

        // Footer-Komponente (aus LaternaPage) einfügen
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