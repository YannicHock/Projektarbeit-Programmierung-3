package de.prog3.projektarbeit.data.database.query;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.data.objects.Transfer;
import de.prog3.projektarbeit.utils.Formatter;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import static de.prog3.projektarbeit.data.jooq.tables.Team.TEAM;
import static de.prog3.projektarbeit.data.jooq.tables.Transfer.TRANSFER;
import static org.jooq.impl.DSL.coalesce;
/**
 * Diese Klasse enthält Abfragen für die Verwaltung von Transfers in der Datenbank.
 * Sie bietet Methoden zum Abrufen und Hinzufügen von Transfers für Spieler.
 */
public class TransferQuery {

    private static final Logger logger = LoggerFactory.getLogger(TransferQuery.class);

    public static ArrayList<Transfer> getTransfers(Player player) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        DSLContext ctx = JooqContextProvider.getDSLContext();
        logger.info("Lade Transfers für Spieler mit ID: {}", player.getId());
        ctx.select(TRANSFER.ID,
                        coalesce(TEAM.as("fromTeam").NAME, "Jugend").as("fromTeamName"),
                        coalesce(TEAM.as("toTeam").NAME, "Karriere Ende").as("toTeamName"),
                        coalesce(TRANSFER.AMOUNT, 0).as(TRANSFER.AMOUNT),
                        TRANSFER.DATE)
                .from(TRANSFER)
                .leftJoin(TEAM.as("fromTeam")).on(TRANSFER.OLDTEAMID.eq(TEAM.as("fromTeam").ID))
                .leftJoin(TEAM.as("toTeam")).on(TRANSFER.NEWTEAMID.eq(TEAM.as("toTeam").ID))
                .where(TRANSFER.PLAYERID.eq(player.getId()))
                .fetch().forEach(record -> {
                    try {
                        Date date = Formatter.parseStringToDate(record.get(TRANSFER.DATE));
                        transfers.add(new Transfer(
                                record.get(TRANSFER.ID),
                                record.get("fromTeamName", String.class),
                                record.get("toTeamName", String.class),
                                record.get(TRANSFER.AMOUNT),
                                date
                        ));
                        logger.info("Transfer hinzugefügt: {} von {} zu {} am {}",
                                record.get(TRANSFER.ID),
                                record.get("fromTeamName", String.class),
                                record.get("toTeamName", String.class),
                                record.get(TRANSFER.DATE));
                    } catch (ParseException e) {
                        logger.error("Fehler beim Parsen des Datums: {}", record.get(TRANSFER.DATE), e);
                        throw new RuntimeException(e);
                    }
                });
        transfers.sort(Comparator.comparing(Transfer::getDate));
        logger.info("Transfers für Spieler mit ID: {} erfolgreich geladen", player.getId());
        return transfers;
    }

    public static void addTransfer(Transfer transfer) throws ParseException, IntegrityConstraintViolationException {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        try {
            logger.info("Füge Transfer für Spieler mit ID: {} hinzu", transfer.getPlayerId());
            ctx.insertInto(TRANSFER)
                    .set(TRANSFER.PLAYERID, transfer.getPlayerId())
                    .set(TRANSFER.OLDTEAMID, transfer.getFromTeamId())
                    .set(TRANSFER.NEWTEAMID, transfer.getToTeamId())
                    .set(TRANSFER.AMOUNT, transfer.getAmount())
                    .set(TRANSFER.DATE, transfer.getDateString())
                    .execute();
            logger.info("Transfer für Spieler mit ID: {} erfolgreich hinzugefügt", transfer.getPlayerId());
        } catch (DataAccessException e){
            if(e.getCause().getMessage().contains("UNIQUE constraint failed: Player.team_id, Player.number")){
                throw new IntegrityConstraintViolationException("Das Team mit der ID: " + transfer.getToTeamId() + " hat bereits einen Spieler mit dieser Rückennumer");
            } else {
                logger.error("Fehler beim Hinzufügen des Transfers für Spieler mit ID: {}", transfer.getPlayerId(), e);
                throw new DataException("Fehler beim Hinzufügen des Transfers für Spieler mit ID: " + transfer.getPlayerId(), e);
            }
        }
    }
}