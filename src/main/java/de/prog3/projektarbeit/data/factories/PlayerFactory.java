package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.JooqContextProvider;
import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.objects.Player;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static de.prog3.projektarbeit.data.jooq.tables.Player.PLAYER;
import static de.prog3.projektarbeit.data.jooq.tables.Positions.POSITIONS;

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

    private static ArrayList<Position> getPlayerPositions(int playerId) {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> positions = ctx.select().from(POSITIONS).where(POSITIONS.PLAYERID.eq(playerId)).fetch();
        ArrayList<Position> playerPositions = new ArrayList<>();
        if(positions.isEmpty()){
            return playerPositions;
        }
        for(Record r : positions){
            playerPositions.add(Position.valueOf(r.get(POSITIONS.POSITION)));
        }
        return playerPositions;
    }

    public static Optional<Player> extractPlayerFromRecord(Record record) {
        Player player = null;
        if (record.get(PLAYER.ID) != null) {
            try {
                player = new Player(record.get(PLAYER.ID), record.get(PLAYER.FIRSTNAME), record.get(PLAYER.LASTNAME), Player.parseStringToDate(record.get(PLAYER.DATEOFBIRTH)), record.get(PLAYER.NUMBER), getPlayerPositions(record.get(PLAYER.ID)), record.get(PLAYER.TEAM_ID));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(player);
    }

}
