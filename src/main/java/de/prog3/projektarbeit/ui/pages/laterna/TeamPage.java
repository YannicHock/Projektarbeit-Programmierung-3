package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.PositionGrouping;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TeamPage implements LaternaPage {
    private final Window window;
    private final LaternaView view;
    private final Team team;
    private final String name;
    private final Table<String> GK_table;
    private final Table<String> DEF_table;
    private final Table<String> MID_table;
    private final Table<String> ATK_table;
    HashMap<PositionGrouping, List<Player>> playerGroupings;
    private final ArrayList<EventListener<? extends Event>> listeners;


    public TeamPage(LaternaView view, Team team) {
        this.name = "Team  - " + team.getName();
        this.team = team;
        this.window = new BasicWindow(name);
        this.view = view;
        playerGroupings = new HashMap<>();
        this.GK_table = new Table<>("ID", "Spielername", "Alter", "Rückennummer");
        this.DEF_table = new Table<>("ID", "Spielername", "Alter", "Rückennummer");
        this.MID_table = new Table<>("ID", "Spielername", "Alter", "Rückennummer");
        this.ATK_table = new Table<>("ID", "Spielername", "Alter", "Rückennummer");
        listeners = new ArrayList<>();
        loadPlayerGroupings();
        drawTables();
        registerListener();
        open();
    }

    private void loadPlayerGroupings(){
        for (Player player : team.getPlayers().values()) {
            groupPlayer(player);
        }
        playerGroupings.keySet().forEach(positionGrouping -> playerGroupings.compute(positionGrouping, (k, players) -> players));
    }

    private void registerListener(){
    }



    private void open() {
        window.setHints(List.of(Window.Hint.CENTERED));
        view.getGui().addWindow(window);
        getWindow().setComponent(get());
        getWindow().waitUntilClosed();
    }


    private void groupPlayer(Player player) {
        PositionGrouping grouping = PositionGrouping.getGrouping(player);
        List<Player> list = new ArrayList<>();
        if(playerGroupings.containsKey(grouping)){
            list = new ArrayList<>(playerGroupings.get(grouping));
        }
        list.add(player);
        playerGroupings.put(grouping, list);
    }


    private void drawTables(){
        playerGroupings.keySet().forEach(grouping -> {
            List<Player> players = playerGroupings.get(grouping);
            players = players.stream().sorted(Comparator.comparing(Player::getLastName)).toList();
            players.forEach(player -> {
                Table<String> table;
                switch (grouping){
                    case STRIKER -> table = ATK_table;
                    case MIDFIELDER -> table = MID_table;
                    case DEFENDER -> table = DEF_table;
                    case GOALKEEPER -> table = GK_table;
                    default -> table = null;
                }
                table.getTableModel().addRow(player.getId() + "", player.getFirstName() + " " + player.getLastName(), player.getAge() + "", player.getNumber() + "");
            });
        });
    }

    public Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainPanel = new Panel(new GridLayout(1));

        mainPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(1)));

        ArrayList<Table<String>> list = new ArrayList<>(List.of(GK_table, DEF_table, MID_table, ATK_table));
        addClickAction(list);
        mainPanel.addComponent(GK_table.withBorder(Borders.singleLine(PositionGrouping.GOALKEEPER.getFriendlyName())));
        mainPanel.addComponent(DEF_table.withBorder(Borders.singleLine(PositionGrouping.DEFENDER.getFriendlyName())));
        mainPanel.addComponent(MID_table.withBorder(Borders.singleLine(PositionGrouping.MIDFIELDER.getFriendlyName())));
        mainPanel.addComponent(ATK_table.withBorder(Borders.singleLine(PositionGrouping.STRIKER.getFriendlyName())));
        int width = calculateWidth(list);
        GK_table.setPreferredSize(new TerminalSize(width, calculateHeight(GK_table)));
        DEF_table.setPreferredSize(new TerminalSize(width, calculateHeight(DEF_table)));
        MID_table.setPreferredSize(new TerminalSize(width, calculateHeight(MID_table)));
        ATK_table.setPreferredSize(new TerminalSize(width, calculateHeight(ATK_table)));

        contentPanel.addComponent(mainPanel);

        Panel footerPanel = new Panel(new GridLayout(2)).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2));
        footerPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        footerPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        buttonPanel.addComponent(new Button("Schließen", this::close));
        footerPanel.addComponent(buttonPanel.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(2)));

        contentPanel.addComponent(footerPanel);

        return contentPanel;
    }

    private void addClickAction(ArrayList<Table<String>> list){
        list.forEach(stringTable -> stringTable.setSelectAction(() -> {
            List<String> data = stringTable.getTableModel().getRow(stringTable.getSelectedRow());
            Integer key = Integer.parseInt(data.getFirst());
            new OpenPageEvent(view, PageType.PLAYER, team.getPlayers().get(key)).call();
        }));
    }

    private int calculateHeight(Table<String> table){
        return Math.min(5, table.getPreferredSize().getRows());
    }

    private int calculateWidth(ArrayList<Table<String>> list){
        int width = 0;
        for (Table<String> table : list) {
            width = Math.max(width, table.getPreferredSize().getColumns());
        }
        return width;
    }

    @Override
    public Window getWindow() {
        return window;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void close() {
        getWindow().close();
        new WindowCloseEvent(view).call();
    }

}