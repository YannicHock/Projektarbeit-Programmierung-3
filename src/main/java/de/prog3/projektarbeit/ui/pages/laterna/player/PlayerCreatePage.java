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

public class PlayerCreatePage extends LaternaPage {
    private final Window window;
    private final LaternaView view;
    private final ArrayList<EventListener<? extends Event>> listeners;

    public PlayerCreatePage(LaternaView view) {
        this.window = new BasicWindow("Spieler erstellen");
        this.view = view;
        listeners = new ArrayList<>();
        registerListener();
        open();
    }

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
                    MessageDialog.showMessageDialog(window.getTextGUI(), "Fehler beim erstellen des Spielers", builder.toString());
                });
            }
        };
        listeners.add(creationFinishedEventEventListener);
    }


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

        contentPanel.addComponent(new Button("Fertig", () ->{
            String firstNameString = firstNameTextBox.getText();
            String lastNameString = lastNameTextBox.getText();
            String birthDateString = birthDateTextBox.getText();
            String numberString = numberTextBox.getText();
            ArrayList<Position> positionResult = new ArrayList<>();
            checkBoxList.getCheckedItems().forEach(item -> {
                Position checkedPosition = Position.getByFriendlyName(item);
                if(checkedPosition != null){
                    positionResult.add(checkedPosition);
                }
            });

            new AttemptPlayerCreationEvent(firstNameString, lastNameString, birthDateString, numberString, positionResult).call();

        }));
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
