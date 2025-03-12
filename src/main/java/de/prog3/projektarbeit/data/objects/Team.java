package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.data.DataObject;
import de.prog3.projektarbeit.data.JooqContextProvider;
import de.prog3.projektarbeit.exceptions.UnableToSaveTeamExeption;
import org.jooq.DSLContext;

import java.util.HashMap;

import static de.prog3.projektarbeit.data.jooq.tables.Team.TEAM;


public class Team extends DataObject {
    private final String name;
    private HashMap<Integer, Player> players;
    private int id;

    public Team(String name) {
        super();
        this.name = name;
        players = new HashMap<>();
    }

    public Team(int id, String name) {
        this(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }


    public String getName() {
        return name;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public void save() throws UnableToSaveTeamExeption {
        DSLContext ctx = JooqContextProvider.getDSLContext();

        ctx.insertInto(TEAM)
                .columns(TEAM.NAME)
                .values(this.name)
                .onDuplicateKeyUpdate()
                .set(TEAM.NAME, this.name)
                .execute();
        ctx.select(TEAM.ID).from(TEAM).where(TEAM.NAME.eq(this.name)).fetch().forEach(record -> this.id = record.get(TEAM.ID));
    }

    public void setPlayers(HashMap<Integer, Player> players) {
        this.players = players;
    }

    @Override
    public void registerListener() {}
}