package de.prog3.projektarbeit.ui.pages.laterna.player;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.database.query.PlayerQuery;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerUpdateFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerCreationFinishedListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerUpdateFinishedListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class FreeAgentsPage extends LaternaPage {
    private final Window window;
    private final LaternaView view;
    private final String name;
    private final Table<String> table;
    private final HashMap<Integer, Player> players;
    private final ArrayList<EventListener<? extends Event>> listeners;


    public FreeAgentsPage(LaternaView view) {
        this.name = "Vereinslose Spieler";
        this.window = new BasicWindow(name);
        this.view = view;
        this.players = PlayerQuery.getFreeAgents();
        this.table = new Table<>("ID", "Spielername", "Alter", "Rückennummer");
        listeners = new ArrayList<>();
        registerListener();
        drawTable();
        addClickAction();
        open();
    }

    private void registerListener() {
        EventListener<PlayerCreationFinishedEvent> creation = new PlayerCreationFinishedListener(){
            @Override
            public void onEvent(PlayerCreationFinishedEvent event) {
                event.getPlayer().ifPresent(player -> {
                    if(player.getTeamId() == 0){
                        players.put(player.getId(), player);
                        redrawTable();
                    }
                });
            }
        };

        EventListener<PlayerUpdateFinishedEvent> playerUpdateFinishedEventListener = new PlayerUpdateFinishedListener() {
            @Override
            public void onEvent(PlayerUpdateFinishedEvent event) {
                event.getPlayer().ifPresent(player -> {
                    if(players.containsKey(player.getId())){
                        players.put(player.getId(), player);
                        redrawTable();
                    }
                });
            }
        };
        listeners.add(playerUpdateFinishedEventListener);

        listeners.add(creation);
    }

    private void redrawTable(){
        table.getTableModel().clear();
        drawTable();
    }

    private void drawTable(){
        ArrayList<Player> playerList = new ArrayList<>(players.values());
        playerList.sort(Comparator.comparing(Player::getLastName));
        for (Player player : playerList) {
            table.getTableModel().addRow(player.getId() + "", player.getFullName(), player.getAge() + "", player.getNumber() + "");
        }
    }

    private void addClickAction(){
        table.setSelectAction(() -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            int id = Integer.parseInt(data.getFirst());
            new OpenPageEvent(view, PageType.PLAYER, players.get(id)).call();
        });
    }

    public Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainPanel = new Panel(new GridLayout(2));
        GridLayout gridLayout = (GridLayout) mainPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        mainPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(1)));


        mainPanel.addComponent(table);

        contentPanel.addComponent(mainPanel);
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
        return name;
    }

    @Override
    public ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }
}