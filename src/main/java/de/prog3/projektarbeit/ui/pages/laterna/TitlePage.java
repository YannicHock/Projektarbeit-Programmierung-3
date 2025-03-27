package de.prog3.projektarbeit.ui.pages.laterna;

        import com.googlecode.lanterna.gui2.*;
        import de.prog3.projektarbeit.eventHandling.events.Event;
        import de.prog3.projektarbeit.eventHandling.events.ui.OpenPageEvent;
        import de.prog3.projektarbeit.eventHandling.listeners.EventListener;
        import de.prog3.projektarbeit.ui.pages.PageType;
        import de.prog3.projektarbeit.ui.views.laterna.LaternaView;

        import java.util.ArrayList;

        /**
         * Die TitlePage-Klasse repräsentiert die Hauptmenüseite in der Laterna-Ansicht.
         */
        public class TitlePage extends LaternaPage {

            private final Window window;
            private final LaternaView view;

            /**
             * Konstruktor der TitlePage-Klasse.
             * Initialisiert das Fenster und die Ansicht und öffnet die Seite.
             *
             * @param view Die LaternaView-Instanz, die diese Seite enthält.
             */
            public TitlePage(LaternaView view){
                this.window = new BasicWindow("Hauptmenu");
                this.view = view;
                open();
            }

            /**
             * Erstellt und gibt die Hauptkomponente der Seite zurück.
             *
             * @return Die Hauptkomponente der Seite.
             */
            public Component get(){

                Panel contentPanel = new Panel(new GridLayout(1));

                Panel mainPanel = new Panel();
                mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

                // Spielerverwaltung
                Panel playerManagement = new Panel();
                playerManagement.addComponent(new Button("Spieler erstellen", () -> new OpenPageEvent(view, PageType.CREATE_PLAYER).call()));
                playerManagement.addComponent(new Button("Vereinslose Spieler anzeigen", () -> new OpenPageEvent(view, PageType.FREE_AGENTS).call()));
                mainPanel.addComponent(playerManagement.withBorder(Borders.singleLine("Spielerverwaltung")));

                // Teamverwaltung
                Panel teamManagement = new Panel();
                teamManagement.addComponent(new Button("Teamübersicht", () -> new OpenPageEvent(view, PageType.TEAMS).call()));
                teamManagement.addComponent(new Button("Team erstellen", () -> new OpenPageEvent(view, PageType.CREATE_TEAM).call()));
                mainPanel.addComponent(teamManagement.withBorder(Borders.singleLine("Teamverwaltung")));

                // Turnierverwaltung
                Panel tournamentManagement = new Panel();
                mainPanel.addComponent(tournamentManagement.withBorder(Borders.singleLine("Tunierverwaltung")));
                tournamentManagement.addComponent(new Button("Turniere Ansehen", () -> new OpenPageEvent(view, PageType.TOURNAMENT).call()));
                contentPanel.addComponent(mainPanel);

                // Seitenende
                contentPanel.addComponent(footer(true));

                return contentPanel;
            }

            /**
             * Gibt die Liste der Event-Listener zurück.
             *
             * @return Eine leere Liste von Event-Listenern.
             */
            @Override
            protected ArrayList<EventListener<? extends Event>> getListeners() {
                return new ArrayList<>();
            }

            /**
             * Gibt das Fenster der Seite zurück.
             *
             * @return Das Fenster der Seite.
             */
            @Override
            public Window getWindow() {
                return window;
            }

            /**
             * Gibt die LaternaView-Instanz der Seite zurück.
             *
             * @return Die LaternaView-Instanz.
             */
            @Override
            public LaternaView getLaternaView() {
                return view;
            }

            /**
             * Gibt den Namen der Seite zurück.
             *
             * @return Der Name der Seite.
             */
            @Override
            public String getName() {
                return "Hauptmenu";
            }
        }