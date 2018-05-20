package model;

/**
 * @author Christian Berger
 * Eine Figur ist eine festgelegte Konstante, ein Quadrupel aus Name,
 * Breite, Hoehe und Koordinaten. Es stehen folgende Figuren zur Auswahl:
 * Block, Boat, Toad, Glider, Spaceship, Pulsar.
 * Sowie eine Methode um eine beliebige Figur einzufuegen.
 */
public enum Figure {
    
    /**
     * Kleines statisches Quadrat
     */
    BLOCK("Block", 2, 2, "0.0  0.1  1.1  1.0"),
    
    /**
     * mittlel-kleines statische Objekt in Bootsform
     */
    BOAT("Boat", 3, 3, "0.0 0.1 1.0 1.2 2.1"),
    
    /**
     * Oszillierendes Objekt mit 2 Zyklen und Rotatiosbewegung:
     * Achtung: Es passt zwar auf 
     * ein 3x1 Spielfeld, kann sich aber dann nicht entwickeln 
     */
    BLINKER("Blinker", 3, 1, "0.0 1.0 2.0"),
    
    /**
     * Oszilierendes Objekt mit 2 Zyklen
     */
    TOAD("Toad", 4, 2, "1.0 2.0 3.0 0.1 2.1 1.1"),
    
    /**
     * Ein klein-mittleres sich in der Spielwelt bewegendes Objekt.
     * Die Bewegung laeuft ueber 4 Phasen
     */
    GLIDER("Glider", 3, 3, "0.0 1.0 2.0 0.1 1.2"),
    
    /**
     * grosses sich bewegendes Objekt. Verschiebt die Position innerhalb
     * von 4 Phasen
     */
    SPACESHIP("Spaceship", 5, 4, "1.0 4.0 0.1 0.2 4.2 0.3 1.3 2.3 3.3"),
    
    /**
     * sehr grosses, oszillierendes Objekt mit 4 Phasen,
     * das zwar auf ein 13x13 Spielfeld passt aber fuer eine korrekte
     *  Generation ein 15x15 SPielfeld braucht!
     */
    PULSAR("Pulsar", 13, 13, "2.0 3.0 9.0 10.0 3.1 4.1 8.1 9.1 0.2 3.2 9.2 12.2"
    + " 7.2 5.2 0.3 1.3 2.3 4.3 5.3 7.3 8.3 10.3 11.3 12.3 1.4 3.4 5.4 7.4" 
    + " 9.4 11.4 2.5 3.5 4.5 8.5 9.5 10.5 2.7 3.7 4.7 8.7 9.7 10.7 1.8 3.8 5.8" 
    + " 7.8 9.8 11.8 0.9 1.9 2.9 4.9 5.9 7.9 8.9 10.9 11.9 12.9 0.10 0.10 5.10" 
    + " 7.10 3.10  9.10 12.10 9.11 8.11 4.11 3.11 10.12 9.12 3.12 2.12 "),
    
    /**
     * 
     */
    CLEAR("Clear", 0, 0, "");

    private String name;
    private int figureColumns;
    private int figureRows;
    private String coords;

    /**
     * Eine Figur besitzt folgende Eigenschaften:
     * @param name  der Name der Figur
     * @param figureColumns  Breite der Figur
     * @param figureRows  Hoehe der Fizgur
     * @param coords  Koordinaten der lebenden Zellen, in einem String 
     *        gespeichert, wobei die einzelnen Zellen in der Form x.y
     *        dargestellt und durch Leerzeichen getrennt werden.
     */
    Figure(String name, int figureColumns, int figureRows, String coords) {
        this.name = name;
        this.figureColumns = figureColumns;
        this.figureRows = figureRows;
        this.coords = coords;
    }

    /**
     * Gibt Namen der Figur zurueck
     * 
     * @return Name der Figur
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Breite der Figur zurueck
     * 
     * @return Breite der Figur
     */
    public int getColumns() {
        return figureColumns;
    }

    /**
     * Gibt die Hoehe der Figur zurueck
     * 
     * @return Hoehe der Figur
     */
    public int getRows() {
        return figureRows;
    }

    /**
     * gibt die Koordinaten der Figur zurueck. <br>
     * Allerdings in String Form wobei die einzelnen lebenden Zellen in der 
     * Form x.y dargestellt und durch Leerzeichen getrennt werden.
     * 
     * @return Koordinaten der Figur als String
     */
    public String getCoords() {
        return coords;
    }

    /**
     * Fuegt eine Figur ins Spielfeld ein
     * 
     * @param figure eine spezielle Figur (Konstante)
     * @param game das Spielfeld
     * @return erfolgreich Figur eingefuegt?
     */
    public static boolean insertFigure(Figure figure, Grid game) {
        // Berrechnungen um die Figur zentriert einfuegen zu koenen
        int gameX = (game.getColumns() - figure.getColumns()) / 2;
        int gameY = (game.getRows() - figure.getRows()) / 2;
        
        // Prueft ob das Spielfeld gross genug fuer die Figur ist
        if (figure.getRows() > game.getRows()
                || figure.getColumns() > game.getColumns()) {
            return false;
        }
        // Liest die Koordinatentupel aus dem String welche durch Leerzeichen
        // abgetrennt worden sind und fuegt sie in ein Array ein
        String[] cells = figure.getCoords().trim().split("\\s+");
        
        // Sauebert das Spielfeld zuerst
        game.clear();
        
        if (figure != CLEAR) {
            // Liest nun die Werte der x und y Koordinaten aus
            for (int i = 0; i < cells.length; i++) {
            
                String[] koords = cells[i].split("\\.");
                try {
                    int x = Integer.parseInt(koords[0]);
                    int y = Integer.parseInt(koords[1]);
            
                    // Setzt die entsprechende Zelle nun auf lebend
                    game.setAlive(x + gameX, y + gameY, true);
                } catch (NumberFormatException e) {
                    /*
                     *  Da man davon ausgehen kann, dass die Konstanten richtige
                     *  Koordianten haben, wird dieser Fall eh nicht eintreten.
                     *  Falls die enum erweitert wird, bewahrt dies aber das
                     *  Programm vor einem Absturz.
                     */
                    game.clear();
                }
            }
        }
        return true;
    }
    
}
