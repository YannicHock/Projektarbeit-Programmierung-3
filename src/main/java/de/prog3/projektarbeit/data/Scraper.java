package de.prog3.projektarbeit.data;


import de.prog3.projektarbeit.utils.Formatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;

public class Scraper {

    private static final Logger log = LoggerFactory.getLogger(Scraper.class);

    public static void scrapePlayer(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            Element playerInfo = doc.getElementsByClass("kick__vita__header--player").getFirst();
            String name = playerInfo.getElementsByClass("kick__vita__header__person-name-medium-h1").getFirst().text();
            String[] nameParts = name.split(" ");
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1 ? nameParts[1] : "";
            Date date;
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
            date = Formatter.parseStringToDate(dateString);

            String team = playerInfo.getElementsByClass("kick__vita__header__team-name").getFirst().text();
            //kick__player__number
            int number = Integer.parseInt(doc.getElementsByClass("kick__player__number").getFirst().text());


            System.out.println("Name: " + firstName + " " + lastName);
            System.out.println("Position: " + pos);
            System.out.println("Geboren: " + date.toString());
            System.out.println("Team: " + team);
            System.out.println("Nummer: " + number);
        } catch (Exception e){
            log.error("e: ", e);
        }
    }

    public static void scrapeTeam(String url){
        try {
            HashSet<String> links = new HashSet<>();
            Document doc = Jsoup.connect(url).get();
            doc.select("a")
                    .stream()
                    .map(element -> element.attr("href"))
                    .filter(href -> href.contains("/spieler/"))
                    .forEach(links::add);
            doc.getElementsByClass("kick__head-breadcrumb__items").getLast().text();
        } catch (Exception e){
            log.error("e: ", e);
        }
    }
}
