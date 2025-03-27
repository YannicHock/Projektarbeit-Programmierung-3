package de.prog3.projektarbeit.ui.pages.laterna.player;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerCreationFinishedListener;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Diese Klasse stellt die Benutzeroberfläche zum Erstellen eines neuen Spielers dar.
 * Hier können Benutzer alle notwendigen Informationen (Vorname, Nachname, Geburtsdatum, Rückennummer und Positionen)
 * eingeben. Nach Eingabe der Daten wird ein {@link AttemptPlayerCreationEvent} ausgelöst, das den Erstellungsprozess
 * startet. Außerdem registriert die Seite einen Listener, der auf ein {@link PlayerCreationFinishedEvent} reagiert,
 * um im Erfolgsfall das Fenster zu schließen oder bei Fehlern eine Fehlermeldung anzuzeigen.
 */
public class CreatePlayerPage extends LaternaPage {

    /**
     * Das Fenster, in dem die Spieler-Erstellungsseite angezeigt wird.
     */
    private final Window window;

    /**
     * Die zugehörige LaternaView, die für die Verwaltung des Bildschirms zuständig ist.
     */
    private final LaternaView view;

    /**
     * Liste der registrierten EventListener für diese Seite.
     */
    private final ArrayList<EventListener<? extends Event>> listeners;

    /**
     * Konstruktor. Erstellt ein Fenster mit dem Titel "Spieler erstellen", registriert notwendige Event-Listener
     * und öffnet die Seite.
     *
     * @param view Die übergeordnete LaternaView, in der die Seite dargestellt wird.
     */
    public CreatePlayerPage(LaternaView view) {
        this.window = new BasicWindow("Spieler erstellen");
        this.view = view;
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

    /**
     * Registriert einen Listener für das PlayerCreationFinishedEvent.
     * Der Listener schließt das Fenster, wenn ein Spieler erfolgreich erstellt wurde.
     * Im Fehlerfall zeigt er eine Fehlermeldung an.
     */
    private void registerListener(){
        EventListener<PlayerCreationFinishedEvent> creationFinishedEventEventListener = new PlayerCreationFinishedListener() {
            @Override
            public void onEvent(PlayerCreationFinishedEvent event) {
                event.getPlayer().ifPresent(player -> window.close());
                event.getExceptions().ifPresent(exceptions -> {
                    StringBuilder builder = new StringBuilder();
                    exceptions.forEach(exception -> {
                        builder.append(exception.getMessage());
                        builder.append("\n");
                    });
                    MessageDialog.showMessageDialog(window.getTextGUI(),
                            "Fehler beim Erstellen des Spielers", builder.toString());
                });
            }
        };
        listeners.add(creationFinishedEventEventListener);
    }

    /**
     * Baut das Layout der Seite auf. Hier werden Eingabefelder für Vorname, Nachname, Geburtsdatum,
     * Rückennummer und Positionen angeordnet. Außerdem wird ein Button hinzugefügt, der beim Klicken ein
     * {@link AttemptPlayerCreationEvent} auslöst.
     *
     * @return Das erstellte UI-Element als Component.
     */
    @Override
    public Component get() {

        Panel contentPanel = new Panel(new GridLayout(1));
        Panel mainPanel = new Panel(new GridLayout(2));

        TerminalSize size = new TerminalSize(30, 1);
        Label firstName = new Label("Vorname*");
        mainPanel.addComponent(firstName);
        TextBox firstNameTextBox = new TextBox().setPreferredSize(size);
        mainPanel.addComponent(firstNameTextBox);

        mainPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Label lastName = new Label("Nachname*");
        mainPanel.addComponent(lastName);
        TextBox lastNameTextBox = new TextBox().setPreferredSize(size);
        mainPanel.addComponent(lastNameTextBox);

        mainPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Label birthDate = new Label("Geburtsdatum*");
        mainPanel.addComponent(birthDate);
        TextBox birthDateTextBox = new TextBox().setPreferredSize(size);
        mainPanel.addComponent(birthDateTextBox);

        mainPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Label number = new Label("Rückennummer*");
        mainPanel.addComponent(number);
        TextBox numberTextBox = new TextBox().setPreferredSize(size);
        mainPanel.addComponent(numberTextBox);

        mainPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Label position = new Label("Position*");
        mainPanel.addComponent(position);
        CheckBoxList<String> checkBoxList = new CheckBoxList<>(size.withRelativeRows(3));
        Arrays.stream(Position.values()).forEach(playerposition -> checkBoxList.addItem(playerposition.getFriendlyName()));
        mainPanel.addComponent(checkBoxList);

        contentPanel.addComponent(mainPanel);

        contentPanel.addComponent(new Button("Fertig", () -> {
            String firstNameString = firstNameTextBox.getText();
            String lastNameString = lastNameTextBox.getText();
            String birthDateString = birthDateTextBox.getText();
            String numberString = numberTextBox.getText();
            ArrayList<Position> positionResult = new ArrayList<>();
            checkBoxList.getCheckedItems().forEach(item -> {
                Position checkedPosition = Position.getByFriendlyName(item);
                if (checkedPosition != null) {
                    positionResult.add(checkedPosition);
                }
            });
            new AttemptPlayerCreationEvent(firstNameString, lastNameString, birthDateString, numberString, positionResult).call();
        }));
        contentPanel.addComponent(footer(false));

        return contentPanel;
    }

    /**
     * Gibt das Fenster dieser Seite zurück.
     *
     * @return Das {@link Window}-Objekt.
     */
    @Override
    public Window getWindow() {
        return window;
    }

    /**
     * Gibt die zugehörige LaternaView zurück.
     *
     * @return Die aktuelle {@link LaternaView}.
     */
    @Override
    public LaternaView getLaternaView() {
        return view;
    }

    /**
     * Gibt den Namen dieser Seite zurück.
     *
     * @return Der Name als String.
     */
    @Override
    public String getName() {
        return "Spieler erstellen";
    }

    /**
     * Gibt alle registrierten EventListener für diese Seite zurück.
     *
     * @return Eine Liste von {@link EventListener}-Objekten.
     */
    @Override
    public ArrayList<EventListener<? extends Event>> getListeners() {
        return listeners;
    }
}