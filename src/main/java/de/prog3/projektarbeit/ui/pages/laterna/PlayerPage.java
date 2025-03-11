package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

public class PlayerPage implements LaternaPage{

    private final Window window;
    private final LaternaView view;
    private final Player player;
    private final String name;

    public PlayerPage(LaternaView view, Player player) {
        this.name = "Spieler  - " + player.getFirstName() + " " + player.getLastName();
        this.player = player;
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
        table.getTableModel().addRow(player.getFirstName() + " " + player.getLastName(), player.getAge() + "", player.getPositionsAsString());

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


    @Override
    public Window getWindow() {
        return null;
    }

    public void open() {
        this.view.getGui().addWindow(window);
        window.setComponent(get());
        window.waitUntilClosed();
    }

    @Override
    public void close() {
        window.close();
        new WindowCloseEvent(this.view).call();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
