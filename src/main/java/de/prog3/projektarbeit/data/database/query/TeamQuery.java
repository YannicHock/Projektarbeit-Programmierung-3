package de.prog3.projektarbeit.data.database.query;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.exceptions.TeamNotFoundExeption;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

import static de.prog3.projektarbeit.data.jooq.tables.Player.PLAYER;
import static de.prog3.projektarbeit.data.jooq.tables.Team.TEAM;
/**
 * Diese Klasse enthält Abfragen für die Verwaltung von Teams in der Datenbank.
 * Sie bietet Methoden zum Abrufen aller Teams, Abrufen eines Teams nach ID,
 * und Abrufen des Namens eines Teams nach ID.
 */

public class TeamQuery {

    private static final Logger logger = LoggerFactory.getLogger(TeamQuery.class);

    public static HashMap<Integer, Team> getAll() {
        logger.info("Lade alle Teams");
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record4<Integer, Integer, String, Integer>> result = ctx.select(TEAM.ID, TEAM.LEAGUEID, TEAM.NAME, DSL.count(PLAYER.TEAM_ID))
                .from(TEAM)
                .leftJoin(PLAYER).on(PLAYER.TEAM_ID.eq(TEAM.ID))
                .groupBy(TEAM.ID)
                .fetch();
        HashMap<Integer, Team> teams = new HashMap<>();
        result.forEach(record -> {
            Team team = new Team(record.get(TEAM.ID), record.get(TEAM.NAME), record.get(TEAM.LEAGUEID), record.get(DSL.count(PLAYER.TEAM_ID)));
            teams.put(team.getId(), team);
            logger.debug("Gefundenes Team: {} mit ID: {}", team.getName(), team.getId());
        });
        logger.info("Gefundene Teams: {}", teams.size());
        return teams;
    }

    public static Team getTeamById(int id) throws TeamNotFoundExeption {
        logger.info("Lade Team mit ID: {}", id);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(TEAM).leftJoin(PLAYER).on(PLAYER.TEAM_ID.eq(TEAM.ID)).where(TEAM.ID.eq(id)).fetch();
        Team team = null;
        for (Record r : result) {
            if (team == null) {
                team = new Team(r.get(TEAM.ID), r.get(TEAM.NAME), r.get(TEAM.LEAGUEID));
                logger.info("Gefundenes Team: {} mit ID: {}", team.getName(), team.getId());
            }
            Team finalTeam = team;
            PlayerQuery.extractPlayerFromRecord(r).ifPresent(player -> {
                finalTeam.addPlayer(player);
                logger.debug("Füge Spieler: {} zum Team: {}", player.getFullName(), finalTeam.getName());
            });
        }
        if (team == null) {
            logger.warn("Team mit ID {} nicht gefunden", id);
            throw new TeamNotFoundExeption("Team mit der ID " + id + " nicht gefunden");
        }
        return team;
    }

    public static void save(Team team) {
        DSLContext ctx = JooqContextProvider.getDSLContext();

        System.out.println(team.getLeagueId());
        ctx.insertInto(TEAM)
                .columns(TEAM.NAME, TEAM.LEAGUEID)
                .values(team.getName(), team.getLeagueId())
                .onDuplicateKeyUpdate()
                .set(TEAM.NAME, team.getName())
                .set(TEAM.LEAGUEID, team.getLeagueId())
                .execute();
        ctx.select(TEAM.ID).from(TEAM).where(TEAM.NAME.eq(team.getName())).fetch().forEach(record -> team.setId(record.get(TEAM.ID)));
    }

    public static ArrayList<Team> getAllTeamsInLeague(int leagueId) {
        logger.info("Lade alle Teams in Liga mit ID: {}", leagueId);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(TEAM).where(TEAM.LEAGUEID.eq(leagueId)).fetch();
        ArrayList<Team> teams = new ArrayList<>();
        result.forEach(record -> {
            Team team = new Team(record.get(TEAM.ID), record.get(TEAM.NAME), record.get(TEAM.LEAGUEID));
            teams.add(team);
            logger.debug("Gefundenes Team: {} mit ID: {}", team.getName(), team.getId());
        });
        logger.info("Gefundene Teams: {}", teams.size());
        return teams;
    }

    public static String getNameById(int id) throws TeamNotFoundExeption {
        if (id == 0) {
            return "Vereinslos";
        } else {
            logger.info("Lade Namen des Teams mit ID: {}", id);
            DSLContext ctx = JooqContextProvider.getDSLContext();
            Record result = ctx.select().from(TEAM).where(TEAM.ID.eq(id)).fetchOne();
            if (result == null) {
                logger.warn("Teamname für das Team mit ID {} nicht gefunden", id);
                throw new TeamNotFoundExeption("Team mit der ID " + id + " nicht gefunden");
            }
            return result.get(TEAM.NAME);
        }
    }
}