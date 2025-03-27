package de.prog3.projektarbeit.ui.pages.laterna.player;

import com.googlecode.lanterna.gui2.*;
import de.prog3.projektarbeit.data.database.query.TeamQuery;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerUpdateFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerUpdateFinishedListener;
import de.prog3.projektarbeit.exceptions.TeamNotFoundExeption;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;
import de.prog3.projektarbeit.utils.Formatter;

import java.util.ArrayList;


public class PlayerPage extends LaternaPage {

    private final Window window;
    private final LaternaView view;
    private Player player;
    private final String name;
    private final Label nameContent;
    private final Label ageContent;
    private final Label birthDateContent;
    private final Label numberContent;
    private final Label currentTeamContent;
    private final Label positionContent;
    private final ArrayList<EventListener<? extends Event>> listeners;

    public PlayerPage(LaternaView view, Player player) {
        this.name = "Spieleransicht";
        this.player = player;
        this.window = new BasicWindow(name);
        this.view = view;
        nameContent = new Label("");
        ageContent = new Label("");
        birthDateContent = new Label("");
        numberContent = new Label("");
        currentTeamContent = new Label("");
        positionContent = new Label("");
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

    private void updatePlayer(Player player){
        this.player = player;
        nameContent.setText(player.getFullName());
        ageContent.setText(player.getAge() + "");
        birthDateContent.setText(Formatter.parseDateToString(player.getDateOfBirth()));
        numberContent.setText(player.getNumber() + "");
        try {
            currentTeamContent.setText(TeamQuery.getNameById(player.getTeamId()));
        } catch (TeamNotFoundExeption e){
            currentTeamContent.setText("Fehler beim Laden des Teams");
        }
        positionContent.setText(player.getPositionsAsString());
    }

    private void registerListener(){
       EventListener<PlayerUpdateFinishedEvent> playerUpdateFinishedEventListener = new PlayerUpdateFinishedListener() {
            @Override
            public void onEvent(PlayerUpdateFinishedEvent event) {
                event.getPlayer().ifPresent(player -> {
                    if(player.getId() == PlayerPage.this.player.getId()){
                        updatePlayer(player);
                    }
                });
            }
        };
        listeners.add(playerUpdateFinishedEventListener);
    }


    @Override
    protected Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainpanel = new Panel(new GridLayout(2));
        contentPanel.addComponent(mainpanel);

        Label nameLabel = new Label("Name: ");
        mainpanel.addComponent(nameLabel);
        nameContent.setText(player.getFullName());
        mainpanel.addComponent(nameContent);


        Label ageLable = new Label("Alter: ");
        mainpanel.addComponent(ageLable);
        ageContent.setText(player.getAge() + "");
        mainpanel.addComponent(ageContent);

        Label birthDateLabel = new Label("Geburtsdatum: ");
        mainpanel.addComponent(birthDateLabel);
        birthDateContent.setText(Formatter.parseDateToString(player.getDateOfBirth()));
        mainpanel.addComponent(birthDateContent);

        Label numberLabel = new Label("Rückennummer: ");
        mainpanel.addComponent(numberLabel);
        numberContent.setText(player.getNumber() + "");
        mainpanel.addComponent(numberContent);

        Label currentTeamLabel = new Label("Aktuelles Team: ");
        mainpanel.addComponent(currentTeamLabel);
        try {
            currentTeamContent.setText(TeamQuery.getNameById(player.getTeamId()));
        } catch (TeamNotFoundExeption e){
            currentTeamContent.setText("Fehler beim Laden des Teams");
        }
        mainpanel.addComponent(currentTeamContent);
        mainpanel.addComponent(new EmptySpace());
        mainpanel.addComponent(new Button("Transferhistorie", () -> new OpenPageEvent(view, PageType.PLAYER_TRANSFER_HISTORY, player).call()));

        mainpanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Label positionLabel = new Label("Position(en): ");
        mainpanel.addComponent(positionLabel);
        Panel positionPanel = new Panel(new GridLayout(1));
        contentPanel.addComponent(positionPanel);
        positionPanel.addComponent(positionContent);
        positionContent.setText(player.getPositionsAsString());

        contentPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        Panel buttonPanel = new Panel(new GridLayout(3));


        buttonPanel.addComponent(new Button("Bearbeiten", () -> new OpenPageEvent(view, PageType.EDIT_PLAYER, player).call()));
        buttonPanel.addComponent(new Button("Transfer", () -> new OpenPageEvent(view, PageType.TRANSFER_PLAYER, player).call()));

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