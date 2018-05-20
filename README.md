# Author

Christian Berger

# Project Title

Game of Life for it-talents.de competition

## Getting Started

Einfach in der Konsole den Befehl

```java -jar GameOfLife.jar```

ausführen (Installiertes Java ist hierfür eine Vorraussetzung)

<img src="https://raw.githubusercontent.com/bergerch/GameOfLife/master/gol.png" width="600" height="400" />

## Use

Alle Inputs können durch eine graphische Benutzeroberfläche erfolgen.
Ein Klick auf eine Zelle belebt sie (falls tot) oder tötet sie (falls sie schon lebt).
Man kann auch Linien zeichnen indem man die Maus gedrück hält und über tote Zellen fährt um sie zu beleben (umgekehrt kann man auch mehrere Zellen löschen indem man auf eine lebende Zelle klickt, die Maus gedrückt hält und über weitere Zellen fährt).
Für weitere Einstellungen und Features stehen selbsterkärende Bedienelemente zur Verfügung

## Development

Das Programm ist in Java geschrieben. Alle Source Datein liegen bei. Zur Entwicklung wurde IntelliJ benutzt.

## Known Issues

Das Ändern der Fenstergröße (Resizing) führt auch zu einem Ädnern des (x,y) Modells. Zur Laufzeit der Simulation kann daher bei gleitenden Figuren, die über den Rand schreiten eine Fehlberechnung auftreten (eigentlich kein Fehler sondern ein Concurrency Problem,- man müsste das Resizing zur Laufzeit blockieren)

## Weiteres

Der Code ist genereisch genug um alternative Regelwerke zu unterstüzen e.g.
```javascript
    /*
     * Spielregeln!! Moeglich sind beispielsweise:
     * 
     * GEBURT | TOT     | Welt 
     * ------------------------ 
     * 3      | 0145678 | Cornway Original 
     * 0123478| 5       | Anti Cornway 
     * 1357   | 02468   | Kopiersystem 
     * 02468  |1357     | Antikopiersystem 
     * 3      | 014578  | Explodierend, aehnlich zu Cornway
     */
     
```
Siehe dazu die Klasse "Game"

### Kontakt

E-Mail: bergerch@fim.uni-passau.de
