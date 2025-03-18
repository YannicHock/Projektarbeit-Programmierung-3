package de.prog3.projektarbeit.ui.pages.laterna.player;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.database.query.TransferQuery;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.data.objects.Transfer;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;
import de.prog3.projektarbeit.utils.Formatter;

import java.text.ParseException;
import java.util.ArrayList;


public class PlayerTransferHistoryPage extends LaternaPage {

    private final Window window;
    private final LaternaView view;
    private final Player player;
    private final String name;
    private final ArrayList<Transfer> transferHistory;
    private final Table<String> table;
    private final ArrayList<EventListener<? extends Event>> listeners;

    public PlayerTransferHistoryPage(LaternaView view, Player player) {
        this.name = "Transferhistorie von " + player.getFullName();
        this.player = player;
        this.window = new BasicWindow(name);
        this.view = view;
        this.transferHistory = TransferQuery.getTransfers(player);
        this.table = new Table<>( "Datum", "Altes Team", "Neues Team", "Ablöse");
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

    private void registerListener(){
    }


    @Override
    protected Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainpanel = new Panel(new GridLayout(2));
        GridLayout gridLayout = (GridLayout) mainpanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        mainpanel.addComponent(
                new EmptySpace()
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(1)));

        for (Transfer transfer : transferHistory) {
            table.getTableModel().addRow(Formatter.parseDateToString(transfer.getDate()), transfer.getFromTeamName(), transfer.getToTeamName(), Formatter.formatCurrency(transfer.getAmount()));
        }

        mainpanel.addComponent(table);

        contentPanel.addComponent(mainpanel);


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
        return this.name;
    }

    @Override
    public ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }
}