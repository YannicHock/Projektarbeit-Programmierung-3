package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.exceptions.TeamNotFoundExeption;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.util.HashMap;

import static de.prog3.projektarbeit.data.jooq.tables.Player.PLAYER;
import static de.prog3.projektarbeit.data.jooq.tables.Team.TEAM;

public class TeamFactory {

    private String name;

    public Team build() throws IllegalArgumentException {
        if(name == null || name.isEmpty() || name.isBlank()){
            throw new IllegalArgumentException("Fehlender Vorname");
        }

        Team team = new Team(name);
        return team;
    }

    public TeamFactory setName(String name) {
        this.name = name;
        return this;
    }

    public static HashMap<Integer, Team> getAllWithoutPlayers() {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record3<Integer, String, Integer>> result = ctx.select(TEAM.ID, TEAM.NAME, DSL.count(PLAYER.TEAM_ID))
                .from(TEAM)
                .leftJoin(PLAYER).on(PLAYER.TEAM_ID.eq(TEAM.ID))
                .groupBy(TEAM.ID)
                .fetch();
        HashMap<Integer, Team> teams = new HashMap<>();
        for (Record record : result) {
            Team team = new Team(record.get(TEAM.ID), record.get(TEAM.NAME), record.get(DSL.count(PLAYER.TEAM_ID)));
            teams.put(team.getId(), team);
        }
        return teams;
    }

    public static Team getTeamById(int id) throws TeamNotFoundExeption {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(TEAM).leftJoin(PLAYER).on(PLAYER.TEAM_ID.eq(TEAM.ID)).where(TEAM.ID.eq(id)).fetch();
        Team team = null;
        for(Record r : result){
            if(team == null) {
                team = new Team(r.get(TEAM.ID), r.get(TEAM.NAME));
            }
            PlayerFactory.extractPlayerFromRecord(r).ifPresent(team::addPlayer);
        }
        return team;
    }

    public static String getNameById(int id) throws TeamNotFoundExeption {
        if(id == 0){
            return "Vereinslos";
        } else {
            DSLContext ctx = JooqContextProvider.getDSLContext();
            Record result = ctx.select().from(TEAM).where(TEAM.ID.eq(id)).fetchOne();
            if(result == null) {
                throw new TeamNotFoundExeption("Team mit der ID " + id + " nicht gefunden");
            }
            return result.get(TEAM.NAME);
        }
    }

}

