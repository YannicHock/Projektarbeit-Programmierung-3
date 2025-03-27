package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.objects.Team;

/**
 * Die Klasse TeamFactory ist verantwortlich für die Erstellung von Team-Objekten.
 * Sie bietet Methoden zum Setzen verschiedener Attribute eines Teams und validiert die Daten vor der Erstellung des Teams.
 */
public class TeamFactory {

    private String name;
    private int leagueId;

    /**
     * Setzt den Namen des Teams.
     * @return die TeamFactory-Instanz
     */
    public Team build() throws IllegalArgumentException {
        if(name == null || name.isEmpty() || name.isBlank()){
            throw new IllegalArgumentException("Fehlender Teamname");
        }
        if(leagueId <= 0){
            throw new IllegalArgumentException("Fehlende Liga-ID");
        }

        return new Team(name, leagueId);
    }

    /**
     * Setzt den Namen des Teams.
     * @param name der Name
     * @return die TeamFactory-Instanz
     */
    public TeamFactory setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Setzt die ID der Liga, in der das Team spielt.
     * @param leagueId die Liga-ID
     * @return die TeamFactory-Instanz
     */
    public TeamFactory setLeagueId(int leagueId) {
        this.leagueId = leagueId;
        return this;
    }
}

