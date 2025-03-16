package de.prog3.projektarbeit.data.database.query;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.jooq.tables.records.PlayerRecord;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.exceptions.PlayerNotFoundExeption;
import de.prog3.projektarbeit.exceptions.UnableToSavePlayerExeption;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static de.prog3.projektarbeit.data.jooq.tables.Player.PLAYER;
import static de.prog3.projektarbeit.data.jooq.tables.Positions.POSITIONS;

public class PlayerQuery {

    public static void save(Player player) throws UnableToSavePlayerExeption {
        try {
            DSLContext ctx = JooqContextProvider.getDSLContext();
            if(player.getId() == 0){
                ctx.insertInto(PLAYER)
                        .columns(PLAYER.FIRSTNAME, PLAYER.LASTNAME, PLAYER.DATEOFBIRTH, PLAYER.NUMBER)
                        .values(player.getFirstName(), player.getLastName(), Player.parseDateToString(player.getDateOfBirth()), player.getNumber())
                        .onDuplicateKeyUpdate()
                        .set(PLAYER.FIRSTNAME, player.getFirstName())
                        .set(PLAYER.LASTNAME, player.getLastName())
                        .set(PLAYER.DATEOFBIRTH, Player.parseDateToString(player.getDateOfBirth()))
                        .set(PLAYER.NUMBER, player.getNumber())
                        .set(PLAYER.TEAM_ID, player.getTeamId())
                        .execute();
                ctx.select(PLAYER.ID).from(PLAYER).where(PLAYER.FIRSTNAME.eq(player.getFirstName())).and(PLAYER.LASTNAME.eq(player.getLastName())).fetch().forEach(record -> player.setId(record.get(PLAYER.ID)));
            } else {
                ctx.update(PLAYER)
                        .set(PLAYER.FIRSTNAME, player.getFirstName())
                        .set(PLAYER.LASTNAME, player.getLastName())
                        .set(PLAYER.DATEOFBIRTH, Player.parseDateToString(player.getDateOfBirth()))
                        .set(PLAYER.NUMBER, player.getNumber())
                        .set(PLAYER.TEAM_ID, player.getTeamId())
                        .where(PLAYER.ID.eq(player.getId()))
                        .execute();
            }
            ArrayList<Position> oldPositions = new ArrayList<>();
            ctx.select().from(POSITIONS).where(POSITIONS.PLAYERID.eq(player.getId())).fetch().forEach(record -> oldPositions.add(Position.valueOf(record.get(POSITIONS.POSITION))));
            ArrayList<Position> added = new ArrayList<>(player.getPositions());
            ArrayList<Position> removed = new ArrayList<>(oldPositions);
            added.removeAll(removed);
            removed.removeAll(player.getPositions());

            removed.forEach(position ->
                    ctx.deleteFrom(POSITIONS)
                            .where(POSITIONS.PLAYERID.eq(player.getId())).and(POSITIONS.POSITION.eq(position.name()))
                            .execute()
            );

            added.forEach(position ->
                    ctx.insertInto(POSITIONS)
                            .columns(POSITIONS.PLAYERID, POSITIONS.POSITION)
                            .values(player.getId(), position.name())
                            .execute()
            );
        } catch (ParseException e){
            throw new UnableToSavePlayerExeption("Player {" + player.getFullName() + " (" + player.getId() + ")} could not be saved");
        }
    }

    private static ArrayList<Position> getPlayerPositions(int playerId) {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<org.jooq.Record> positions = ctx.select().from(POSITIONS).where(POSITIONS.PLAYERID.eq(playerId)).fetch();
        ArrayList<Position> playerPositions = new ArrayList<>();
        if (positions.isEmpty()) {
            return playerPositions;
        }
        for (Record r : positions) {
            playerPositions.add(Position.valueOf(r.get(POSITIONS.POSITION)));
        }
        return playerPositions;
    }


    public static Optional<Player> extractPlayerFromRecord(Record record) {
        Player player = null;
        if (record.get(PLAYER.ID) != null) {
            try {
                player = new Player(record.get(PLAYER.ID), record.get(PLAYER.FIRSTNAME), record.get(PLAYER.LASTNAME), Player.parseStringToDate(record.get(PLAYER.DATEOFBIRTH)), record.get(PLAYER.NUMBER), getPlayerPositions(record.get(PLAYER.ID)), record.get(PLAYER.TEAM_ID));
            } catch (ParseException e) {
                System.err.println("Fehler beim Parsen des Geburtsdatums: " + e);
            }
        }
        return Optional.ofNullable(player);
    }



    public static Player getPlayerById(int id) throws PlayerNotFoundExeption {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        PlayerRecord record = (PlayerRecord) ctx.select().from(PLAYER).where(PLAYER.ID.eq(id)).fetchOne();
        Player player = null;
        try {
            if (record == null) {
                throw new PlayerNotFoundExeption("Spieler mit der ID " + id + " nicht gefunden");
            }
            Optional<Integer> teamId = Optional.ofNullable(record.getTeamId());
            player = new Player(record.getId(), record.getFirstname(), record.getLastname(), Player.parseStringToDate(record.getDateofbirth()), record.getNumber(), getPlayerPositions(record.getId()), teamId.orElse(0));
        } catch (ParseException e) {
            throw new PlayerNotFoundExeption("Spieler mit der ID " + id + " nicht gefunden");
        }
        return player;
    }

    public static HashMap<Integer, Player> getFreeAgents() {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(PLAYER).where(PLAYER.TEAM_ID.eq(0).or(PLAYER.TEAM_ID.isNull())).fetch();
        HashMap<Integer, Player> players = new HashMap<>();
        for (Record r : result) {
            PlayerRecord record = (PlayerRecord) r;
            try {
                Optional<Integer> teamId = Optional.ofNullable(record.getTeamId());
                Player player = new Player(record.getId(), record.getFirstname(), record.getLastname(), Player.parseStringToDate(record.getDateofbirth()), record.getNumber(), getPlayerPositions(((PlayerRecord) r).getId()), teamId.orElse(0));
                players.put(player.getId(), player);
            } catch (ParseException e) {
                System.err.println("Fehler beim Parsen des Geburtsdatums: " + e);
                throw new RuntimeException(e);
            }
        }
        return players;
    }
}
