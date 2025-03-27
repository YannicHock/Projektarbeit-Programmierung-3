# 1. Projektübersicht
Projektname: Kicker CLI
Das Programm bildet eine vereinfachte Kopie bekannter Fußball Datenbanken wir Transfermarkt.de oder Kicker.de als CLI Anwendung ab.
# 2. Features
* **Multi Window Support mit aktivier Synchronisierung der angezeigten Daten**
* Spieler erstellung
* Spieler bearbeitung
* Spieler anzeigen
* Spieler transfers
* Transferhistorie eines Spielers anzeigen
* Mannschafts Erstellung
* Mannschafts Anzeige
* Turnier Erstellung
* Turnier Anzeige

# 3. Systemanforderungen
* Java: 21.0.0
* Maven: 3.9.9

# 4. Installation
`git clone https://github.com/YannicHock/Projektarbeit-Programmierung-3/`

`mvn clean install`

# 5. Schnellstart
Insofern Java 21 als Standard Java Version gesetzt ist, kann das Programm mit Doppelklick auf die gebaute oder aus dem Release heruntergeladene Jar Datei gestartet werden.

Alternativ kann das Programm auch über ein CLI gestartet werden.

`java -jar target/Projektarbeit-Abgabeversion.jar`

# 6. Anwendungsbeispiele
Die Anwendung startet mit einem Hauptfenster, in dem die verschiedenen Funktionen ausgewählt werden können. Dabei kann mit den Pfeiltasten oder TAB navigiert werden und mit Leertaste oder Enter die ausgewählte Aktion ausgeführt werden. Die Menuführung ist dabei wie folgt aufgebaut:
* Spieler
  * Spieler erstellen
  * Spieler bearbeiten
  * Spieler anzeigen
  * Spieler transferieren
  * Transferhistorie anzeigen
* Mannschaft
  * Mannschaft erstellen
  * Mannschaft anzeigen
* Turnier
  * Turnier erstellen
  * Turnier anzeigen

# 7. Bekannte Einschränkungen
Derzeit ist keine Löschung von Spielern, Mannschaften oder Turnieren möglich.