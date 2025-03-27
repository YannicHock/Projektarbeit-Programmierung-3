package de.prog3.projektarbeit.data.factories;

import de.prog3.projektarbeit.data.objects.Team;
/**
 * Die Klasse `TeamFactory` dient zur Erstellung von `Team`-Objekten.
 * Sie ermöglicht das Setzen des Teamnamens und validiert die Daten vor der Erstellung des Objekts.
 */

/**
 * Die Klasse TeamFactory ist verantwortlich für die Erstellung von Team-Objekten.
 * Sie bietet Methoden zum Setzen verschiedener Attribute eines Teams und validiert die Daten vor der Erstellung des Teams.
 */
public class TeamFactory {

    private String name;

    /**
     * Setzt den Namen des Teams.
     * @return die TeamFactory-Instanz
     */
    public Team build() throws IllegalArgumentException {
        if(name == null || name.isEmpty() || name.isBlank()){
            throw new IllegalArgumentException("Fehlender Teamname");
        }

        return new Team(name);
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
}

