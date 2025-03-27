package de.prog3.projektarbeit.data.database.query;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.Match;
import static de.prog3.projektarbeit.data.jooq.tables.Match.MATCH;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.exceptions.MatchNotFoundException;
import de.prog3.projektarbeit.utils.Formatter;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import static de.prog3.projektarbeit.data.jooq.tables.Team.TEAM;
/**
 * /Diese Klasse enthält Abfragen für die Verwaltung von Matches in der Datenbank.
 */
public class MatchQuery {

    private static final Logger logger = LoggerFactory.getLogger(MatchQuery.class);

    /**
     * Speichert ein Match in der Datenbank.
     * @param match Das Match, das gespeichert werden soll.
     */
    public static void save(Match match, int tournamentId) {
        logger.info("Speichere Match: {}", match);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        ctx.insertInto(MATCH)
                .set(MATCH.HOME_TEAM_ID, match.getHomeTeam().getId())
                .set(MATCH.AWAY_TEAM_ID, match.getAwayTeam().getId())
                .set(MATCH.TOURNAMENT_ID,  tournamentId)
                .set(MATCH.MATCH_DATE, Formatter.parseDateToString(match.getDate()))
                .execute();
    }

    public static void clearMatches(int tournamentId) {
        logger.info("Lösche Matches für Turnier mit ID: {}", tournamentId);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        ctx.deleteFrom(MATCH)
                .where(MATCH.TOURNAMENT_ID.eq(tournamentId))
                .execute();
    }
}