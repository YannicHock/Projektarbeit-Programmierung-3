package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.Team;
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

}

