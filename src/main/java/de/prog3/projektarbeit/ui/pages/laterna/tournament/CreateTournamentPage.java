package de.prog3.projektarbeit.ui.pages.laterna.tournament;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import de.prog3.projektarbeit.data.database.query.LeagueQuery;
import de.prog3.projektarbeit.data.database.query.TournamentQuery;
import de.prog3.projektarbeit.data.objects.League;
import de.prog3.projektarbeit.data.objects.Tournament;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.tournament.AttemptTournamentCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.tournament.TournamentFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.tournament.TournamentCreationFinishedListener;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;
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
        listeners.add(new TournamentCreationFinishedListener() {
            @Override
            public void onEvent(TournamentFinishedEvent event) {
                event.getExceptions().ifPresentOrElse(exceptions -> {
                    StringBuilder message = new StringBuilder();
                    for (Exception e : exceptions) {
                        message.append(e.getMessage()).append("\n");
                    }
                    try {
                        MessageDialog.showMessageDialog(window.getTextGUI(), "Fehler", message.toString());
                    } catch (Exception ignore){}
                },() -> close());
            }
        });
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
                    new AttemptTournamentCreationEvent(league.getName(), league.getParticipants()).call();
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
