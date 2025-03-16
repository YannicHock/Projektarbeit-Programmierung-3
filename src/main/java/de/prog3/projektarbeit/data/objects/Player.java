package de.prog3.projektarbeit.data.objects;

import de.prog3.projektarbeit.data.database.JooqContextProvider;
import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.exceptions.UnableToSavePlayerExeption;
import org.jooq.DSLContext;

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

    public void save() throws UnableToSavePlayerExeption {
        try {
            DSLContext ctx = JooqContextProvider.getDSLContext();
            if(super.getId() == 0){
                ctx.insertInto(PLAYER)
                        .columns(PLAYER.FIRSTNAME, PLAYER.LASTNAME, PLAYER.DATEOFBIRTH, PLAYER.NUMBER)
                        .values(this.firstName, this.lastName, parseDateToString(this.dateOfBirth), this.number)
                        .onDuplicateKeyUpdate()
                        .set(PLAYER.FIRSTNAME, this.firstName)
                        .set(PLAYER.LASTNAME, this.lastName)
                        .set(PLAYER.DATEOFBIRTH, parseDateToString(dateOfBirth))
                        .set(PLAYER.NUMBER, this.number)
                        .set(PLAYER.TEAM_ID, this.teamId)
                        .execute();
                ctx.select(PLAYER.ID).from(PLAYER).where(PLAYER.FIRSTNAME.eq(this.firstName)).and(PLAYER.LASTNAME.eq(this.lastName)).fetch().forEach(record -> super.setId(record.get(PLAYER.ID)));
            } else {
                ctx.update(PLAYER)
                        .set(PLAYER.FIRSTNAME, this.firstName)
                        .set(PLAYER.LASTNAME, this.lastName)
                        .set(PLAYER.DATEOFBIRTH, parseDateToString(dateOfBirth))
                        .set(PLAYER.NUMBER, this.number)
                        .set(PLAYER.TEAM_ID, this.teamId)
                        .where(PLAYER.ID.eq(super.getId()))
                        .execute();
            }
            ArrayList<Position> oldPositions = new ArrayList<>();
            ctx.select().from(POSITIONS).where(POSITIONS.PLAYERID.eq(super.getId())).fetch().forEach(record -> oldPositions.add(Position.valueOf(record.get(POSITIONS.POSITION))));
            ArrayList<Position> added = new ArrayList<>(positions);
            ArrayList<Position> removed = new ArrayList<>(oldPositions);
            added.removeAll(removed);
            removed.removeAll(positions);

            removed.forEach(position ->
                    ctx.deleteFrom(POSITIONS)
                            .where(POSITIONS.PLAYERID.eq(super.getId())).and(POSITIONS.POSITION.eq(position.name()))
                            .execute()
            );

            added.forEach(position ->
                    ctx.insertInto(POSITIONS)
                            .columns(POSITIONS.PLAYERID, POSITIONS.POSITION)
                            .values(super.getId(), position.name())
                            .execute()
            );
        } catch (ParseException e){
            throw new UnableToSavePlayerExeption("Player {" +this.firstName + " " + this.lastName + "} could not be saved");
        }
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
