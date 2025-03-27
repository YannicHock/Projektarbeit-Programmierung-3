package de.prog3.projektarbeit.eventHandling.events.data.player;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.eventHandling.events.Event;
import de.prog3.projektarbeit.utils.Formatter;

import java.util.ArrayList;


public class AttemptPlayerUpdateEvent extends Event {
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String number;
    private final Player oldPlayer;
    private final ArrayList<Position> positions;
    private final int teamId;


    public AttemptPlayerUpdateEvent(Player player, String number, int teamId) {
        super("AttemptPlayerUpdateEvent");
        this.oldPlayer = player;
        this.teamId = teamId;
        this.firstName = null;
        this.lastName = null;
        this.dateOfBirth = null;
        this.number = number;
        this.positions = null;
    }


    public AttemptPlayerUpdateEvent(Player player, String firstName, String lastName, String dateOfBirth, String number, ArrayList<Position> positions) {
        super("AttemptPlayerUpdateEvent");
        this.oldPlayer = player;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.number = number;
        this.positions = positions;
        this.teamId = -1;
    }

    public Player getOldPlayer() {
        return oldPlayer;
    }

    public String getFirstName() {
        if(firstName == null){
            return oldPlayer.getFirstName();
        }
        return firstName;
    }

    public String getLastName() {
        if(lastName == null){
            return oldPlayer.getLastName();
        }
        return lastName;
    }

    public String getDateOfBirth() {
        if (dateOfBirth == null) {
            return Formatter.parseDateToString(oldPlayer.getDateOfBirth());
        }
        return dateOfBirth;
    }

    public String getNumber() {
        if (number == null) {
            return oldPlayer.getNumber() + "";
        }
        return number;
    }

    public ArrayList<Position> getPositions() {
        if(positions == null){
            return oldPlayer.getPositions();
        }
        return positions;
    }

    public int getTeamId() {
        if(teamId == -1){
            return oldPlayer.getTeamId();
        }
        return teamId;
    }
}
