package de.prog3.projektarbeit.data.database;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import javax.sql.DataSource;
import org.jooq.SQLDialect;

/**
 * Bietet eine Singleton-Instanz von DSLContext für die Interaktion mit der Datenbank mittels jOOQ.
 */
public class JooqContextProvider {

    private static DSLContext dslContext;

    /**
     * Initialisiert den DSLContext mit dem von DataSourceProvider bereitgestellten DataSource.
     * Diese Methode sollte einmalig beim Start der Anwendung aufgerufen werden.
     */
    public static void init(){
        DataSourceProvider.init();
        DataSource ds = DataSourceProvider.getDataSource();

        dslContext = DSL.using(ds, SQLDialect.SQLITE);
    }

    /**
     * Gibt die Singleton-Instanz von DSLContext zurück.
     * Falls der DSLContext nicht initialisiert ist, wird er mit dem von DataSourceProvider bereitgestellten DataSource initialisiert.
     *
     * @return die Singleton-Instanz von DSLContext
     */
    public static DSLContext getDSLContext() {
        if (dslContext == null) {
            DataSource ds = DataSourceProvider.getDataSource();

            dslContext = DSL.using(ds, SQLDialect.SQLITE);
        }
        return dslContext;
    }
}