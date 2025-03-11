package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.data.DataObject;
import de.prog3.projektarbeit.data.Position;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class Player extends DataObject {

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int number;
    private ArrayList<Position> positions;
    private int teamId;
    private int id;


    public Player(int id, String firstName, String lastName, Date dateOfBirth, int number, ArrayList<Position> positions) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.number = number;
        this.teamId = 0;
        this.positions = positions;
        this.id = id;
    }

    public Player(String firstName, String lastName, Date dateOfBirth, int number, ArrayList<Position> positions) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.number = number;
        this.teamId = 0;
        this.positions = positions;
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

    public int getId() {
        return id;
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

    public void save() {
    }

    @Override
    public void registerListener() {
    }

}
