package de.prog3.projektarbeit.data;

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

import java.text.ParseException;
import java.util.ArrayList;

public class DataListener {
    public static void register(){
        new AttemptPlayerCreationListener() {
            @Override
            public void onEvent(AttemptPlayerCreationEvent event) {
                Player player = null;
                ArrayList<Exception> exceptions = new ArrayList<>();
                try {
                    PlayerFactory playerFactory = new PlayerFactory();
                    player = playerFactory.setFirstName(event.getFirstName())
                            .setLastName(event.getLastName())
                            .setDateOfBirth(Player.parseStringToDate(event.getDateOfBirth()))
                            .setNumber(Integer.parseInt(event.getNumber()))
                            .setPositions(event.getPositions())
                            .build();
                } catch (IllegalArgumentException e) {
                    exceptions.add(e);
                } catch (ParseException e) {
                    exceptions.add(new ParseException("Fehler beim Parsen des Geburtsdatums", e.getErrorOffset()));
                }

                if(player!=null){
                    try {
                        player.save();
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
                try {
                    player = new PlayerFactory()
                            .setFirstName(event.getFirstName())
                            .setLastName(event.getLastName())
                            .setPositions(event.getPositions())
                            .setDateOfBirth(Player.parseStringToDate(event.getDateOfBirth()))
                            .setNumber(Integer.parseInt(event.getNumber()))
                            .setId(event.getPlayer().getId())
                            .setTeamId(event.getPlayer().getTeamId())
                            .build();
                } catch (ParseException e) {
                    exceptions.add(new ParseException("Fehler beim Parsen des Geburtsdatums", e.getErrorOffset()));
                }

                if(player!=null){
                    try {
                        player.save();
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
