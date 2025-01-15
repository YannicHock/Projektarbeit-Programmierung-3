package de.prog3.projektarbeit.ui.pages.laterna;

import com.googlecode.lanterna.gui2.*;
import de.prog3.projektarbeit.Player;
import de.prog3.projektarbeit.data.PlayerFactory;
import de.prog3.projektarbeit.data.TeamFactory;
import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.RequestNewViewEvent;
import de.prog3.projektarbeit.eventHandling.events.ui.WindowCloseEvent;
import de.prog3.projektarbeit.ui.pages.PageType;
import de.prog3.projektarbeit.ui.views.ViewType;
import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

import java.util.UUID;

public class TitlePage implements LaternaPage {

    private final Window window;
    private final LaternaView view;

    public TitlePage(LaternaView view){
        this.window = new BasicWindow("Hauptmenu");
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
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        contentPanel.addComponent(new Button("Testseite", () -> new OpenPageEvent(view, PageType.TEST, "Test").call()));
        //contentPanel.addComponent(new Button("Zufallsspieler", () -> new OpenPageEvent(view, PageType.PLAYER, new PlayerFactory(UUID.randomUUID()).createPlayer()).call()));
        contentPanel.addComponent(new Button("Zufallsteam", () -> new OpenPageEvent(view, PageType.TEAM, new TeamFactory(UUID.randomUUID()).createTeam()).call()));
        contentPanel.addComponent(
                new Separator(Direction.HORIZONTAL)
                        .setLayoutData(
                                GridLayout.createHorizontallyFilledLayoutData(2)));
        Panel buttonPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        buttonPanel.addComponent(new Button("Neues Fenster", () -> new RequestNewViewEvent(ViewType.LATERNA).call()));
        buttonPanel.addComponent(new Button("Schließen", this::close));
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
        return window;
    }

    @Override
    public void close() {
        window.close();
        new WindowCloseEvent(this.view).call();
    }

    @Override
    public String getName() {
        return "Hauptmenu";
    }
}
