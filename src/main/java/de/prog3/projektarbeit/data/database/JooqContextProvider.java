package de.prog3.projektarbeit.data.database;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.SQLDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * Bietet eine Singleton-Instanz von DSLContext für die Interaktion mit der Datenbank mittels jOOQ.
 */
public class JooqContextProvider {

    private static final Logger logger = LoggerFactory.getLogger(JooqContextProvider.class);
    private static DSLContext dslContext;

    /**
     * Initialisiert den DSLContext mit dem von DataSourceProvider bereitgestellten DataSource.
     * Diese Methode sollte einmalig beim Start der Anwendung aufgerufen werden.
     */
    public static void init(){
        logger.info("Starte Initialisierung des JooqContextProviders ...");
        DataSourceProvider.init();
        DataSource ds = DataSourceProvider.getDataSource();

        dslContext = DSL.using(ds, SQLDialect.SQLITE);
        logger.info("DSLContext wurde erfolgreich initialisiert.");
    }

    /**
     * Gibt die Singleton-Instanz von DSLContext zurück.
     * Falls der DSLContext nicht initialisiert ist, wird er mit dem von DataSourceProvider bereitgestellten DataSource initialisiert.
     *
     * @return die Singleton-Instanz von DSLContext
     */
    public static DSLContext getDSLContext() {
        if (dslContext == null) {
            logger.warn("DSLContext war noch nicht initialisiert. Initialisiere nun ...");
            DataSource ds = DataSourceProvider.getDataSource();
            dslContext = DSL.using(ds, SQLDialect.SQLITE);
            logger.info("DSLContext wurde im getDSLContext() erfolgreich initialisiert.");
        }
        return dslContext;
    }
}