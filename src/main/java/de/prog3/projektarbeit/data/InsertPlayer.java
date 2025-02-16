package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.jooq.tables.Player;
import de.prog3.projektarbeit.jooq.tables.records.PlayerRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import static de.prog3.projektarbeit.jooq.tables.Player.PLAYER;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.field;

public class InsertPlayer {
    public static void insertPlayer(){
        DSLContext ctx = JooqContextProvider.getDSLContext();

        // Insert als "Query Builder"
        ctx.insertInto(PLAYER)
                .columns(PLAYER.NAME, PLAYER.POSITION)
                .values("Mathusan Saravanapavan",  "Stürmer")
                .execute();



        System.out.println("Spieler hinzugefügt!");

        // Zur Kontrolle: alle Datensätze abfragen
        Result<Record> result = ctx.select().from(PLAYER).fetch();
        System.out.println("Anzahl Datensätze: " + result.size());
        for (Record r : result) {
            System.out.println(r.get(PLAYER.NAME) + ": " + r.get(PLAYER.POSITION));
        }
    }
}
