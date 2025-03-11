package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.table.Table;
import de.prog3.projektarbeit.data.PositionGrouping;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.pages.laterna.LaternaPage;
import de.prog3.projektarbeit.ui.views.ViewType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class PlayerPage implements LaternaPage {

    private final Window window;
    private final LaternaView view;
    private Player player;
    private final String name;
    private final Label nameContent;
    private final Label ageContent;
    private final Label birthDateContent;
    private final Label numberContent;
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
        positionContent = new Label("");
        listeners = new ArrayList<>();
        registerListener();
        open();
    }


    private void open() {
        window.setHints(List.of(Window.Hint.CENTERED));
        view.getGui().addWindow(window);
        getWindow().setComponent(get());
        getWindow().waitUntilClosed();
    }

    private void registerListener(){
    }


    private Component get() {
        Panel contentPanel = new Panel(new GridLayout(1));

        Panel mainpanel = new Panel(new GridLayout(2));
        contentPanel.addComponent(mainpanel);

        Label nameLabel = new Label("Name: ");
        mainpanel.addComponent(nameLabel);
        nameContent.setText(player.getFirstName() + " " + player.getLastName());
        mainpanel.addComponent(nameContent);


        Label ageLable = new Label("Alter: ");
        mainpanel.addComponent(ageLable);
        ageContent.setText(player.getAge() + "");
        mainpanel.addComponent(ageContent);

        Label birthDateLabel = new Label("Geburtsdatum: ");
        mainpanel.addComponent(birthDateLabel);
        try {
            birthDateContent.setText(Player.parseDateToString(player.getDateOfBirth()));
        } catch (ParseException e){
            birthDateContent.setText("Fehler beim Konvertieren des Geburtsdatums");
        }
        mainpanel.addComponent(birthDateContent);

        Label numberLabel = new Label("Rückennummer: ");
        mainpanel.addComponent(numberLabel);
        numberContent.setText(player.getNumber() + "");
        mainpanel.addComponent(numberContent);


        Label positionLabel = new Label("Position(en): ");
        mainpanel.addComponent(positionLabel);
        positionContent.setText(player.getPositionsAsString());
        mainpanel.addComponent(positionContent);
        mainpanel.addComponent(new EmptySpace());
        Panel positionPanel = new Panel(new GridLayout(1));
        positionPanel.addComponent(positionLabel);
        positionPanel.addComponent(positionContent);
        contentPanel.addComponent(positionPanel);


        Panel footerPanel = new Panel(new GridLayout(2)).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2));
        footerPanel.addComponent(new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
        footerPanel.addComponent(new Separator(Direction.HORIZONTAL).setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        buttonPanel.addComponent(new Button("Schließen", this::close));
        footerPanel.addComponent(buttonPanel.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(2)));

        contentPanel.addComponent(footerPanel);
        return contentPanel;
    }

    @Override
    public Window getWindow() {
        return window;
    }

    @Override
    public void close() {
        getWindow().close();
        new WindowCloseEvent(view).call();
    }

    @Override
    public String getName() {
        return this.name;
    }

}