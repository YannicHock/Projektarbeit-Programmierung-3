package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.data.Position;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import static de.prog3.projektarbeit.data.jooq.tables.Player.PLAYER;
import static de.prog3.projektarbeit.data.jooq.tables.Positions.POSITIONS;

public class Player extends DataObject {

    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;
    private final int number;
    private final ArrayList<Position> positions;
    private final int teamId;

    public Player(int id, String firstName, String lastName, Date dateOfBirth, int number, ArrayList<Position> positions, int teamId) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.number = number;
        this.positions = positions;
        this.teamId = teamId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public int getTeamId() {
        return teamId;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    public String getPositionsAsString() {
        return positions.stream()
                .map(position -> "- " + position.getFriendlyName())
                .collect(Collectors.joining("\n"));
    }


    public int getNumber() {
        return number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        LocalDate birthDate = this.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        if (birthDate != null) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    public static Date parseStringToDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.parse(date);
    }


    public static String parseDateToString(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player player){
            return player.getId() == this.getId();
        }
        return false;
    }
}
