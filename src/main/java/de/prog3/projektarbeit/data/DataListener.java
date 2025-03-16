package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.data.database.query.PlayerQuery;
import de.prog3.projektarbeit.data.factories.PlayerFactory;
import de.prog3.projektarbeit.data.factories.TeamFactory;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerUpdateEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerUpdateFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.data.team.AttemptTeamCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.team.TeamCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.AttemptPlayerCreationListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.AttemptPlayerUpdateListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.team.AttemptTeamCreationListener;
import de.prog3.projektarbeit.exceptions.UnableToSavePlayerExeption;
import de.prog3.projektarbeit.exceptions.UnableToSaveTeamExeption;
import de.prog3.projektarbeit.exceptions.ValidationException;
import de.prog3.projektarbeit.utils.Parser;

import java.text.ParseException;
import java.util.ArrayList;

public class DataListener {
    public static void register(){
        new AttemptPlayerCreationListener() {
            @Override
            public void onEvent(AttemptPlayerCreationEvent event) {
                Player player = null;
                ArrayList<Exception> exceptions = new ArrayList<>();
                PlayerFactory factory = new PlayerFactory();
                try {
                    factory = factory.setDateOfBirth(Parser.parseStringToDate(event.getDateOfBirth()));
                } catch (ParseException e){
                    exceptions.add(new ParseException("Das Geburtsdatum muss das Format dd-mm-yyyy oder dd.mm.yyyy haben aber sah so aus: " + event.getDateOfBirth(), e.getErrorOffset()));
                }
                try {
                    factory = factory.setNumber(Integer.parseInt(event.getNumber()));
                } catch (NumberFormatException e){
                    exceptions.add(new NumberFormatException("Die Rückennummer muss eine ganze Zahl > 0 und < 100 sein aber war: " + event.getNumber()));
                }

                try {
                    player = factory.setFirstName(event.getFirstName())
                            .setLastName(event.getLastName())
                            .setPositions(event.getPositions())
                            .build();
                } catch (ValidationException e) {
                    exceptions.addAll(e.getExceptions());
                }

                if(player!=null){
                    try {
                        PlayerQuery.save(player);
                        new PlayerCreationFinishedEvent(player).call();
                        return;
                    } catch (UnableToSavePlayerExeption e) {
                        exceptions.add(e);
                    }
                }
                new PlayerCreationFinishedEvent(exceptions).call();
            }
        };


        new AttemptTeamCreationListener() {
            @Override
            public void onEvent(AttemptTeamCreationEvent event) {
                Team team = null;
                ArrayList<Exception> exceptions = new ArrayList<>();
                try {
                    TeamFactory teamFactory = new TeamFactory();
                    team = teamFactory.setName(event.getTeamName())
                            .build();
                } catch (IllegalArgumentException e) {
                    exceptions.add(e);
                }

                if(team!=null){
                    try {
                        team.save();
                        new TeamCreationFinishedEvent(team).call();
                        return;
                    } catch (UnableToSaveTeamExeption e) {
                        exceptions.add(e);
                    }
                }
                new PlayerCreationFinishedEvent(exceptions).call();
            }
        };

        new AttemptPlayerUpdateListener(){
            @Override
            public void onEvent(AttemptPlayerUpdateEvent event) {
                Player player = null;
                ArrayList<Exception> exceptions = new ArrayList<>();
                PlayerFactory factory = new PlayerFactory();
                try {
                    factory = factory.setDateOfBirth(Parser.parseStringToDate(event.getDateOfBirth()));
                } catch (ParseException e){
                    exceptions.add(new ParseException("Das Geburtsdatum muss das Format dd-mm-yyyy oder dd.mm.yyyy haben aber sah so aus: " + event.getDateOfBirth(), e.getErrorOffset()));
                }
                try {
                    factory = factory.setNumber(Integer.parseInt(event.getNumber()));
                } catch (NumberFormatException e){
                    exceptions.add(new NumberFormatException("Die Rückennummer muss eine ganze Zahl > 0 und < 100 sein aber war: " + event.getNumber()));
                }

                try {
                    player = factory.setFirstName(event.getFirstName())
                            .setLastName(event.getLastName())
                            .setPositions(event.getPositions())
                            .setTeamId(event.getPlayer().getTeamId())
                            .setId(event.getPlayer().getId())
                            .build();
                } catch (ValidationException e) {
                    exceptions.addAll(e.getExceptions());
                }

                if(player!=null){
                    try {
                        PlayerQuery.save(player);
                        new PlayerUpdateFinishedEvent(player, event.getPlayer()).call();
                        return;
                    } catch (UnableToSavePlayerExeption e) {
                        exceptions.add(e);
                    }
                }
                new PlayerUpdateFinishedEvent(exceptions).call();
            }
        };
    }
}
