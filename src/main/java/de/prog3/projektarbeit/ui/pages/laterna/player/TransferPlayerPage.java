package de.prog3.projektarbeit.ui.pages.laterna.player;

import com.googlecode.lanterna.gui2.*;
import de.prog3.projektarbeit.data.factories.TeamFactory;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerUpdateFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerUpdateFinishedListener;
import de.prog3.projektarbeit.exceptions.TeamNotFoundExeption;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


public class TransferPlayerPage extends LaternaPage {

    private final Window window;
    private final LaternaView view;
    private Player player;
    private final String name;
    private final Label nameContent;
    private final Label currentTeamContent;
    private final HashMap<Integer, Integer> indexMap;
    private final ArrayList<EventListener<? extends Event>> listeners;

    public TransferPlayerPage(LaternaView view, Player player) {
        this.name = "Spielertransfer";
        this.player = player;
        this.window = new BasicWindow(name);
        this.view = view;
        nameContent = new Label("");
        currentTeamContent = new Label("");
        listeners = new ArrayList<>();
        indexMap = new HashMap<>();
        registerListener();
        open();
    }

    private void registerListener(){
    }


    @Override
    protected Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainpanel = new Panel(new GridLayout(2));
        contentPanel.addComponent(mainpanel);

        Label nameLabel = new Label("Spieler: ");
        mainpanel.addComponent(nameLabel);
        nameContent.setText(player.getFirstName() + " " + player.getLastName());
        mainpanel.addComponent(nameContent);

        Label numberLabel = new Label("Rückennummer: ");
        mainpanel.addComponent(numberLabel);
        TextBox numberTextBox = new TextBox();
        numberTextBox.setText(player.getNumber() + "");
        mainpanel.addComponent(numberTextBox);

        Label currentTeamLabel = new Label("Aktuelles Team: ");
        mainpanel.addComponent(currentTeamLabel);
        try {
            currentTeamContent.setText(TeamFactory.getNameById(player.getTeamId()));
        } catch (TeamNotFoundExeption e){
            currentTeamContent.setText("Fehler beim Laden des Teams");
        }
        mainpanel.addComponent(currentTeamContent);

        Label targetTeamLabel = new Label("Neues Team: ");
        mainpanel.addComponent(targetTeamLabel);
        ComboBox<String> teamComboBox = new ComboBox<>();
        int index = 0;
        for(Team team : TeamFactory.getAllWithoutPlayers().values()){
            indexMap.put(index, team.getId());
            index++;
            teamComboBox.addItem(team.getName());
        }
        teamComboBox.addItem("Vereinslos");
        indexMap.put(index, 0);
        mainpanel.addComponent(teamComboBox);


        Panel buttonPanel = new Panel(new GridLayout(3));

        contentPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        buttonPanel.addComponent(new Button("Fertig", () -> {
            String numberString = numberTextBox.getText();
            int teamId = indexMap.get(teamComboBox.getSelectedIndex());
            System.out.printf("Number: %s, TeamId: %d\n", numberString, teamId);
        }));
        contentPanel.addComponent(buttonPanel);
        contentPanel.addComponent(footer(false, false));
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
        return this.name;
    }

    @Override
    public ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }
}