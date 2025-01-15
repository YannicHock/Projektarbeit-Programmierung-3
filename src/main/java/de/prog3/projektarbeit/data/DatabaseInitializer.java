package de.prog3.projektarbeit.data;

import org.jooq.DSLContext;

public class DatabaseInitializer {

    public static void main(String[] args) {

        DSLContext ctx = JooqContextProvider.getDSLContext();


        ctx.query("""
            CREATE TABLE IF NOT EXISTS spieler (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                team TEXT,
                position TEXT
            )
        """).execute();

        System.out.println("Tabelle 'spieler' erstellt (falls sie nicht existiert).");
    }
}