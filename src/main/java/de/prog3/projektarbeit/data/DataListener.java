package de.prog3.projektarbeit.data;

import de.prog3.projektarbeit.data.database.query.PlayerQuery;
import de.prog3.projektarbeit.data.database.query.TeamQuery;
import de.prog3.projektarbeit.data.database.query.TransferQuery;
import de.prog3.projektarbeit.data.factories.PlayerFactory;
import de.prog3.projektarbeit.data.factories.TeamFactory;
import de.prog3.projektarbeit.data.factories.TransferFactory;
import de.prog3.projektarbeit.data.objects.Player;
import de.prog3.projektarbeit.data.objects.Team;
import de.prog3.projektarbeit.data.objects.Transfer;
import de.prog3.projektarbeit.eventHandling.events.data.player.*;
import de.prog3.projektarbeit.eventHandling.events.data.team.AttemptTeamCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.team.TeamCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.Priority;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.AttemptPlayerCreationListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.AttemptPlayerTransferListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.AttemptPlayerUpdateListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerUpdateFinishedListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.team.AttemptTeamCreationListener;
import de.prog3.projektarbeit.exceptions.UnableToSavePlayerExeption;
import de.prog3.projektarbeit.exceptions.UnableToSaveTeamExeption;
import de.prog3.projektarbeit.exceptions.ValidationException;
import de.prog3.projektarbeit.utils.Formatter;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;


public class DataListener {
    private static final Logger logger = LoggerFactory.getLogger(DataListener.class);

    public static void register(){
        new AttemptPlayerCreationListener() {
            @Override
            public void onEvent(AttemptPlayerCreationEvent event) {
                logger.info("AttemptPlayerCreationEvent empfangen: {}", event);
                Player player = null;
                ArrayList<Exception> exceptions = new ArrayList<>();
                PlayerFactory factory = new PlayerFactory();
                try {
                    factory = factory.setDateOfBirth(Formatter.parseStringToDate(event.getDateOfBirth()));
                    logger.debug("Geburtsdatum erfolgreich gesetzt.");
                } catch (ParseException e){
                    exceptions.add(new ParseException("Das Geburtsdatum muss das Format dd-mm-yyyy oder dd.mm.yyyy haben aber sah so aus: " + event.getDateOfBirth(), e.getErrorOffset()));
                    logger.warn("Fehler beim Setzen des Geburtsdatums: {}", e.getMessage());
                }
                try {
                    factory = factory.setNumber(Integer.parseInt(event.getNumber()));
                    logger.debug("Spielernummer erfolgreich gesetzt.");
                } catch (NumberFormatException e){
                    exceptions.add(new NumberFormatException("Die Rückennummer muss eine ganze Zahl > 0 und < 100 sein aber war: " + event.getNumber()));
                    logger.warn("Fehler beim Setzen der Spielernummer: {}", e.getMessage());
                }

                try {
                    player = factory.setFirstName(event.getFirstName())
                            .setLastName(event.getLastName())
                            .setPositions(event.getPositions())
                            .build();
                    logger.info("PlayerFactory erfolgreich verarbeitet.");
                } catch (ValidationException e) {
                    exceptions.addAll(e.getExceptions());
                    logger.warn("Validierungsfehler beim Erstellen des Spielers: {}", e.getExceptions());
                }

                if(player != null){
                    try {
                        PlayerQuery.save(player);
                        logger.info("Spieler erfolgreich gespeichert: {}", player);
                        new PlayerCreationFinishedEvent(player).call();
                        return;
                    } catch (UnableToSavePlayerExeption e) {
                        exceptions.add(e);
                        logger.error("Fehler beim Speichern des Spielers: {}", e.getMessage());
                    }
                }
                logger.info("PlayerCreationFinishedEvent wird mit Fehlern ausgelöst. Anzahl Fehler: {}", exceptions.size());
                new PlayerCreationFinishedEvent(exceptions).call();
            }
        };

        new AttemptTeamCreationListener() {
            @Override
            public void onEvent(AttemptTeamCreationEvent event) {
                logger.info("AttemptTeamCreationEvent empfangen: {}", event);
                Team team = null;
                System.out.println(event.getLeagueId());
                ArrayList<Exception> exceptions = new ArrayList<>();
                try {
                    TeamFactory teamFactory = new TeamFactory();
                    team = teamFactory
                            .setName(event.getTeamName())
                            .setLeagueId(event.getLeagueId())
                            .build();
                    logger.info("TeamFactory erfolgreich verarbeitet.");
                } catch (IllegalArgumentException e) {
                    exceptions.add(e);
                    logger.warn("Fehler beim Erstellen des Teams: {}", e.getMessage());
                }

                if(team != null){
                    //System.out.println(team.getLeagueId());
                    TeamQuery.save(team);
                    logger.info("Team erfolgreich gespeichert: {}", team);
                    new TeamCreationFinishedEvent(team).call();
                    return;
                }
                logger.info("TeamCreationFinishedEvent wird mit Fehlern ausgelöst. Anzahl Fehler: {}", exceptions.size());
                new PlayerCreationFinishedEvent(exceptions).call();
            }
        };

        new AttemptPlayerUpdateListener(){
            @Override
            public void onEvent(AttemptPlayerUpdateEvent event) {
                logger.info("AttemptPlayerUpdateEvent empfangen: {}", event);
                Player player = null;
                ArrayList<Exception> exceptions = new ArrayList<>();
                PlayerFactory factory = new PlayerFactory();
                try {
                    factory = factory.setDateOfBirth(Formatter.parseStringToDate(event.getDateOfBirth()));
                    logger.debug("Geburtsdatum erfolgreich gesetzt.");
                } catch (ParseException e){
                    exceptions.add(new ParseException("Das Geburtsdatum muss das Format dd-mm-yyyy oder dd.mm.yyyy haben aber sah so aus: " + event.getDateOfBirth(), e.getErrorOffset()));
                    logger.warn("Fehler beim Setzen des Geburtsdatums: {}", e.getMessage());
                }
                try {
                    factory = factory.setNumber(Integer.parseInt(event.getNumber()));
                    logger.debug("Spielernummer erfolgreich gesetzt.");
                } catch (NumberFormatException e){
                    exceptions.add(new NumberFormatException("Die Rückennummer muss eine ganze Zahl > 0 und < 100 sein aber war: " + event.getNumber()));
                    logger.warn("Fehler beim Setzen der Spielernummer: {}", e.getMessage());
                }

                try {
                    player = factory.setFirstName(event.getFirstName())
                            .setLastName(event.getLastName())
                            .setPositions(event.getPositions())
                            .setTeamId(event.getTeamId())
                            .setId(event.getOldPlayer().getId())
                            .build();
                    logger.info("PlayerFactory für Update erfolgreich verarbeitet.");
                } catch (ValidationException e) {
                    exceptions.addAll(e.getExceptions());
                    logger.warn("Validierungsfehler beim Aktualisieren des Spielers: {}", e.getExceptions());
                }

                if(player != null){
                    try {
                        PlayerQuery.save(player);
                        logger.info("Spieleraktualisierung erfolgreich gespeichert.");
                        new PlayerUpdateFinishedEvent(player, event.getOldPlayer()).call();
                        return;
                    } catch (UnableToSavePlayerExeption | IntegrityConstraintViolationException e) {
                        exceptions.add(e);
                        logger.error("Fehler beim Speichern der Spieleraktualisierung: {}", e.getMessage());
                    }
                }
                logger.info("PlayerUpdateFinishedEvent wird mit Fehlern ausgelöst. Anzahl Fehler: {}", exceptions.size());
                new PlayerUpdateFinishedEvent(exceptions).call();
            }
        };

        new AttemptPlayerTransferListener(){
            @Override
            public void onEvent(AttemptPlayerTransferEvent transferEvent) {
                logger.info("AttemptPlayerTransferEvent empfangen: {}", transferEvent);
                Player player = transferEvent.getPlayer();
                ArrayList<Exception> exceptions = new ArrayList<>();
                try {
                    Transfer transfer = new TransferFactory()
                            .setPlayerId(player.getId())
                            .setNewTeamId(transferEvent.getNewTeamId())
                            .setOldTeamId(transferEvent.getPlayer().getTeamId())
                            .setAmount(Integer.parseInt(transferEvent.getAmount()))
                            .build();
                    logger.info("TransferFactory erfolgreich verarbeitet. Transfer: {}", transfer);
                    new PlayerUpdateFinishedListener(Priority.LOWEST) {
                        @Override
                        public void onEvent(PlayerUpdateFinishedEvent event) {
                            event.getExceptions().ifPresent(updateExceptions -> {
                                exceptions.addAll(updateExceptions);
                                logger.warn("Fehler beim Update im Transfer-Prozess: {}", updateExceptions);
                                new PlayerTransferFinishedEvent(exceptions).call();
                            });
                            event.getPlayer().ifPresent(newPlayer -> {
                                try {
                                    TransferQuery.addTransfer(transfer);
                                    logger.info("Transfer erfolgreich in der Datenbank eingetragen.");
                                    new PlayerTransferFinishedEvent(newPlayer, player.getTeamId(), transferEvent.getNewTeamId()).call();
                                } catch (ParseException e) {
                                    exceptions.add(new ParseException("Das Datum des Transfers konnte nicht geparst werden", e.getErrorOffset()));
                                    logger.error("Fehler beim Parsen des Transferdatums: {}", e.getMessage());
                                } catch (NumberFormatException e){
                                    exceptions.add(new IllegalArgumentException("Die Transfersumme muss eine ganze Zahl > 0 sein aber war: " + transferEvent.getAmount()));
                                    logger.error("Fehler bei der Transfersumme: {}", e.getMessage());
                                } catch (IntegrityConstraintViolationException e){
                                    exceptions.add(e);
                                    logger.error("Integritätsverletzung beim Hinzufügen des Transfers: {}", e.getMessage());
                                }
                                new PlayerTransferFinishedEvent(exceptions).call();
                            });
                            logger.debug("PlayerUpdateFinishedListener im Transfer-Prozess wird abgemeldet.");
                            this.unregister();
                        }
                    };
                } catch (IllegalArgumentException e){
                    exceptions.add(e);
                    logger.warn("Fehler beim Erstellen des Transfers: {}", e.getMessage());
                }
                if(exceptions.isEmpty()){
                    logger.info("Keine Fehler beim Transfer. Starte Aktualisierung...");
                    new AttemptPlayerUpdateEvent(player, transferEvent.getNumberString(), transferEvent.getNewTeamId()).call();
                } else {
                    logger.info("PlayerTransferFinishedEvent wird mit {} Fehler(n) ausgelöst.", exceptions.size());
                    new PlayerTransferFinishedEvent(exceptions).call();
                }
            }
        };
    }
}