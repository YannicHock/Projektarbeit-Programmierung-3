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

public class MatchQuery {

    private static final Logger logger = LoggerFactory.getLogger(MatchQuery.class);

    public static List<Match> getAllMatches() {
        logger.info("Lade alle Matches");
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(MATCH).fetch();

        List<Match> matches = new ArrayList<>();
        result.forEach(record -> {
            Team homeTeam = getTeamById(ctx, record.get(MATCH.HOME_TEAM_ID));
            Team awayTeam = getTeamById(ctx, record.get(MATCH.AWAY_TEAM_ID));
            Match match = null;
            try {
                match = new Match(homeTeam, awayTeam, Formatter.parseStringToDate(record.get(MATCH.MATCH_DATE)));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            matches.add(match);
            logger.debug("Gefundenes Match: {} - {}", homeTeam.getName(), awayTeam.getName());
        });
        logger.info("Gefundene Matches: {}", matches.size());
        return matches;
    }

    public static Match getMatchById(int id) throws MatchNotFoundException, ParseException {
        logger.info("Lade Match mit ID: {}", id);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Record record = ctx.select().from(MATCH).where(MATCH.ID.eq(id)).fetchOne();

        if (record == null) {
            logger.warn("Match mit ID {} nicht gefunden", id);
            throw new MatchNotFoundException("Match mit der ID " + id + " nicht gefunden");
        }

        Team homeTeam = getTeamById(ctx, record.get(MATCH.HOME_TEAM_ID));
        Team awayTeam = getTeamById(ctx, record.get(MATCH.AWAY_TEAM_ID));
        return new Match(homeTeam, awayTeam, Formatter.parseStringToDate(record.get(MATCH.MATCH_DATE)));
    }

    public static void createMatch(Match match) {
        logger.info("Erstelle neues Match: {}", match);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        ctx.insertInto(MATCH)
                .set(MATCH.HOME_TEAM_ID, match.getHomeTeam().getId())
                .set(MATCH.AWAY_TEAM_ID, match.getAwayTeam().getId())
                .execute();
    }

    public static void updateMatch(int id, Match match) throws MatchNotFoundException {
        logger.info("Aktualisiere Match mit ID: {}", id);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        int affectedRows = ctx.update(MATCH)
                .set(MATCH.HOME_TEAM_ID, match.getHomeTeam().getId())
                .set(MATCH.AWAY_TEAM_ID, match.getAwayTeam().getId())
                .where(MATCH.ID.eq(id))
                .execute();

        if (affectedRows == 0) {
            logger.warn("Match mit ID {} nicht gefunden", id);
            throw new MatchNotFoundException("Match mit der ID " + id + " nicht gefunden");
        }
    }

    public static void deleteMatch(int id) throws MatchNotFoundException {
        logger.info("Lösche Match mit ID: {}", id);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        int affectedRows = ctx.deleteFrom(MATCH).where(MATCH.ID.eq(id)).execute();

        if (affectedRows == 0) {
            logger.warn("Match mit ID {} nicht gefunden", id);
            throw new MatchNotFoundException("Match mit der ID " + id + " nicht gefunden");
        }
    }

    private static Team getTeamById(DSLContext ctx, int teamId) {
        Record teamRecord = ctx.select().from(TEAM).where(TEAM.ID.eq(teamId)).fetchOne();
        return teamRecord == null ? null : new Team(teamRecord.get(TEAM.ID), teamRecord.get(TEAM.NAME));
    }
}