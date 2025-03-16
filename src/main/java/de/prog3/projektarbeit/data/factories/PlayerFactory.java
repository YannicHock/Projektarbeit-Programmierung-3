package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Date;

public class PlayerFactory {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int number;
    private ArrayList<Position> positions;
    private int id = 0;
    private int teamId = 0;

    public PlayerFactory setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public PlayerFactory setId(int id) {
        this.id = id;
        return this;
    }

    public PlayerFactory setTeamId(int teamId) {
        this.teamId = teamId;
        return this;
    }

    public PlayerFactory setPositions(ArrayList<Position> positions) {
        this.positions = positions;
        return this;
    }

    public PlayerFactory setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PlayerFactory setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PlayerFactory setNumber(int number) {
        this.number = number;
        return this;
    }

    public static void validDateData(String firstName, String lastName, Date dateOfBirth, int number, ArrayList<Position> positions) throws ValidationException {
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

    public Player build() throws ValidationException {
        validDateData(firstName, lastName, dateOfBirth, number, positions);
        return new Player(id, firstName, lastName, dateOfBirth, number, positions, teamId);
    }
}