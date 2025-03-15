package de.prog3.projektarbeit.ui.pages;

import de.prog3.projektarbeit.ui.pages.laterna.player.*;
import de.prog3.projektarbeit.ui.pages.laterna.team.CreateTeamPage;
import de.prog3.projektarbeit.ui.pages.laterna.team.TeamPage;
import de.prog3.projektarbeit.ui.pages.laterna.team.TeamsPage;
import de.prog3.projektarbeit.ui.pages.laterna.TitlePage;
import de.prog3.projektarbeit.ui.views.View;
import de.prog3.projektarbeit.ui.views.ViewType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public enum PageType {
    MAIN(new HashMap<>() {{
        put(ViewType.LATERNA, TitlePage.class);
    }}),
    PLAYER(new HashMap<>() {{
        put(ViewType.LATERNA, PlayerPage.class);
    }}),
    FREE_AGENTS(new HashMap<>() {{
        put(ViewType.LATERNA, FreeAgentsPage.class);
    }}),
    CREATE_PLAYER(new HashMap<>() {{
        put(ViewType.LATERNA, CreatePlayerPage.class);
    }}),
    EDIT_PLAYER(new HashMap<>() {{
        put(ViewType.LATERNA, EditPlayerPage.class);
    }}),
    TRANSFER_PLAYER(new HashMap<>() {{
        put(ViewType.LATERNA, TransferPlayerPage.class);
    }}),
    TEAM(new HashMap<>() {{
        put(ViewType.LATERNA, TeamPage.class);
    }}),
    CREATE_TEAM(new HashMap<>() {{
        put(ViewType.LATERNA, CreateTeamPage.class);
    }}),
    TEAMS(new HashMap<>() {{
        put(ViewType.LATERNA, TeamsPage.class);
    }})
    ;

    private static final Logger logger = LoggerFactory.getLogger(PageType.class);
    private final HashMap<ViewType, Class<? extends Page>> pageClasses;

    PageType(HashMap<ViewType, Class<? extends Page>> titlePageClasses) {
        this.pageClasses = titlePageClasses;
    }

    public void openPage(View view, Object... additionalParams) {
        try {
            Class<? extends Page> pageClass = pageClasses.get(view.getType());
            if (pageClass != null) {
                Class<?>[] paramTypes = new Class<?>[additionalParams.length + 1];
                paramTypes[0] = view.getClass();
                for (int i = 0; i < additionalParams.length; i++) {
                    paramTypes[i + 1] = additionalParams[i].getClass();
                }

                Object[] params = new Object[additionalParams.length + 1];
                params[0] = view;
                System.arraycopy(additionalParams, 0, params, 1, additionalParams.length);

                pageClass.getDeclaredConstructor(paramTypes).newInstance(params);
            } else {
                logger.warn("Keine Seitenklasse für den Ansichtstyp gefunden: {}", view.getType());
            }
        } catch (Exception e) {
            logger.error("Fehler beim Öffnen der Seite", e);
        }
    }
}