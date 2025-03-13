package de.prog3.projektarbeit.eventHandling.events.data.player;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.Event;

import java.util.ArrayList;


public class AttemptPlayerUpdateEvent extends Event {
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String number;
    private final Player player;
    private final ArrayList<Position> positions;


    public AttemptPlayerUpdateEvent(Player player, String firstName, String lastName, String dateOfBirth, String number, ArrayList<Position> positions) {
        super("AttemptPlayerUpdateEvent");
        this.player = player;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.number = number;
        this.positions = positions;
    }

    public Player getPlayer() {
        return player;
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

    public ArrayList<Position> getPositions() {
        return positions;
    }
}
