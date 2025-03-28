package de.prog3.projektarbeit.data;


import de.prog3.projektarbeit.eventHandling.events.data.DataImportEvent;
import de.prog3.projektarbeit.eventHandling.events.data.DataImportFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.AttemptPlayerTransferEvent;
import de.prog3.projektarbeit.eventHandling.events.data.player.PlayerCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.events.data.team.AttemptTeamCreationEvent;
import de.prog3.projektarbeit.eventHandling.events.data.team.TeamCreationFinishedEvent;
import de.prog3.projektarbeit.eventHandling.listeners.data.DataImportEventListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.player.PlayerCreationFinishedListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.team.AttemptTeamCreationListener;
import de.prog3.projektarbeit.eventHandling.listeners.data.team.TeamCreationFinishedListener;
import de.prog3.projektarbeit.utils.Formatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scraper {

    private static final Logger log = LoggerFactory.getLogger(Scraper.class);

    private static final String URL_BASE = "https://www.kicker.de";
    private static final String URL_PATTERN = "https://kicker\\.de/([a-zA-Z0-9-]+)/kader/([a-zA-Z0-9-]+)/2024-25";

    private Position getPosByString(String posString){
        return switch (posString) {
            case "Tor" -> Position.GK;
            case "Abwehr" -> Position.CB;
            case "Sturm" -> Position.ST;
            default -> Position.CM;
        };
    }

    private void scrapePlayer(String url, int teamId){
        try {
            Document doc = Jsoup.connect(url).get();
            Element playerInfo = doc.getElementsByClass("kick__vita__header--player").getFirst();
            String name = playerInfo.getElementsByClass("kick__vita__header__person-name-medium-h1").getFirst().text();
            String[] nameParts = name.split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";
            String pos = playerInfo.getElementsByClass("kick__vita__header__person-detail-kvpair-info")
                    .stream()
                    .filter(element -> element.text().startsWith("Position:"))
                    .map(element -> element.text().split(" ")[1])
                    .findFirst().orElse("Unbekannt");
            String dateString = playerInfo.getElementsByClass("kick__vita__header__person-detail-kvpair-info")
                    .stream()
                    .filter(element -> element.text().startsWith("Geboren:"))
                    .map(element -> element.text().split(" ")[1])
                    .findFirst().orElse("Unbekannt").replaceAll("\\.", "-");
            //kick__player__number
            int number = Integer.parseInt(doc.getElementsByClass("kick__player__number").getFirst().text());

            new PlayerCreationFinishedListener(){
                @Override
                public void onEvent(PlayerCreationFinishedEvent event) {
                    event.getPlayer().ifPresent(player -> new AttemptPlayerTransferEvent(player, player.getNumber()+"", teamId, 0 + "").call());
                }
            };
            new AttemptPlayerCreationEvent(firstName, lastName, dateString, number + "", new ArrayList<>(List.of(getPosByString(pos)))).call();
        } catch (Exception e){
            log.error("e: ", e);
        }
    }

    private void scrapeTeam(String url, int leagueId){
        ArrayList<Exception> exceptions = new ArrayList<>();
        if (isValidURL(url)){
            try {
                HashSet<String> links = new HashSet<>();
                Document doc = Jsoup.connect(url).get();
                doc.select("a")
                        .stream()
                        .map(element -> element.attr("href"))
                        .filter(href -> href.contains("/spieler/"))
                        .forEach(links::add);
                String name = doc.getElementsByClass("kick__head-breadcrumb__items").getLast().text().replaceAll(" Kader", "");
                new TeamCreationFinishedListener() {
                    @Override
                    public void onEvent(TeamCreationFinishedEvent event) {
                        event.getTeam().ifPresent(team -> {
                            links.forEach(link -> {
                                String playerUrl = URL_BASE + link;
                                scrapePlayer(playerUrl, team.getId());
                            });
                            new DataImportFinishedEvent(null).call();
                        });
                    }
                };
                new AttemptTeamCreationEvent(name, leagueId).call();
            } catch (Exception e){
                exceptions.add(e);
            }
        } else {
            exceptions.add(new MalformedURLException("URL muss das Format https://www.kicker.de/[team]/kader/[liga]/2024-25 haben"));
        }
        new DataImportFinishedEvent(exceptions).call();
    }

    public static void registerListener(){
        new DataImportEventListener(){
            @Override
            public void onEvent(DataImportEvent event) {
                new Scraper().scrapeTeam(event.getUrl(), event.getLeagueId());
            }
        };
    }

    private static boolean isValidURL(String url) {
        Pattern pattern = Pattern.compile(URL_PATTERN);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

}
