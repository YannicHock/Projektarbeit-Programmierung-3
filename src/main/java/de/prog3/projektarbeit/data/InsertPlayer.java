package de.prog3.projektarbeit.data;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.field;

public class InsertPlayer {

    public static void main(String[] args) {
        DSLContext ctx = JooqContextProvider.getDSLContext();

        // Insert als "Query Builder"
        ctx.insertInto(table("players"))
                .columns(field("id"),field("name"), field("position"))
                .values("001","Mathusan Saravanapavan",  "Stürmer")
                .execute();

        System.out.println("Spieler hinzugefügt!");

        // Zur Kontrolle: alle Datensätze abfragen
        Result<Record> result = ctx.select().from("players").fetch();
        System.out.println("Anzahl Datensätze: " + result.size());
        for (Record r : result) {
            System.out.println("Datensatz: " + r);
        }
    }
}
