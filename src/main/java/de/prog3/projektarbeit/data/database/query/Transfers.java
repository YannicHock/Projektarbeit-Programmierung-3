package de.prog3.projektarbeit.data.database.query;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.data.objects.TransferHistory;
import org.jooq.DSLContext;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import static de.prog3.projektarbeit.data.jooq.tables.Team.TEAM;
import static de.prog3.projektarbeit.data.jooq.tables.Transfer.TRANSFER;
import static org.jooq.impl.DSL.coalesce;

public class Transfers {

    public static ArrayList<TransferHistory.Transfer> getTransfers(Player player) {
        ArrayList<TransferHistory.Transfer> transfers = new ArrayList<>();
        DSLContext ctx = JooqContextProvider.getDSLContext();
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
                        Date date = Player.parseStringToDate(record.get(TRANSFER.DATE));
                        TransferHistory.Transfer transfer = new TransferHistory.Transfer(
                                record.get(TRANSFER.ID),
                                record.get("fromTeamName", String.class),
                                record.get("toTeamName", String.class),
                                record.get(TRANSFER.AMOUNT),
                                date
                        );
                        transfers.add(transfer);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
        transfers.sort(Comparator.comparing(TransferHistory.Transfer::date));
        return transfers;
    }
}
