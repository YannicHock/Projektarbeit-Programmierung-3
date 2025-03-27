package de.prog3.projektarbeit.data.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Die Klasse DataSourceProvider ist verantwortlich für die Bereitstellung einer DataSource für die Anwendung.
 * Sie verwendet HikariCP für Connection-Pooling und SQLite als Datenbank.
 */

public class DataSourceProvider {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceProvider.class);
    private static HikariDataSource dataSource;
    private static final String SOURCE_PATH = "database.sqlite";
    private static final String DESTINATION_PATH = "data/db.sqlite";


    /**
     * Initialisiert den DataSourceProvider.
     * Erstellt das Verzeichnis für die Datenbankdatei, falls es nicht existiert.
     * Kopiert die Datenbankdatei aus den Ressourcen, falls sie nicht existiert.
     * Konfiguriert und initialisiert die HikariDataSource.
     */

    public static void init(){
        logger.info("Starte Initialisierung des DataSourceProviders ...");
        File file = new File(DESTINATION_PATH);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.mkdirs() && !parentDir.exists()) {
            logger.error("Erstellen des Verzeichnisses fehlgeschlagen: {}", parentDir);
        } else {
            logger.debug("Verzeichnis {} ist vorhanden oder wurde erfolgreich erstellt.", parentDir);
        }

        if(!file.exists()){
            logger.info("Datenbankdatei {} existiert nicht. Kopiere aus Ressourcen ...", DESTINATION_PATH);
            copyDatabaseFile();
        } else {
            logger.debug("Datenbankdatei {} existiert bereits.", DESTINATION_PATH);
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:" + DESTINATION_PATH);
        config.setDriverClassName("org.sqlite.JDBC");
        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
        logger.info("DataSource wurde erfolgreich initialisiert.");
    }

    /**
     * Gibt die DataSource zurück.
     * Initialisiert die DataSource, falls sie noch nicht initialisiert ist.
     *
     * @return die DataSource
     */

    public static DataSource getDataSource() {
        if (dataSource == null) {
            logger.info("DataSource noch nicht initialisiert. Führe init() aus.");
            init();
        }
        return dataSource;
    }

    /**
     * Kopiert die Datenbankdatei von den Ressourcen in das Zielverzeichnis.
     * Verwendet einen InputStream, um die Datei zu lesen und mit Files.copy zu kopieren.
     */
    private static void copyDatabaseFile() {
        ClassLoader classLoader = DataSourceProvider.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(SOURCE_PATH)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Datei nicht gefunden!");
            }
            Files.copy(inputStream, Paths.get(DESTINATION_PATH));
            logger.info("Datenbankdatei wurde erfolgreich nach {} kopiert.", DESTINATION_PATH);
        } catch (Exception e) {
            logger.error("Fehler beim Kopieren der Datenbankdatei: ", e);
        }
    }
}