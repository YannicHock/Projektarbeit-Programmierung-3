package de.prog3.projektarbeit.data.importer;

import de.prog3.projektarbeit.data.factories.PlayerFactory;
import de.prog3.projektarbeit.data.database.query.PlayerQuery;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.exceptions.ValidationException;
import de.prog3.projektarbeit.utils.Formatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;

public class MassImport {
    private static final Logger logger = LoggerFactory.getLogger(MassImport.class);
    private static final String IMPORT_DB_URL = "jdbc:sqlite:src/main/resources/database.sqlite";

    public void importData() {
        logger.info("Starte Massendatenimport aus externer SQLite-Datenbank: {}", IMPORT_DB_URL);

        try (Connection conn = DriverManager.getConnection(IMPORT_DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Spieler")) {

            while (rs.next()) {
                processRow(rs);
            }
        } catch (Exception e) {
            logger.error("Fehler beim Import der Daten", e);
        }
        logger.info("Massendatenimport abgeschlossen.");
    }

    private void processRow(ResultSet rs) {
        try {
            int id = rs.getInt("id");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String dateOfBirthStr = rs.getString("dateOfBirth");
            int number = rs.getInt("number");

            logger.info("Importiere Spieler: {} {} (ID: {})", firstName, lastName, id);

            Date dateOfBirth = parseDate(dateOfBirthStr);

            // Erzeuge den Spieler mithilfe der PlayerFactory
            PlayerFactory factory = new PlayerFactory()
                    .setId(id)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setDateOfBirth(dateOfBirth)
                    .setNumber(number);

            Player player = factory.build();

            PlayerQuery.save(player);
            logger.info("Spieler erfolgreich gespeichert: {} (ID: {})", player.getFirstName(), player.getId());
        } catch (ValidationException ve) {
            logger.error("Validierungsfehler beim Import eines Spielers: {}", ve.getMessage(), ve);
        } catch (Exception ex) {
            logger.error("Fehler beim Verarbeiten einer Zeile im Import", ex);
        }
    }

    private Date parseDate(String dateStr) {
        try {
            return Formatter.parseStringToDate(dateStr);
        } catch (ParseException e) {
            logger.warn("Konnte das Geburtsdatum '{}' nicht parsen. Verwende aktuelles Datum als Fallback.", dateStr, e);
            return new Date();
        }
    }

    public static void main(String[] args) {
        new MassImport().importData();
    }
}