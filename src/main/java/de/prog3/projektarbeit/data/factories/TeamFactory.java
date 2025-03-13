package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.exceptions.TeamNotFoundExeption;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
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

    public static HashMap<Integer, Team> getAll() {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(TEAM).leftJoin(PLAYER).on(PLAYER.TEAM_ID.eq(TEAM.ID)).fetch();
        HashMap<Integer, Team> teams = new HashMap<>();
        for (Record record : result) {
            Team team;
            if (teams.containsKey(record.get(TEAM.ID))) {
                team = teams.get(record.get(TEAM.ID));
            } else {
                team = new Team(record.get(TEAM.ID), record.get(TEAM.NAME));
                teams.put(record.get(TEAM.ID), team);
            }
            PlayerFactory.extractPlayerFromRecord(record).ifPresent(team::addPlayer);
        }
        return teams;
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

