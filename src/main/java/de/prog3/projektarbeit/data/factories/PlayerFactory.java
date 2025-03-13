package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.JooqContextProvider;
import de.prog3.projektarbeit.data.Position;
import de.prog3.projektarbeit.data.jooq.tables.records.PlayerRecord;
import de.prog3.projektarbeit.data.objects.Player;

import de.prog3.projektarbeit.exceptions.PlayerNotFoundExeption;
import de.prog3.projektarbeit.exceptions.ValidationException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static de.prog3.projektarbeit.data.jooq.tables.Player.PLAYER;
import static de.prog3.projektarbeit.data.jooq.tables.Positions.POSITIONS;

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

    public static void validDateData(String firstName, String lastName, Date dateOfBirth, int number, ArrayList<Position> positions) throws ValidationException{
        ArrayList<Exception> exceptions = new ArrayList<>();
        if(firstName == null || firstName.isBlank()){
             exceptions.add(new IllegalArgumentException("Fehlender Vorname"));
        }
        if(lastName == null || lastName.isBlank()){
            exceptions.add(new IllegalArgumentException("Fehlender Nachname"));
        }
        if(dateOfBirth == null){
            exceptions.add(new IllegalArgumentException("Fehlender Geburtstag"));
        }
        if(number <= 0 || number > 99){
            exceptions.add(new IllegalArgumentException("Spielernummer muss größer als 0 und kleiner als 100 sein"));
        }
        if(positions == null || positions.isEmpty()){
            exceptions.add(new IllegalArgumentException("Spieler muss mindestens eine Position haben"));
        }
        if(!exceptions.isEmpty()){
            throw new ValidationException(exceptions);
        }
    }

    public Player build() throws ValidationException {
        validDateData(firstName, lastName, dateOfBirth, number, positions);
        return new Player(id, firstName, lastName, dateOfBirth, number, positions, teamId);
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


    public static Player getPlayerById(int id) throws PlayerNotFoundExeption {
        DSLContext ctx = JooqContextProvider.getDSLContext();
        PlayerRecord record = (PlayerRecord) ctx.select().from(PLAYER).where(PLAYER.ID.eq(id)).fetchOne();
        Player player = null;
        try {
            if(record == null){
                throw new PlayerNotFoundExeption("Spieler mit der ID " + id + " nicht gefunden");
            }
            Optional<Integer> teamId = Optional.ofNullable(record.getTeamId());
            player = new Player(record.getId(), record.getFirstname(), record.getLastname(), Player.parseStringToDate(record.getDateofbirth()), record.getNumber(), getPlayerPositions(record.getId()), teamId.orElse(0));
        } catch (ParseException e) {
            throw new PlayerNotFoundExeption("Spieler mit der ID " + id + " nicht gefunden");
        }
        return player;
    }

    public static HashMap<Integer, Player> getFreeAgents(){
        DSLContext ctx = JooqContextProvider.getDSLContext();
        Result<Record> result = ctx.select().from(PLAYER).where(PLAYER.TEAM_ID.eq(0).or(PLAYER.TEAM_ID.isNull())).fetch();
        HashMap<Integer, Player> players = new HashMap<>();
        for(Record r : result){
            PlayerRecord record = (PlayerRecord) r;
            try {
                Optional<Integer> teamId = Optional.ofNullable(record.getTeamId());
                Player player = new Player(record.getId(), record.getFirstname(), record.getLastname(), Player.parseStringToDate(record.getDateofbirth()), record.getNumber(), getPlayerPositions(((PlayerRecord) r).getId()), teamId.orElse(0));
                players.put(player.getId(), player);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return players;
    }

}
