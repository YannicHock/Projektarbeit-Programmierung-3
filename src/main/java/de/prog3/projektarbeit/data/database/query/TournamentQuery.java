package de.prog3.projektarbeit.data.database.query;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.Match;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.data.objects.Tournament;
import de.prog3.projektarbeit.exceptions.TeamNotFoundExeption;
import de.prog3.projektarbeit.exceptions.TournamentNotFoundException;
import de.prog3.projektarbeit.utils.Formatter;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static de.prog3.projektarbeit.data.jooq.tables.Match.MATCH;
import static de.prog3.projektarbeit.data.jooq.tables.Team.TEAM;
import static de.prog3.projektarbeit.data.jooq.tables.Tournament.TOURNAMENT;

public class TournamentQuery {
    private static final Logger logger = LoggerFactory.getLogger(TournamentQuery.class);

    public static ArrayList<Tournament> getAll() {
        logger.info("Lade alle Turniere");
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(TOURNAMENT).fetch();
        ArrayList<Tournament> tournaments = new ArrayList<>();
        result.forEach(record -> {
            tournaments.add(new Tournament(record.get(TOURNAMENT.ID), record.get(TOURNAMENT.NAME)));
        });
        logger.info("Gefundene Turniere: {}", tournaments.size());
        return tournaments;
    }

    public static Tournament getById(int id) throws TournamentNotFoundException {
        logger.info("Lade Turnier mit ID: {}", id);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Record record = ctx.select().from(TOURNAMENT).where(TOURNAMENT.ID.eq(id)).fetchOne();

        ArrayList<Team> teams = new ArrayList<>();
        Result<Record> teamRecords = ctx.select().from(TEAM).where(TEAM.LEAGUEID.eq(id)).fetch();
        teamRecords.forEach(teamRecord -> {
            teams.add(new Team(teamRecord.get(TEAM.NAME), teamRecord.get(TEAM.LEAGUEID)));
        });

        ArrayList<Match> matches = new ArrayList<>();
        Result<Record> matchRecords = ctx.select().from(MATCH).where(MATCH.TOURNAMENT_ID.eq(id)).fetch();
        matchRecords.forEach(matchRecord -> {
            try {
                matches.add(new Match(
                        TeamQuery.getTeamById(matchRecord.get(MATCH.HOME_TEAM_ID)),
                        TeamQuery.getTeamById(matchRecord.get(MATCH.AWAY_TEAM_ID)),
                        Formatter.parseStringToDate(matchRecord.get(MATCH.MATCH_DATE))
                ));
            } catch (TeamNotFoundExeption | ParseException e) {
                throw new RuntimeException(e);
            }
        });

        if (record == null) {
            throw new TournamentNotFoundException("Turnier mit ID " + id + " nicht gefunden");
        }
        return new Tournament(record.get(TOURNAMENT.ID), record.get(TOURNAMENT.NAME), teams, matches);
    }


    private void addTeamToTournament(int tournamentId, int teamId) {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        ctx.insertInto(TEAM)
                .set(TEAM.ID, tournamentId)
                .set(TEAM.ID, teamId)
                .execute();
    }


    public void saveTournamentWithMatches(Tournament tournament) {
        logger.info("Speichere Turnier: {}", tournament.getName());
        DSLContext ctx = JooqContextProvider.getDSLContext();
        ctx.transaction(configuration -> {
            DSLContext transactionalCtx = DSL.using(configuration);
            transactionalCtx.insertInto(TOURNAMENT)
                    .set(TOURNAMENT.NAME, tournament.getName())
                    .execute();

            // Ermittle die generierte ID für das neue Turnier
            int tournamentId = transactionalCtx.select(TOURNAMENT.ID)
                    .from(TOURNAMENT)
                    .where(TOURNAMENT.NAME.eq(tournament.getName()))
                    .fetchOne()
                    .get(TOURNAMENT.ID);

            // Speicher die Teams des Turniers
            for (Team team : tournament.getTeams()) {
                addTeamToTournament(tournamentId, team.getId());
            }
            for (de.prog3.projektarbeit.data.objects.Match match : tournament.getMatches()) {
                transactionalCtx.insertInto(MATCH)
                        .set(MATCH.HOME_TEAM_ID, match.getHomeTeam().getId())
                        .set(MATCH.AWAY_TEAM_ID, match.getAwayTeam().getId())
                        .set(MATCH.TOURNAMENT_ID, tournamentId)
                        .set(MATCH.MATCH_DATE, Formatter.parseDateToString(match.getDate()))
                        .execute();
            }
        });
    }


}