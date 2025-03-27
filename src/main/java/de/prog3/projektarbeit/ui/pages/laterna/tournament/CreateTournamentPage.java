package de.prog3.projektarbeit.ui.pages.laterna.tournament;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.database.query.LeagueQuery;
import de.prog3.projektarbeit.data.database.query.TournamentQuery;
import de.prog3.projektarbeit.data.objects.League;
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

public class CreateTournamentPage extends LaternaPage {
    private final Window window;
    private final LaternaView view;
    private final List<League> leagues;
    private final ArrayList<EventListener<? extends Event>> listeners;

    public CreateTournamentPage(LaternaView view) {
        this.window = new BasicWindow("Spielplan erstellen");
        this.view = view;
        leagues = LeagueQuery.getAll();
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

    private void registerListener(){
    }


    @Override
    public Component get() {

        Panel contentPanel = new Panel(new GridLayout(1));

        Label title = new Label("Spielplan erstellen für:");
        contentPanel.addComponent(title);

        leagues.forEach(league -> {
            Button button = new Button(league.getName(), () -> {
                MessageDialog dialog = new MessageDialogBuilder().setTitle("Spielplan erstellen")
                        .setText("Spielplan für " + league.getName() + " erstellen?")
                        .addButton(MessageDialogButton.OK)
                        .addButton(MessageDialogButton.Cancel)
                        .build();
                MessageDialogButton res = dialog.showDialog(view.getGui());
                if(res.equals(MessageDialogButton.OK)){
                    Tournament tournament = new Tournament(0, league.getName(), league.getParticipants());
                    //TODO sauber über events lösen
                    TournamentQuery.save(tournament);
                    window.close();
                }
            });
            contentPanel.addComponent(button);
        });

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
