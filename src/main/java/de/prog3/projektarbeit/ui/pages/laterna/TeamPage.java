package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.Player;
import de.prog3.projektarbeit.Team;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.HashMap;
import java.util.List;

public class TeamPage implements LaternaPage{
    private final Window window;
    private final LaternaView view;
    private final Team team;
    private final String name;

    private HashMap<String, Player> map = new HashMap<>();

    public TeamPage(LaternaView view, Team team) {
        this.name = "Team  - " + team.getName();
        for(Player player : team.getPlayers()){
            map.put(player.getName() + player.getAge(), player);
        }
        this.team = team;
        this.window = new BasicWindow();
        this.view = view;
        open();
    }

    private Component get(){
        Panel contentPanel = new Panel(new GridLayout(2));

        GridLayout gridLayout = (GridLayout)contentPanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);


        contentPanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(1)));

        Table<String> table = new Table<String>("Spielername", "Alter", "Position");
        for(Player player : team.getPlayers()){
            table.getTableModel().addRow(player.getName(), player.getAge() + "", player.getPosition());
        }

        table.setSelectAction(() -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            String key = data.getFirst() + data.get(1);
            new OpenPageEvent(view, PageType.PLAYER, map.get(key)).call();
        });

        contentPanel.addComponent(table);
        contentPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        buttonPanel.addComponent(new Button("Zurück", this::close));
        //buttonPanel.addComponent(new Button("Schließen", this::close));
        contentPanel.addComponent(buttonPanel.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(2)));

        return contentPanel;
    }



    public void open() {
        this.view.getTextGUI().addWindow(window);
        window.setComponent(get());
        window.waitUntilClosed();
    }


    @Override
    public Window getWindow() {
        return null;
    }

    @Override
    public void close() {
        window.close();
        new WindowCloseEvent(view).call();
    }

    @Override
    public String getName() {
        return "";
    }
}
