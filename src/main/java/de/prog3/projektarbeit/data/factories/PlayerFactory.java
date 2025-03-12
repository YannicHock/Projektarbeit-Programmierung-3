package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.objects.Player;

import java.util.ArrayList;
import java.util.Date;

public class PlayerFactory {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int number;
    private ArrayList<Position> positions;


    public PlayerFactory setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public static void validDateData(String firstName, String lastName, Date dateOfBirth, int number, ArrayList<Position> positions) throws IllegalArgumentException{
        if(firstName == null || firstName.isBlank()){
            throw new IllegalArgumentException("Fehlender Vorname");
        }
        if(lastName == null || lastName.isBlank()){
            throw new IllegalArgumentException("Fehlender Nachname");
        }
        if(dateOfBirth == null){
            throw new IllegalArgumentException("Fehlender Geburtstag");
        }
        if(number <= 0){
            throw new IllegalArgumentException("Spielernummer muss größer als 0 sein");
        }
        if(positions == null || positions.isEmpty()){
            throw new IllegalArgumentException("Spieler muss mindestens eine Position haben");
        }
    }

    public Player build() throws IllegalArgumentException {
        validDateData(firstName, lastName, dateOfBirth, number, positions);
        return new Player(firstName, lastName, dateOfBirth, number, positions);
    }


}
