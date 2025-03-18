package de.prog3.projektarbeit.eventHandling.events.data.player;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.eventHandling.events.Event;

import java.util.ArrayList;


public class AttemptPlayerCreationEvent extends Event {
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String number;
    private final ArrayList<Position> positions;


    public AttemptPlayerCreationEvent(String firstName, String lastName, String dateOfBirth, String number, ArrayList<Position> positions) {
        super("AttemptPlayerCreationEvent");
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.number = number;
        this.positions = positions;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNumber() {
        return number;
    }
}
