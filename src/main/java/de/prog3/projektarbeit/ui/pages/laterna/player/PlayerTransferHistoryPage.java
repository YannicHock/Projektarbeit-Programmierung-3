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

import java.util.ArrayList;

/**
 * Diese Klasse stellt eine Seite zur Anzeige der Transferhistorie eines Spielers dar.
 * Die Seite zeigt in einer Tabelle wichtige Informationen zu jedem Transfer an, wie Datum,
 * altes Team, neues Team und den Ablösebetrag. Die Daten werden über die TransferQuery aus
 * der Datenbank geladen. Zudem werden EventListener verwaltet, die in Zukunft auf Ereignisse
 * reagieren können (derzeit ist die Listener-Registrierung noch nicht implementiert).
 *
 * Die Klasse erbt von LaternaPage und ist Teil der Lanterna-basierten Benutzeroberfläche.
 */
public class PlayerTransferHistoryPage extends LaternaPage {

    /**
     * Das Fenster, in dem die Transferhistorie angezeigt wird.
     */
    private final Window window;

    /**
     * Die zugehörige LaternaView, die die Benutzeroberfläche verwaltet.
     */
    private final LaternaView view;

    /**
     * Der Spieler, dessen Transferhistorie dargestellt wird.
     */
    private final Player player;

    /**
     * Der Name der Seite, der in der UI als Titel verwendet wird.
     */
    private final String name;

    /**
     * Die Liste der Transferobjekte, die die Transferhistorie des Spielers darstellen.
     */
    private final ArrayList<Transfer> transferHistory;

    /**
     * Eine Tabelle zur Darstellung der Transferdaten (Datum, altes Team, neues Team, Ablöse).
     */
    private final Table<String> table;

    /**
     * Eine Liste von EventListenern, die für zukünftige Ereignisse registriert werden können.
     */
    private final ArrayList<EventListener<? extends Event>> listeners;

    /**
     * Konstruktor: Initialisiert die Seite zur Anzeige der Transferhistorie eines Spielers.
     * Hierbei wird der Name der Seite anhand des Spielernamens gesetzt, das Fenster erstellt,
     * die Transferhistorie über die TransferQuery geladen und die UI-Komponenten aufgebaut.
     *
     * @param view   Die übergeordnete LaternaView, in der diese Seite angezeigt wird.
     * @param player Der Spieler, dessen Transferhistorie angezeigt werden soll.
     */
    public PlayerTransferHistoryPage(LaternaView view, Player player) {
        this.name = "Transferhistorie von " + player.getFullName();
        this.player = player;
        this.window = new BasicWindow(name);
        this.view = view;
        // Transferhistorie des Spielers aus der Datenbank abrufen
        this.transferHistory = TransferQuery.getTransfers(player);
        // Tabelle zur Anzeige der Transferdaten initialisieren
        this.table = new Table<>("Datum", "Altes Team", "Neues Team", "Ablöse");
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

    /**
     * Registriert EventListener für diese Seite.
     * (Aktuell ist diese Methode leer, kann jedoch in Zukunft erweitert werden, um auf relevante Ereignisse zu reagieren.)
     */
    private void registerListener() {
        // Platzhalter für die Registrierung von EventListenern
    }

    /**
     * Baut das UI-Layout der Seite auf.
     * Es wird ein Panel mit einer Tabelle erstellt, in der die Transferdaten dargestellt werden.
     *
     * @return Das Panel, das die UI-Komponenten der Transferhistorie enthält.
     */
    @Override
    protected Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));
        Panel mainpanel = new Panel(new GridLayout(2));
        GridLayout gridLayout = (GridLayout) mainpanel.getLayoutManager();
        gridLayout.setHorizontalSpacing(3);

        mainpanel.addComponent(
                new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1))
        );

        // Jede Transferzeile wird zur Tabelle hinzugefügt
        for (Transfer transfer : transferHistory) {
            table.getTableModel().addRow(
                    Formatter.parseDateToString(transfer.getDate()),
                    transfer.getFromTeamName(),
                    transfer.getToTeamName(),
                    Formatter.formatCurrency(transfer.getAmount())
            );
        }

        mainpanel.addComponent(table);
        contentPanel.addComponent(mainpanel);
        contentPanel.addComponent(footer(false));
        return contentPanel;
    }

    /**
     * Gibt das Fenster dieser Seite zurück.
     *
     * @return Das Fenster-Objekt.
     */
    @Override
    public Window getWindow() {
        return window;
    }

    /**
     * Gibt die zugehörige LaternaView zurück.
     *
     * @return Die aktuelle LaternaView.
     */
    @Override
    public LaternaView getLaternaView() {
        return view;
    }

    /**
     * Gibt den Namen dieser Seite zurück.
     *
     * @return Den Namen der Seite.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gibt die Liste der registrierten EventListener dieser Seite zurück.
     *
     * @return Eine Liste von EventListenern.
     */
    @Override
    public ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }
}