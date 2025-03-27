package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Date;


/**
 * Die Klasse PlayerFactory ist verantwortlich für die Erstellung von Player-Objekten.
 * Sie bietet Methoden zum Setzen verschiedener Attribute eines Players und validiert die Daten vor der Erstellung des Players.
 */

public class PlayerFactory {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int number;
    private ArrayList<Position> positions;
    private int id = 0;
    private int teamId = 0;


    /**
     * Setzt das Geburtsdatum des Players.
     * @param dateOfBirth das Geburtsdatum
     * @return die PlayerFactory-Instanz
     */
    public PlayerFactory setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    /**
     * Setzt die ID des Players.
     * @param id die ID
     * @return die PlayerFactory-Instanz
     */
    public PlayerFactory setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Setzt die Team-ID des Players.
     * @param teamId die Team-ID
     * @return die PlayerFactory-Instanz
     */
    public PlayerFactory setTeamId(int teamId) {
        this.teamId = teamId;
        return this;
    }

    /**
     * Setzt die Positionen des Players.
     * @param positions die Positionen
     * @return die PlayerFactory-Instanz
     */
    public PlayerFactory setPositions(ArrayList<Position> positions) {
        this.positions = positions;
        return this;
    }

    /**
     * Setzt den Vornamen des Players.
     * @param firstName der Vorname
     * @return die PlayerFactory-Instanz
     */
    public PlayerFactory setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Setzt den Nachnamen des Players.
     * @param lastName der Nachname
     * @return die PlayerFactory-Instanz
     */
    public PlayerFactory setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Setzt die Nummer des Players.
     * @param number die Nummer
     * @return die PlayerFactory-Instanz
     */
    public PlayerFactory setNumber(int number) {
        this.number = number;
        return this;
    }

    /**
     * Validiert die Daten zur Erstellung eines Players.
     * @param firstName der Vorname
     * @param lastName der Nachname
     * @param dateOfBirth das Geburtsdatum
     * @param number die Nummer
     * @param positions die Positionen
     * @throws ValidationException wenn eine Validierung fehlschlägt
     */
    public static void validateData(String firstName, String lastName, Date dateOfBirth, int number, ArrayList<Position> positions) throws ValidationException {
        ArrayList<Exception> exceptions = new ArrayList<>();
        if (firstName == null || firstName.isBlank()) {
            exceptions.add(new IllegalArgumentException("Fehlender Vorname"));
        }
        if (lastName == null || lastName.isBlank()) {
            exceptions.add(new IllegalArgumentException("Fehlender Nachname"));
        }
        if (dateOfBirth == null) {
            exceptions.add(new IllegalArgumentException("Fehlender Geburtstag"));
        }
        if (number <= 0 || number > 99) {
            exceptions.add(new IllegalArgumentException("Spielernummer muss größer als 0 und kleiner als 100 sein"));
        }
        if (positions == null || positions.isEmpty()) {
            exceptions.add(new IllegalArgumentException("Spieler muss mindestens eine Position haben"));
        }
        if (!exceptions.isEmpty()) {
            throw new ValidationException(exceptions);
        }
    }

    /**
     * Erstellt und gibt ein Player-Objekt zurück.
     * @return das Player-Objekt
     * @throws ValidationException wenn eine Validierung fehlschlägt
     */
    public Player build() throws ValidationException {
        validateData(firstName, lastName, dateOfBirth, number, positions);
        return new Player(id, firstName, lastName, dateOfBirth, number, positions, teamId);
    }
}