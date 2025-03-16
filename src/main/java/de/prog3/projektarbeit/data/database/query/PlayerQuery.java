package de.prog3.projektarbeit.data.database.query;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.jooq.tables.records.PlayerRecord;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.exceptions.PlayerNotFoundExeption;
import de.prog3.projektarbeit.exceptions.UnableToSavePlayerExeption;
import de.prog3.projektarbeit.utils.Parser;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static de.prog3.projektarbeit.data.jooq.tables.Player.PLAYER;
import static de.prog3.projektarbeit.data.jooq.tables.Positions.POSITIONS;

public class PlayerQuery {

    private static final Logger logger = LoggerFactory.getLogger(PlayerQuery.class);

    public static void save(Player player) throws UnableToSavePlayerExeption {
        try {
            DSLContext ctx = JooqContextProvider.getDSLContext();
            if (player.getId() == 0) {
                logger.info("Speichere neuen Spieler: {}", player.getFullName());
                ctx.insertInto(PLAYER)
                        .columns(PLAYER.FIRSTNAME, PLAYER.LASTNAME, PLAYER.DATEOFBIRTH, PLAYER.NUMBER)
                        .values(player.getFirstName(), player.getLastName(), Parser.parseDateToString(player.getDateOfBirth()), player.getNumber())
                        .onDuplicateKeyUpdate()
                        .set(PLAYER.FIRSTNAME, player.getFirstName())
                        .set(PLAYER.LASTNAME, player.getLastName())
                        .set(PLAYER.DATEOFBIRTH, Parser.parseDateToString(player.getDateOfBirth()))
                        .set(PLAYER.NUMBER, player.getNumber())
                        .set(PLAYER.TEAM_ID, player.getTeamId())
                        .execute();
                player.setId(ctx.lastID().intValue());
            } else {
                logger.info("Aktualisiere Spieler: {}", player.getFullName());
                ctx.update(PLAYER)
                        .set(PLAYER.FIRSTNAME, player.getFirstName())
                        .set(PLAYER.LASTNAME, player.getLastName())
                        .set(PLAYER.DATEOFBIRTH, Parser.parseDateToString(player.getDateOfBirth()))
                        .set(PLAYER.NUMBER, player.getNumber())
                        .set(PLAYER.TEAM_ID, player.getTeamId())
                        .where(PLAYER.ID.eq(player.getId()))
                        .execute();
            }
            updatePlayerPositions(ctx, player);
        } catch (ParseException e) {
            logger.error("Fehler beim Speichern des Spielers: {}", player.getFullName(), e);
            throw new UnableToSavePlayerExeption("Player {" + player.getFullName() + " (" + player.getId() + ")} could not be saved");
        }
    }

    private static void updatePlayerPositions(DSLContext ctx, Player player) {
        logger.info("Aktualisiere Positionen für Spieler: {}", player.getFullName());
        ArrayList<Position> oldPositions = getPlayerPositions(ctx, player.getId());
        ArrayList<Position> added = new ArrayList<>(player.getPositions());
        ArrayList<Position> removed = new ArrayList<>(oldPositions);
        added.removeAll(removed);
        removed.removeAll(player.getPositions());

        removed.forEach(position -> {
            logger.debug("Entferne Position: {} für Spieler: {}", position.name(), player.getFullName());
            ctx.deleteFrom(POSITIONS)
                    .where(POSITIONS.PLAYERID.eq(player.getId()))
                    .and(POSITIONS.POSITION.eq(position.name()))
                    .execute();
        });

        added.forEach(position -> {
            logger.debug("Füge Position: {} für Spieler: {}", position.name(), player.getFullName());
            ctx.insertInto(POSITIONS)
                    .columns(POSITIONS.PLAYERID, POSITIONS.POSITION)
                    .values(player.getId(), position.name())
                    .execute();
        });
    }

    private static ArrayList<Position> getPlayerPositions(DSLContext ctx, int playerId) {
        logger.debug("Lade Positionen für Spieler mit ID: {}", playerId);
        Result<Record> positions = ctx.select().from(POSITIONS).where(POSITIONS.PLAYERID.eq(playerId)).fetch();
        ArrayList<Position> playerPositions = new ArrayList<>();
        positions.forEach(r -> playerPositions.add(Position.valueOf(r.get(POSITIONS.POSITION))));
        return playerPositions;
    }

    public static Optional<Player> extractPlayerFromRecord(Record record) {
        if (record.get(PLAYER.ID) == null) return Optional.empty();
        try {
            Player player = new Player(record.get(PLAYER.ID), record.get(PLAYER.FIRSTNAME), record.get(PLAYER.LASTNAME),
                    Parser.parseStringToDate(record.get(PLAYER.DATEOFBIRTH)), record.get(PLAYER.NUMBER),
                    getPlayerPositions(JooqContextProvider.getDSLContext(), record.get(PLAYER.ID)), record.get(PLAYER.TEAM_ID));
            logger.debug("Extrahiere Spieler aus Datensatz: {}", player.getFullName());
            return Optional.of(player);
        } catch (ParseException e) {
            logger.error("Fehler beim Parsen des Geburtsdatums für Spieler: {}", record.get(PLAYER.FIRSTNAME) + " " + record.get(PLAYER.LASTNAME), e);
            return Optional.empty();
        }
    }

    public static Player getPlayerById(int id) throws PlayerNotFoundExeption {
        logger.info("Lade Spieler mit ID: {}", id);
        DSLContext ctx = JooqContextProvider.getDSLContext();
        PlayerRecord record = ctx.selectFrom(PLAYER).where(PLAYER.ID.eq(id)).fetchOneInto(PlayerRecord.class);
        if (record == null) {
            logger.warn("Spieler mit ID {} nicht gefunden", id);
            throw new PlayerNotFoundExeption("Player with ID " + id + " not found");
        }
        try {
            return new Player(record.getId(), record.getFirstname(), record.getLastname(),
                    Parser.parseStringToDate(record.getDateofbirth()), record.getNumber(),
                    getPlayerPositions(ctx, record.getId()), Optional.ofNullable(record.getTeamId()).orElse(0));
        } catch (ParseException e) {
            logger.error("Fehler beim Parsen des Geburtsdatums für Spieler mit ID: {}", id, e);
            throw new PlayerNotFoundExeption("Player with ID " + id + " not found");
        }
    }

    public static HashMap<Integer, Player> getFreeAgents() {
        logger.info("Lade alle vereinslosen Spieler");
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<PlayerRecord> result = ctx.selectFrom(PLAYER).where(PLAYER.TEAM_ID.eq(0).or(PLAYER.TEAM_ID.isNull())).fetch();
        HashMap<Integer, Player> players = new HashMap<>();
        result.forEach(r -> {
            PlayerRecord record = r.into(PlayerRecord.class);
            try {
                Player player = new Player(record.getId(), record.getFirstname(), record.getLastname(),
                        Parser.parseStringToDate(record.getDateofbirth()), record.getNumber(),
                        getPlayerPositions(ctx, record.getId()), Optional.ofNullable(record.getTeamId()).orElse(0));
                players.put(player.getId(), player);
                logger.debug("Gefundener vereinsloser Spieler: {}", player.getFullName());
            } catch (ParseException e) {
                logger.error("Fehler beim Parsen des Geburtsdatums für Spieler: {}", record.getFirstname() + " " + record.getLastname(), e);
                throw new RuntimeException(e);
            }
        });
        logger.info("Gefundene vereinslose Spieler: {}", players.size());
        return players;
    }
}