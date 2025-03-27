package de.prog3.projektarbeit.data.database.query;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.League;
import de.prog3.projektarbeit.data.objects.Team;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static de.prog3.projektarbeit.data.jooq.tables.League.LEAGUE;


public class LeagueQuery {

    private static final Logger logger = LoggerFactory.getLogger(LeagueQuery.class);


    public static String getNameById(int id) {
        logger.info("Lade Liga mit ID: {}", id);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Record record = ctx.select().from(LEAGUE).where(LEAGUE.ID.eq(id)).fetchOne();
        if (record != null) {
            return record.getValue(LEAGUE.NAME);
        }
        return null;
    }

    public static ArrayList<League> getAll() {
        logger.info("Lade alle Ligen");
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(LEAGUE).fetch();
        ArrayList<League> leagues = new ArrayList<>();
        result.forEach(record -> {
            ArrayList<Team> participatingTeams = TeamQuery.getAllTeamsInLeague(record.get(LEAGUE.ID));
            leagues.add(new League(record.get(LEAGUE.ID), record.get(LEAGUE.NAME),participatingTeams));
            logger.debug("Gefundene Liga: {} mit ID: {}", record.get(LEAGUE.NAME), record.get(LEAGUE.ID));
        });
        logger.info("Gefundene Ligen: {}", leagues.size());
        return leagues;
    }

}
